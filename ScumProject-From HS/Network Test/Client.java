import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        String str;
        Socket sock = null;
        ObjectOutputStream writer = null;//makes an ObjectOutputStream
        ObjectInputStream reader = null;//makes an ObjectInputStream
        Scanner kb = new Scanner(System.in);
        try
        {
            sock = new Socket("127.0.0.1",4445);
        }
        catch(IOException e)
        {
            System.out.println("Could not connect.");
            System.exit(-1);
        }
	System.out.println("Connected");
        try
        {
            /*
             * Try to construct the ObjectOutputStream using the OutputStream from the socket
             */
            writer = new ObjectOutputStream(sock.getOutputStream());
	    System.out.println("got output stream");
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
	    System.out.println("Got Input Stream");
        }
        catch(IOException e)
        {
            System.out.println("Could not create read object.");
            System.exit(-1);
        }
        //write
        str = kb.nextLine();
        try
        {
            /*
             * Try to write the object to the output. I believe the buffer auto-flushes, so
             * that you don't have to explicitly flush it. Must catch IOException.
             */
            writer.writeObject(str);
            //writer.flush();
        }
        catch(IOException e)
        {
            System.out.println("Could not write to buffer");
            System.exit(-1);
        }
        //read
        try
        {
            /*
             * Try to read data sent from the server. Must catch IOException and ClassNotFoundException
             */
            str = (String)reader.readObject();
        }
        catch(IOException e)
        {
            System.out.println("Could not read from buffer");
            System.exit(-1);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Data read was not a string");
            System.exit(-1);
        }
        System.out.println(str);
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
