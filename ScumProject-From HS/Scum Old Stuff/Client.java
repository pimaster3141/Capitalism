import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{
	public static void main(String[] args)
	{
		String str;
		Socket sock = null;
		ObjectOutputStream writer = null;
		Scanner kb = new Scanner(System.in);
		try
		{
			sock = new Socket("127.0.0.1",4444);
		}
		catch(IOException e)
		{
			System.out.println("Could not connect.");
			System.exit(-1);
		}
		try
		{
			writer = new ObjectOutputStream(sock.getOutputStream());
		}
		catch(IOException e)
		{
			System.out.println("Could not create write object.");
			System.exit(-1);
		}
		str = kb.nextLine();
		try
		{
			writer.writeObject(str);
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
