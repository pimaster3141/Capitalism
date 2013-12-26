/*
 * Controls the rules and computation of the scum game
 * general rules: 
 *  first person to lose all of thier cards wins
 *  highest card that can be played is a 2
 *  in each round, players take turns and put down higher cards than that on the discard pile; if no card can be played, the player skips his/her turn
 *      if all the players pass, the discard pile is cleared and the last person to play goes again
 *  players can play doubles and triples but all subsequent players must also do so in order to continue to play
 *  the winner is president, second is vice, second to last is viceScum and last is scum
 *      before the beginning of a new round, players will trade cards in favor of the president -- pres+scum will trade 2 cards and vice/viceScum, 1 card
 */

import java.util.*;

public class ScumGame
{
    private ScumDeck myDeck; //the deck to be dealt
    private ArrayList<Player> players; //list of players in game
    private ArrayList<ScumCard> discardPile; //pile that cards are put on 
    private ArrayList<ScumCard> lastMove; //holds the top set of cards on the discard pile from last player.
    private int numCardsPlay; //number of cards to play each round (doubles, triples, ect), 0 if the player can choose how many cards to play
    private int numberOfDecks; //number of decks the game will be played with
    private int currentPlayer; //index of the current player in the current round
    private int skippedPlayers; //number of skipped players
    private boolean done; //if this round is done yet (all players done)
    private boolean firstRound; //if this is the first round of the game -- used for determining who starts the round
    
    public ScumGame(ArrayList<Player> p, int numDecks) //constructor 
    {
        discardPile = new ArrayList<ScumCard>(); //make a new discard pile
        lastMove = new ArrayList<ScumCard>(); //make a new list to hold the last move played
        skippedPlayers = 0; //reset the skipped player count
        players = p; //add all the players to the game 
        numberOfDecks = numDecks;
        firstRound = true; //sets up for the first round
    }
    
    public void reset() //resets game prior to a new round
    {
        myDeck = new ScumDeck();
        myDeck.clear();
        for (Player p : players) //resets all the players
            p.reset();
        for (int x = 0; x < numberOfDecks; x++)
        {
            ScumDeck temp = new ScumDeck();
            for (ScumCard c : temp.getDeck())
                myDeck.add(c); //creates a new deck
        }
        myDeck.shuffle(); //shuffles the deck
        deal(); //deals out the deck evenly as possible
        clearDiscard(); //clears the discard pile
        lastMove.clear();
        done = false; //the game has started
    }
    
    //deals out cards as evenly as possible.
    private void deal() //deals a deck
    {
        for (int x = 0; myDeck.getSize() > 0; x++) //for each card:
        {
            players.get(x%players.size()).addCard(myDeck.deal()); //give one card to each player in order
        }
    }  
    
    public boolean isDone() //returns if the game is done
    {
        if (!done) // if the game is not done, check if it has ended
            for(Player p : players) //for each player
                if (!p.isDone()) //if the player is not done
                    return done; //the game has not ended
        done = true; //else, the game is over
        return done;
    }
    
    public ArrayList<ScumCard> getLastMove() //returns the last move played
    {
        return lastMove;
    }        
    
    public ArrayList<Player> getPlayers() //returns the players in the game
    {
        return players;
    }
    
    public ArrayList<ScumCard> getDiscardPile() //returns the entire discard pile
    {
        return discardPile;
    }
    
