package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to allow a client to connect to the CapitalServer
 *  and play the Capitalism game. Takes care of socket handling. 
 */
public class ClientConnection {
    
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    
    /**
     * Constructs ClientConnection that connects to CapitalServer, 
     * plays game until the client is closed.
     * @param hostname IP address of server (or "localhost")
     * @param port integer, at which server listens (default 4444)
     * @throws IOException if the client can't connect
     */
    private ClientConnection(String hostname, int port) throws IOException{
        socket= new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Send a message to the server from this ClientConnection
     * @param message, String to be sent to server, should follow message syntax
     */
    public void sendToServer(String message){
        this.out.println(message);
        this.out.flush();
    }
    
    /**
     * Attempts to receive a message from server. Note: this is blocking! 
     * Can handle reply in a separate class, depending on how you want to structure this.
     * @return the earliest message sent from the server, one line. 
     */
    public String getReply(){
        try {
            return this.in.readLine();
        } catch (IOException e) {
            // What do we want to do if there's an error?
            return "";
        }
    }
    
    /**
     * Creates the client object with a given hostname and port.
     * Essentially a helper for the main method.
     * @param hostname 
     * @param port
     */
    private static void runClient(String hostname, int port){
        final ClientConnection c;
        try{
            c= new ClientConnection(hostname, port);
            String username="Divya";//TODO: have client input username
            c.sendToServer("connect "+username);
            //Thread to receive messages from server
            Thread replyThread= new Thread(){
                public void run(){
                    while(true){
                        //do something with server messages here
                        String reply= c.getReply();
                        System.out.println(reply);                        
                    }
                }
            };
            replyThread.start();
            //TODO: thread for sending messages based on GUI actions
        }catch(IOException e){
            //exception thrown if there's a problem with client's connection
            System.err.println("client can't connect :(");
        }
    }
    
    /**
     * Runs client connection with optional arguments for host and port, 
     * where the CapitalServer is running. 
     * Syntax: "ClientConnection --host [HOST] --port [PORT]"
     * PORT is an optional integer in the range 0 to 65535 inclusive, 
     * specifying the port the server should be listening on for incoming 
     * connections. PORT defaults to 4444. 
     * HOST is an optional String containing the IP address of the server. 
     * HOST defaults to "localhost."
     * @param args
     */
    public static void main(String[] args){
        String host= "localhost"; //default host
        int port = 4444; // default port
        
        Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));

        try {
            while (!arguments.isEmpty()) {
                String flag = arguments.remove();
                if (flag.equals("--host")) {
                    host = arguments.remove();
                }
                else if (flag.equals("--port")) {
                    port = Integer.parseInt(arguments.remove());
                    if (port < 0 || port > 65535) {
                        throw new IllegalArgumentException("port " + port
                                + " out of range");
                    }
                }
                else if (flag.equals("")){} //do nothing
                else {
                    throw new IllegalArgumentException("unknown option: \"" + flag + "\"");
                }
            }
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            System.err.println("usage: ClientConnection [--host HOST] [--port PORT]");
            return;
        }
        //Start client connection
        runClient(host,port);
    }

}
