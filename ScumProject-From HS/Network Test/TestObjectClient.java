import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TestObjectClient
{
    public static void main(String[] args)
    {
        String str;
        Socket sock = null;
        ObjectOutputStream writer = null;//makes an ObjectOutputStream
        ObjectInputStream reader = null;//makes an ObjectInputStream
        Scanner kb = new Scanner(System.in);
        final int port = 4444;
        try
        {
            sock = new Socket("127.0.0.1", port);
        }
        catch(IOException e)
        {
            System.out.println("Could not connect.");
            System.exit(-1);
        }
        try
        {
            /*
             * Try to construct the ObjectOutputStream using the OutputStream from the socket
             */
            writer = new ObjectOutputStream(sock.getOutputStream());
        }
        catch(IOException e)
        {
            System.out.println("Could not create write object.");
            System.exit(-1);
        }
        try
        {
            /*
             * Try to construct the ObjectInputStream using the InputStream from the socket
             */
            reader = new ObjectInputStream(sock.getInputStream());
        }
        catch(IOException e)
        {
            System.out.println("Could not create read object.");
            System.exit(-1);
        }
        //write
        
        
        
        
        Object test = new TestObject(5,5,"Hello");
        str = test.toString();
        System.out.println(str);
        
        
        kb.nextLine();
        try
        {
            
            Object temp = new TestObject(5,5,"Hello");
            /*
             * Try to write the object to the output. I believe the buffer auto-flushes, so
             * that you don't have to explicitly flush it. Must catch IOException.
             */
            writer.writeObject(temp);
            writer.flush();
        }
        catch(IOException e)
        {
            System.out.println("Could not write to buffer");
            System.exit(-1);
        }
        
        
        try
        {
            sock.close();
        }
        catch(IOException e)
        {
            System.out.println("Could not close connection.");
            System.exit(-1);
        }
        System.out.println("Wrote and exited successfully");
    }
}
