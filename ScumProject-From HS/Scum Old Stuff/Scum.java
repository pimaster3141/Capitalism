import java.util.*;

public class Scum
{
    static private ScumGame game;
    
    public static void main(String[]args)
    {
        ArrayList<Player> players = new ArrayList<Player>();
        for (int x = 0; x < 4; x++)
        {
            Player p = new HumanPlayer("Test "+ x);
            players.add(p);
        }
        
        game = new ScumGame(players);
        game.playScum();
    }
}
        