import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
    private static ArrayList<Socket> clients = new ArrayList<Socket>();
    public static void main(String args[])
    {
        ServerSocket server = null;//socket of the server
        Socket client = null;//socket to hold the connected client
        ObjectInputStream in = null;//create input stream
        ObjectOutputStream out = null;//create output stream
        String str = null;
        try
        {
            server = new ServerSocket(4445);
        }
        catch(IOException e)
        {
            System.out.println("Could not bind and listen on port: 4444");
            System.exit(-1);
        }
        System.out.println("Socket bind to port 4444 successful. Now listening...");
        
        for (int x = 0; x<4; x++)
        {
            try
            {
                client = server.accept();
            }
            catch(IOException e)
            {
                System.out.println("Error accepting connection: 4444");
                System.exit(-1);
            }
            clients.add(client);
            System.out.println("Added Client: " + (x+1));
        }
        
        try
        {
            /*
             * Try to construct the input stream using the input stream from the client
             */
            in = new ObjectInputStream(client.getInputStream());
        }
        catch(IOException e)
        {
            System.out.println("Could not create reader");
            System.exit(-1);
        }
        try
        {
            /*
             * Try to construct the output stream using the output stream from the client
             */
            out = new ObjectOutputStream(client.getOutputStream());
        }
        catch(IOException e)
        {
            System.out.println("Could not create output object");
            System.exit(-1);
        }
        
        //read
        try
        {
            /*
             * Read message sent by the client. Must catch IOException and ClassNotFoundException
             */
            str = ((String)in.readObject());
            System.out.println(str);
        }
        catch(IOException e)
        {
            System.out.println("Could not get data from buffer");
            System.exit(-1);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("String not found");
            System.exit(-1);
        }
        //write
        try
        {
            /*
             * Try to echo the message back to the client.
             */
            out.writeObject(str);
        }
        catch(IOException e)
        {
            System.out.println("Could not write.");
            System.exit(-1);
        }
        try
        {
            client.close();
            System.out.println("Closed Client");
            server.close();
            System.out.println("Closed Server");
            
            
        }
        catch(IOException e)
        {
            System.out.println("Could not close connection.");
            System.exit(-1);
        }
    }
}
    
