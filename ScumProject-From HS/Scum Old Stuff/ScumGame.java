/*
 * Controls the rules and computation of the scum game
 */

import java.util.*;

public class ScumGame
{
    private ScumDeck myDeck; //the deck to be dealt
    private ArrayList<Player> players; //list of players in game
    private ArrayList<ScumCard> discardPile; //pile that cards are put on 
    private ArrayList<ScumCard> lastMove; //holds the top set of cards on the discard pile from last player.
    private int numCardsPlay; //number of cards to play each round (doubles, triples, ect)
    private int currentPlayer;
    private int skippedPlayers;
    private boolean done;
    private boolean firstRound;
    
    public ScumGame(ArrayList<Player> p)
    {
        discardPile = new ArrayList<ScumCard>();
        lastMove = new ArrayList<ScumCard>();
        skippedPlayers = 0;
        players = p;
        firstRound = true;
    }
    
    public void reset() //resets game for new round
    {
        myDeck = new ScumDeck(); //creates a new deck
        //removes jokers
        //myDeck.remove(new Card(4,13));
        //myDeck.remove(new Card(4,14));
        myDeck.shuffle();
        for (Player p : players)
            p.reset();
        deal();
        clearDiscard();
        done = false;
    }
    
    //deals out cards as evenly as possible.
    private void deal()
    {
        for (int x = 0; myDeck.getSize() > 0; x++)
        {
            players.get(x%players.size()).addCard(myDeck.deal());
        }
    }  
    
    public boolean isDone()
    {
        if (!done)
            for(Player p : players)
                if (!p.isDone())
                    return done;
        done = true;
        return done;
    }
    
    public ArrayList<ScumCard> getLastMove()
    {
        return lastMove;
    }        
    
    public ArrayList<Player> getPlayers()
    {
        return players;
    }
    
    public ArrayList<ScumCard> getDiscardPile()
    {
        return discardPile;
    }
    
    private int findThreeClubs()
    {
        ScumCard check = new ScumCard(1,2);
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getHand().indexOf(check) != -1)
                break;
        return x;
    }
    
    private int findPresident()
    {
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getRank() == 2)
                return x;
        return -1;
    }  
    
    private int findVice()
    {
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getRank() == 1)
                return x;
        return -1;
    }
    
    private int findViceScum()
    {
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getRank() == -1)
                return x;
        return -1;
    }
    
    private void trade (Player recieve, Player send)
    {
        ScumCard c = send.getHand().get(send.findIndexMax());
        send.removeCard(c);
        recieve.addCard(c);
        c = recieve.getHand().get(recieve.findIndexMin());
        recieve.removeCard(c);
        send.addCard(c);
    }
    
    private int findScum()
    {
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getRank() == -2)
                return x;
        return -1;
    }
    
    private void setupRound()
    {
        reset();
        skippedPlayers = 0;
        if (firstRound)
        {
            firstRound = false;
            currentPlayer = findThreeClubs();
        }
        else
        {
            trade(players.get(findPresident()), players.get(findScum()));
            trade(players.get(findPresident()), players.get(findScum()));
            trade(players.get(findVice()), players.get(findViceScum()));
            currentPlayer = findPresident();
        }
        
        for (Player p : players)
        {
            p.setRank(-10);
        }
    }
    
    private boolean isValid(ArrayList<ScumCard> c)
    {
        if (c == null)
        {
            System.out.println(" Skipping your turn...");
            return true;
        }
        if (c.size() != numCardsPlay && numCardsPlay != 0)
        {
            System.out.println("not enough cards");
            return false;
        }
        if (c.size() > 1)
            for (int x = 0; x < c.size()-1; x++)
                if (c.get(x).compareToIgnoreSuit(c.get(x+1)) != 0)
                {
                    System.out.println("not same cards");
                    return false;
                }
        if (discardPile.size() != 0 && discardPile.get(discardPile.size()-1).compareToIgnoreSuit(c.get(0)) >= 0)
        {
            System.out.println("lesser value played");
            return false;
        }
        return true;
    }
    
    private void clearDiscard()
    {
        discardPile.clear();
        numCardsPlay = 0;
        skippedPlayers = 0;
    }
    
    private void setRank(Player p)
    {
        int counter = 0;
        if (p.getRank() != -10)
            return;
        for (Player x : players)
        {
            if (x.isDone())
                counter ++;
        }
        
        if (counter == 0)
            p.setRank(2);
        else if (counter == 1)
            p.setRank(1);
        else if (counter == players.size() -1)
            p.setRank(-1);
        else if (counter == players.size())
            p.setRank(-2);
        else 
            p.setRank(0);
    }
    
    public void playScum()
    {
        setupRound();
        while (!isDone())
        {
            ArrayList<ScumCard> move = new ArrayList<ScumCard>();
            if (discardPile.size() == 0)
            {
                do
                    move = players.get(currentPlayer).getMove(0);
                while (!isValid(move) && move != null);
                numCardsPlay = move.size();
            }
            else
            {
                do
                    move = players.get(currentPlayer).getMove(numCardsPlay);
                while (!isValid(move));
            }
            
            if (move == null)
            {
                skippedPlayers++;
                if (skippedPlayers >= players.size() -1)
                {
                    lastMove.clear();
                    clearDiscard();
                }
            }
            else
            {
                skippedPlayers = 0;
                lastMove.clear();
                for (ScumCard c : move)
                {
                    lastMove.add(c);
                    discardPile.add(c);
                    players.get(currentPlayer).removeCard(c);
                }
                if (players.get(currentPlayer).isDone())
                    setRank(players.get(currentPlayer));
            }
            currentPlayer = (currentPlayer+1)%players.size();
        }
    }
    
    public static void main(String [] args)
    {
        ArrayList<Player> temp= new ArrayList<Player>();
        
        ScumGame game = new ScumGame(temp);
        Player a = new HumanPlayer("A", game);
        Player b = new HumanPlayer("B", game);
        Player c = new HumanPlayer("C", game);
        Player d = new HumanPlayer("D", game);
        temp.add(a);
        temp.add(b);
        temp.add(c);
        temp.add(d);
        
        game.reset();
        
        game.playScum();
    }
}      