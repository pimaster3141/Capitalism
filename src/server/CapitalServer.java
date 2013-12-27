package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Server for Capitalism game
 * 
 */
public class CapitalServer
{
	private final ServerSocket serverSocket;
	//TODO private final RoomList rooms;
	//TODO private final UserList users;

	/**
	 * Make a CapitalServer that listens for connections on port.
	 * 
	 * @param port
	 *            port number, requires 0 <= port <= 65535
	 */
	public CapitalServer(int port) throws IOException
	{
		this.serverSocket = new ServerSocket(port);
	}

	/**
	 * Run the server, listening for client connections and handling them. Never
	 * returns unless an exception is thrown.
	 */
	private void serve()
	{
		System.out.println("Server INIT");
		
		while (true)
		{
			System.out.println("Server WAITING");
			try
			{
				// block until a client connects
				final Socket clientSocket = serverSocket.accept();
				System.out.println("Server Connection");
	
				// start a new thread to handle the connection and update stuff
				new Thread()
				{
					public void run()
					{
						try
						{
							System.err.println("Creating User");
							
							BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
							out.println("To connect type: \"connect [username]\"");
							out.flush();
							
							String input = in.readLine();
							if (input == null)
								throw new IOException("Username was null");
							Pattern p = Pattern.compile("connect \\p{Graph}+");
							Matcher m = p.matcher(input);
							if (!m.matches())
								throw new IOException("Client input not in the format 'connect [username]'");
							Player player = new HumanPlayer(clientSocket, input.substring(input.indexOf(' ') + 1));
							System.err.println("adding User");
							//users.add(player);
							new Thread(player).start();
						}
						catch (IOException e)
						{
							try
							{
								PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
								out.println(e.getMessage());
								out.flush();
								System.err.println("Error: could not run user ~ " + e.getMessage());
								clientSocket.close();
							}
							catch (IOException wtf)
							{
								System.err.println("WTF....");
							}
						}
					}
				}.start();
			}
			catch (IOException kill)
			{
				//if the serversocket spontaneously dies and combusts
				System.err.println("We Crashed and Burned!");
				break;
			}
		}
	}

	/**
	 * Start a CapitalServer running on the specified port.
	 * 
	 * @param port
	 *            The network port on which the server should listen.
	 */
	public static void runServer(int port) throws IOException
	{
		CapitalServer server = new CapitalServer(port);
		server.serve();
	}

	/**
	 * Start a CapitalServer using the given arguments.
	 * 
	 * Usage: CapitalServer [--port PORT]
	 * 
	 * PORT is an optional integer in the range 0 to 65535 inclusive, specifying
	 * the port the server should be listening on for incoming connections. E.g.
	 * "CapitalServer --port 1234" starts the server listening on port 1234.
	 * 
	 */
	public static void main(String[] args)
	{
		int port = 4444; // default port

		Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
		try
		{
			while (!arguments.isEmpty())
			{
				String flag = arguments.remove();
				if (flag.equals("--port"))
				{
					port = Integer.parseInt(arguments.remove());
					if (port < 0 || port > 65535)
						throw new IllegalArgumentException("port " + port + " out of range");
				}
				else
					throw new IllegalArgumentException("unknown option: \"" + flag + "\"");

			}
		}
		catch (IllegalArgumentException iae)
		{
			System.err.println(iae.getMessage());
			System.err.println("usage: CapitalServer [--port PORT]");
			return;
		}
		try
		{
			runServer(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
