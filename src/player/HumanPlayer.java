package player;

import game.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lists.GameList;
import cards.Card;

public class HumanPlayer extends Player
{
	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;
	private final LinkedBlockingQueue<String> outputBuffer = new LinkedBlockingQueue<String>();
	private final Thread outputConsumer;
	private boolean alive = true;
	private final GameList games;
	
	public HumanPlayer(Socket socket, String username, GameList games) throws IOException
	{
		super(username);
		this.games = games;
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
		String regex = "(disconnect)|(((make)|(join)|(leave)) \\p{Graph}+)|((move)( [2-9JQKA][HSCD])*)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		
		//check if input is valid
		if(!m.matches())
			return ("Unrecognized Command: " + input);
		
		//parse first token of string
		int spaceIndex =  input.indexOf(' ');
		String command = input.substring(0, spaceIndex);
		
		//interpret command
		if (command.equals("disconnect"))
		{
			//TODO - trigger disconnect
			return ("disconnect success");
		}
		
		String arguments = input.substring(spaceIndex + 1);
		
		if (command.equals("make"))
			//TODO - trigger make room
			return "Something to say you made a room properly: " + arguments;
		
		if (command.equals("join"))
		{
			try 
			{
				this.joinGame(games.getGameFromName(arguments));
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				return e1.getMessage();
			}
			return "Something to say you joined a room properly: " + arguments;
		}
		
		if (command.equals("leave"))
		{
			//TODO - trigger leave room
			this.leaveGame();
			return "Something to say you left a room properly: " + arguments;
		}
		
		if (command.equals("move"))
		{
			String[] moveArray = arguments.split(" ");
			ArrayList<Card> cards = new ArrayList<Card>(moveArray.length);
			Move move;
			for(String s : moveArray)
			{
				try
				{
					cards.add(new Card(s));
				}
				catch(IOException e)
				{
					return e.getMessage();
				}
			}
			try 
			{
				move = new Move(this, cards);
			} 
			catch (IOException e)
			{
				return e.getMessage();
			}
			try 
			{ 
				this.makeMove(move);
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				return e.getMessage();
			}
			
			return "Something to say woot you didnt throw an exeption when making a move " + arguments;
		}
		
		return "Congrats, you somehow got to this place where you arent supposed to.... good job";
	}
	
	/*
	 * define any output filter/triggers here
	 */
	private void parseOutput(String output)
	{
		if (output.equals(""))
			return;
		System.out.println("Client: " + name + " - sending - " + output);
		out.println(output);
		out.flush();
		return;
	}

	/*
	 * method for other things to send messages to this client
	 * adds the string to a buffer to be consumed when ready. this frees the
	 * sender to do other things and not wait for a slow connection.
	 * 
	 * @param
	 *  String - message to be sent to the client
	 */
	public void  updateQueue(String info)
	{
		outputBuffer.add(info);
	}

	@Override
	protected void processState(GameState s) {
		// TODO Auto-generated method stub
		
	}
		

}
