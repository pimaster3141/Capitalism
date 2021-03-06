package player;

import game.Game;
import game.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lists.GameList;
import lists.ServerUserList;
import lists.UserList;
import cards.Card;
/** 
 * 
 * Implements a human player extension of player (adds support for TCP/IP players)
 *
 */
public class HumanPlayer extends Player
{
	private final Socket socket;		//Socket to connect to
	private final BufferedReader in;	//input stream from socket
	private final PrintWriter out;		//output stream to socket 
	private final LinkedBlockingQueue<String> outputBuffer = new LinkedBlockingQueue<String>();	//input buffer from remote client
	private final Thread outputConsumer;	//consumer for above buffer
	private boolean alive = true;		//boolean if this player is still functioning
	private final GameList games;		//directory of active games on this server
	private final UserList users;
	
	/**
	 * Constructor for a human player
	 * @param socket - socket to bind to
	 * @param username - name of player
	 * @param games - global directory of games on server
	 * @throws IOException - if the player cannot be created like if your socket has problems
	 */
	public HumanPlayer(Socket socket, String username, GameList games, UserList users) throws IOException
	{
		super(username);
		this.games = games;
		this.users = users;
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
			this.cleanup();
			
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

	/**
	 * Handles cleaning up the connection - removing from games, and server, 
	 */
	private void cleanup()
	{
		this.leaveGame();

		// removes the user from the server
		System.out.println("Client: " + name + " - " + "Removing from server listing");
		users.remove(this);
		return;
	}

	/**
	 * Filter and parser for the input stream
	 * MUST HANDLE EXCEPTIONS HERE
	 * @param input - input string to parse
	 * @return - string to be sent back to client regarding input
	 */
	private String parseInput(String input)
	{
		//define regex to parse input
		//String regex = "(disconnect)|(((make)|(join)|(leave)) \\p{Graph}+)|((move)( [2-9JQKA][HSCD])*)";
		String regex = "(disconnect)|"
				+ "(((join)|(leave)) \\p{Graph}+)|"
				+ "((make) \\p{Graph}+ [1-9][0-9]* [1-9][0-9]*)|"
				+ "((move)( [2-9JQKA][HSCD])*)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		
		//check if input is valid
		if(!m.matches())
			return ("Unrecognized Command: " + input);
		
		String[] commands = input.split(" ");
		
		//interpret commands below
		//DISCONNECT
		if (commands[0].equals("disconnect"))
			return this.disconnect();
		
		//MAKE ROOM 
		if (commands[0].equals("make"))
			return this.makeRoom(commands);
		
		//JOIN ROOM
		if (commands[0].equals("join"))
			return this.joinRoom(commands);
		
		if (commands[0].equals("leave"))
			return this.leaveRoom(commands);
		
		//MAKE A MOVE
		if (commands[0].equals("move"))
			return this.sendMove(commands);
		
		//if you get here, then you are really pro at breaking code... good job. 
		return "Congrats, you somehow got to this place where you arent supposed to.... good job";
	}
	
	private String disconnect()
	{
		//TODO - trigger disconnect
		this.alive = false;
		return ("disconnect success");
	}
	
	private String makeRoom(String[] commands)
	{
		try
		{
			int numHuman = Integer.parseInt(commands[2]);
			int numDecks = Integer.parseInt(commands[3]);
			Game g = new Game(commands[1], games, numHuman, numDecks);
			games.add(g);
			g.start();
			this.joinGame(g);
		}
		catch (IOException e)
		{
			return e.getMessage();
		}
		return "Something to say you made a room properly: " + Arrays.deepToString(commands);
	}
	
	private String joinRoom(String[] commands)
	{
		try 
		{
			
			this.joinGame(games.getGameFromName(commands[1]));
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			return e1.getMessage();
		}
		return "Something to say you joined a room properly: " + Arrays.deepToString(commands);
	}
	
	private String leaveRoom(String[] commands)
	{
		//TODO - trigger leave room
		this.leaveGame();
		return "Something to say you left a room properly: " + Arrays.deepToString(commands);
	}
	
	private String sendMove(String[] commands)
	{
		//split incoming card strings
		String[] arguments = Arrays.copyOfRange(commands, 1, commands.length);
		
		System.out.println(Arrays.deepToString(arguments));
		//create new cards in array from string
		ArrayList<Card> cards = new ArrayList<Card>(arguments.length);
		Move move;
		for(String s : arguments)
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
		//try to create a new move from the cardList 
		try 
		{
			move = new Move(this, cards);
		} 
		catch (IOException e)
		{
			return e.getMessage();
		}
		//try to push the move to the game
		try 
		{ 
			this.makeMove(move);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		
		return "Something to say woot you didnt throw an exeption when making a move " + Arrays.deepToString(arguments);
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

	/**
	 * something to update the player about what is going on with the game
	 */
	protected void processState(GameState s) {
		// TODO Auto-generated method stub
		
	}
		

}
