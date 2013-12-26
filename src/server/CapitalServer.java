package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Server for Capitalism game
 *
 */
public class CapitalServer {

    private final ServerSocket serverSocket;
    /**
     * Make a CapitalServer that listens for connections on port.
     * 
     * @param port port number, requires 0 <= port <= 65535
     */
    public CapitalServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Handle a single client connection. Returns when client disconnects.
     * 
     * @param socket socket where the client is connected
     * @throws IOException if connection has an error or terminates unexpectedly
     */
    private void handleConnection(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Welcome to Capitalism.");
        out.flush();
        
        try {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                //do something with input, send output
                
                // flush the buffer so the client gets the reply
                out.flush();
                
            }
        } 
        catch(Exception e){}
        finally { 
            out.println("disconnect"); //notify the client that they are disconnected
            out.flush();
            //disconnecting the client       
            
            out.close();
            in.close();
            socket.close();
        }
    }
        
    /**
     * Run the server, listening for client connections and handling them.
     * Never returns unless an exception is thrown.
     * 
     * @throws IOException if the main server socket is broken
     *                     (IOExceptions from individual clients do *not* terminate serve())
     */
    private void serve() throws IOException {
        while (true) {
            // block until a client connects
            final Socket clientSocket = serverSocket.accept();
            
            // start a new thread to handle the connection
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        handleConnection(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
              });
            thread.start();
        }
    }
    
    /**
     * Start a CapitalServer running on the specified port.
     * 
     * @param port The network port on which the server should listen.
     */
    public static void runServer(int port) throws IOException {
        CapitalServer server = new CapitalServer(port);
        server.serve();
    }
    
    /**
     * Start a CapitalServer using the given arguments.
     * 
     * Usage: CapitalServer [--port PORT]
     * 
     * PORT is an optional integer in the range 0 to 65535 inclusive, specifying the port the
     * server should be listening on for incoming connections. E.g. "CapitalServer --port 1234"
     * starts the server listening on port 1234.
     * 
     */
   public static void main(String[] args) {
       int port = 4444; // default port

       Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
       try {
           while (!arguments.isEmpty()) {
               String flag = arguments.remove();
               if (flag.equals("--port")) {
                   port = Integer.parseInt(arguments.remove());
                   if (port < 0 || port > 65535) {
                       throw new IllegalArgumentException("port " + port
                               + " out of range");
                   }
               }
               else {
                   throw new IllegalArgumentException("unknown option: \"" + flag + "\"");
               }

           }
       } catch (IllegalArgumentException iae) {
           System.err.println(iae.getMessage());
           System.err.println("usage: CapitalServer [--port PORT]");
           return;
       }
       try {
           runServer(port);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    
}
