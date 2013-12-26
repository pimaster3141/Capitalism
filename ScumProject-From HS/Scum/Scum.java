import java.util.*;

public class Scum
{
    private static ArrayList<Player> players;
    private static int numDecks;
    private static Scanner kb = new Scanner(System.in);
    private static ScumGame game;
    
    public static void setupGame()
    {
        System.out.print("How many Decks?: ");
        numDecks = kb.nextInt();
        game = new ScumGame(players, numDecks);
    }
        
    
    public static void setupPlayers()
    {
        System.out.print("How many human players?: ");
        int numPlayers = kb.nextInt();
        kb.nextLine();
        for (int x = 0; x < numPlayers; x++)
        {
            System.out.print("Enter name of Player "+(x+1)+": ");
            String name = kb.nextLine();
            Player p = new HumanPlayer(name, game);
            players.add(p);
        }
        
        System.out.print("\nHow Many Computer Players?: ");
        numPlayers = kb.nextInt();
        kb.nextLine();
        for (int x = 1; x <= numPlayers; x++)
            players.add(new ComputerPlayer("Computer " + x, game));
    }

    public static void main(String [] args)
    {
        /*
        ArrayList<Player> temp= new ArrayList<Player>();
        
        ScumGame game = new ScumGame(players, 2);
        Player a = new HumanPlayer("A", game);
        Player b = new HumanPlayer("B", game);
        Player c = new HumanPlayer("C", game);
        Player d = new HumanPlayer("D", game);
        
        Player a = new ComputerPlayer("A", game);
        Player b = new ComputerPlayer("B", game);
        Player c = new ComputerPlayer("C", game);
        Player d = new ComputerPlayer("D", game);
        Player e = new ComputerPlayer("E", game);
        Player f = new ComputerPlayer("F", game);
        Player g = new ComputerPlayer("G", game);
        Player h = new ComputerPlayer("H", game);
        Player i = new ComputerPlayer("I", game);
        Player j = new ComputerPlayer("J", game);
        temp.add(a);
        temp.add(b);
        temp.add(c);
        temp.add(d);
        temp.add(e);
        temp.add(f);
        temp.add(g);
        temp.add(h);
        temp.add(i);
        temp.add(j);
            */
        players = new ArrayList<Player>();
        setupGame();
        setupPlayers();
        boolean playAgain = true;
        while(playAgain)
        {
            game.playScum();
            System.out.println("Player states: ");
            for (Player p : game.getPlayers())
            {
                System.out.println(p);
                System.out.println();
            }
            System.out.print("Play again? (Y/N): ");
            String str = kb.nextLine();
            str.trim().toLowerCase();
            if (str.equals("n"))
                playAgain = false;
        }
        
        /*
       
        
        String x = "";
        Scanner kb = new Scanner(System.in);
        while(x.equals(""))
        {
            game.playScum();
            x = kb.nextLine();
        }
        */
       
    }
}