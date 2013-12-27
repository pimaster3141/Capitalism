import java.net.*;
import java.io.*;

public class TestObjectServer
{
    public static void main(String args[])
    {
        ServerSocket server = null;//socket of the server
        Socket client = null;//socket to hold the connected client
        ObjectInputStream in = null;//create input stream
        ObjectOutputStream out = null;//create output stream
        TestObject test = null;
        String str = null;
        final int port = 4444;
        
        try
        {
            server = new ServerSocket(port);
        }
        catch(IOException e)
        {
            System.out.println("Could not bind and listen on port: 4444");
            System.exit(-1);
        }
        System.out.println("Socket bind to port 4444 successful. Now listening...");
        
        try
        {
            client = server.accept();
        }
        catch(IOException e)
        {
            System.out.println("Error accepting connection: 4444");
            System.exit(-1);
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
        
        try
        {
            test = (TestObject)in.readObject();
            //System.out.println(str);
            
            //Object temp = in.readObject();
            //System.out.println("Got Object");
            //test = (TestObject)temp;
            //System.out.println(test);
        }
        catch(IOException e)
        {
            System.out.println("error getting object");
            System.exit(-1);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("not right class");
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