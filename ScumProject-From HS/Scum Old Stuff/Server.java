import java.net.*;
import java.io.*;

public class Server
{
	public static void main(String args[])
	{
		ServerSocket server = null;
		Socket client = null;
		ObjectInputStream in = null;
		try
		{
			server = new ServerSocket(4444);
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
			in = new ObjectInputStream(client.getInputStream());
		}
		catch(IOException e)
		{
			System.out.println("Could not create reader");
			System.exit(-1);
		}
		try
		{
			try
			{
				String str = ((String)in.readObject());
				System.out.println(str);
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("String not found");
				System.exit(-1);
			}
		}
		catch(IOException e)
		{
			System.out.println("Could not get data from buffer");
			System.exit(-1);
		}
		try
		{
			client.close();
			server.close();
			
		}
		catch(IOException e)
		{
			System.out.println("Could not close connection.");
			System.exit(-1);
		}
		while(true);
	}
}
