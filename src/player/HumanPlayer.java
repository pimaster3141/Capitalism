package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HumanPlayer extends Player
{
	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;
	private final LinkedBlockingQueue<String> outputBuffer = new LinkedBlockingQueue<String>();
	private final Thread outputConsumer;
	private boolean alive = true;
	
	public HumanPlayer(Socket socket, String username) throws IOException
	{
		super(username);
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		outputConsumer = new Thread()
		{
			public void run()
			{
				System.out.println("Client: " + name + " - " + "Starting Output Thread");
				// keep looping this thread until the connection dies
				while (alive)
					try
					{
						// send data to client
						parseOutput(outputBuffer.take());
					}
					catch (InterruptedException e)
					{
						System.out.println("Client: " + name + " - " + "Stopping Output Thread");
						break;
					}
			}
		};
	}

	/*
	 * Starts the main thread for this connection
	 */
	public void run()
	{
		// echo to client that you're connected
		out.println("Connected");

		try
		{
			// start the output consumer thread to relay data back to user
			outputConsumer.start();

			System.out.println("Client: " + name + " - " + "Starting Input Thread");

			// main loop for parsing responses from client
			for (String line = in.readLine(); (line != null && alive); line = in.readLine())
			{
				//System.out.println(line);
				// parse the input
				String parsedInput = parseInput(line);
				// echo the parsed output to the client
				updateQueue(parsedInput);
				// if the client is not alive anymore, shutdown
				if (!alive)
				{
					System.out.println("Client: " + name + " - " + "Stopping Input Thread");
					break;
				}
			}
			System.out.println("Client: " + name + " - " + "Input Thread Stopped");

		}
		catch (IOException e)
		{
			System.out.println("Client: " + name + " - Connection Lost");

		}
		finally
		{
			// stop the output thread
			outputConsumer.interrupt();
			System.out.println("Client: " + name + " - " + "Output Thread Interrupted");
			// close the socket
			try
			{
				socket.close();
			}
			catch (IOException ignore)
			{
			}
			System.out.println("Client: " + name + " - " + "Cleanup Complete");
		}
	}

	/*
	 * define any input filtering/triggers here
	 */
	private String parseInput(String input)
	{
		String regex = "";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		
		return input;
	}
	
	/*
	 * define any output filter/triggers here
	 */
	private void parseOutput(String output)
	{
		if (output.equals(""))
			return;
		System.out.println("Client: " + name + " - seinding - " + output);
		out.println(output);
		out.flush();
		return;
	}

	/*
	 * method for other things to send messages to this client
	 * adds the string to a buffer to be consumed when ready. this frees the
	 * sender to do other things and not wait for a slow connection.
	 * 
	 * @param String - message to be sent to the client
	 */
	public void  updateQueue(String info)
	{
		outputBuffer.add(info);
	}

	public void makeMove()
	{
		// TODO Auto-generated method stub
		
	}

}
