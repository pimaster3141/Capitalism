// the player interface that must be extended for players to play. 

import java.util.*;

public interface Player
{
    void reset(); //resets player for each round
    String getName(); //returns the name of the player
    int getRank(); //gets the rank of the player (+2/+1 for Prez/Vice, -2/-1 for Scum/ViceScum)
    void setRank(int n); //sets the current rank
    ArrayList<ScumCard> getHand(); //returns the cards held
    void addCard(ScumCard card); //adds the card to hand
    ArrayList<ScumCard> getMove(int numCards); //gets the player's move
    boolean isDone(); //returns if the player is done for the round
    void removeCard(ScumCard c); //removes the card from hand.
	int findIndexMin();//finds index of lowest card in hand
	int findIndexMax();//finds index of highest card in hand
}