    private int findThreeClubs() //finds the index of the person holding the 3 of clubs to start the game
    {
        ScumCard check = new ScumCard(1,2); //card to compare to
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getHand().indexOf(check) != -1) //check each hand
                break;
        return x;
    }
    
    private int findPresident() //index of prez
    {
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getRank() == 2)
                return x;
        return -1;
    }  
    
    private int findVice() //index of vice
    {
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getRank() == 1)
                return x;
        return -1;
    }
    
    private int findViceScum() //index of vice scum
    {
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getRank() == -1)
                return x;
        return -1;
    }
    
    private int findScum() //index of scum
    {
        int x;
        for (x = 0; x < players.size(); x++)
            if(players.get(x).getRank() == -2)
                return x;
        return -1;
    }
    
    private void trade (Player recieve, Player send) //trades the best card of the scum(send) with the worst card of the prez(recieve)
    {
        ScumCard c = send.getHand().get(send.findIndexMax());
        //System.out.print("Trading: " + c + " For: ");
        send.removeCard(c);
        recieve.addCard(c);
        c = recieve.getHand().get(recieve.findIndexMin());
        recieve.removeCard(c);
        //System.out.println(c);
        send.addCard(c);
    }
    
    private void setupRound() //sets up each round right before game time
    {
        reset(); //resets and deals the card related variables
        skippedPlayers = 0; //resets the skipped players
        if (firstRound) //if this is the first round -- find the 3 of clubs holder
        {
            firstRound = false;
            currentPlayer = findThreeClubs();
        }
        else //if not -- trade the cards and set the first player as the president
        {
            trade(players.get(findPresident()), players.get(findScum()));
            trade(players.get(findPresident()), players.get(findScum()));
            trade(players.get(findVice()), players.get(findViceScum()));
            currentPlayer = findPresident();
            //System.out.println(players.get(currentPlayer));
        }
        
        /*
        for (Player p : players)
        {
            System.out.println(p);
            System.out.println();
        }
        */
        
        for (Player p : players) //reset the rank of each player
        {
            p.setRank(-10);
        }
    }
    
    private boolean isValid(ArrayList<ScumCard> c) //returns if the move (c) is a valid move
    {
        if (c == null) //if null, the player intends to skip their turn
        {
            //System.out.println(" Skipping your turn...");
            return true;
        }
        if (c.size() != numCardsPlay && numCardsPlay != 0) //if the not the correct number of cards, return false
        {
            System.err.println("not enough cards");
            return false;
        }
        if (c.size() > 1) //checks if the multi card play has cards of the same value
            for (int x = 0; x < c.size()-1; x++)
                if (c.get(x).compareToIgnoreSuit(c.get(x+1)) != 0)
                {
                    System.err.println("not same cards");
                    return false;
                }
        if (discardPile.size() != 0 && discardPile.get(discardPile.size()-1).compareToIgnoreSuit(c.get(0)) >= 0) // checks if the card played is of greater value than the top of discard pile
        {
            System.err.println("lesser value played");
            return false;
        }
        return true;
    }
    
    private void clearDiscard() //clears the discard pile if everyone skips and resets the related variables
    {
        discardPile.clear(); //clears the pile
        numCardsPlay = 0; //sets the next player to play as many cards as they want
        skippedPlayers = 0; //no skipped players
    }
    
    private void setRank(Player p) //assigns ranks to each player as they finish
    {
        int counter = 0;
        if (p.getRank() != -10) //if the player already has a rank:
            return; //dont do anything
        for (Player x : players) //cont how many players are done
        {
            if (x.isDone())
                counter ++;
        }
        //sets rank based on how many players are done
        if (counter == 1)
            p.setRank(2); //prez
        else if (counter == 2)
            p.setRank(1); //vice
        else if (counter == players.size() -1)
            p.setRank(-1); //vice scum
        else if (counter == players.size())
            p.setRank(-2); //scum
        else 
            p.setRank(0); //neutral
    }
    
    public void playScum() //main method to play one round of scum
    {
        setupRound(); //init round        
       
        while (!isDone()) //while the game is going
        {
            ArrayList<ScumCard> move = new ArrayList<ScumCard>(); //the players choice for a move
            if (discardPile.size() == 0) //if the discard pile is empty
            {
                while (players.get(currentPlayer).isDone()) //finds the first avalible player
                    currentPlayer = (currentPlayer+1)%players.size();
                do
                {
                    move = players.get(currentPlayer).getMove(0);
                    //System.err.println(move);
                }
                while (!isValid(move) || move == null); //keep asking for moves until a valid move is played (no skipping)
                numCardsPlay = move.size(); //sets up how many cards each person should play in this round
            }
            else //if the discard pile is not empty
            {
                do
                    move = players.get(currentPlayer).getMove(numCardsPlay); //gets a move from the player
                while (!isValid(move)); //checks if it is valid
            }
            
            if (move == null) //if the player is skipping
            {
                skippedPlayers++; //increment the skipping counter
                if (skippedPlayers >= players.size() -1) //if all the players have skipped
                {
                    lastMove.clear(); //clear the last move
                    clearDiscard(); //clear the discard pile
                }
            }
            
            else //if not skipping
            {
                skippedPlayers = 0; //reset the skip counter
                lastMove.clear(); //reset the last move variable
                for (ScumCard c : move) //for each card:
                {
                    lastMove.add(c); //add to the last move
                    discardPile.add(c); //add the card to the discard pile
                    players.get(currentPlayer).removeCard(c); //remove the card from the player's hand
                }
                
                if (players.get(currentPlayer).isDone())//if the player is done
                    setRank(players.get(currentPlayer)); //set their rank
            
                if (move.get(0).getRank() == 12)
                {
                    lastMove.clear();
                    clearDiscard();
                    currentPlayer --;
                }
            }
            currentPlayer = (currentPlayer+1)%players.size(); //increment the players
        }
    }
}      