/*
 * Creates a human player
 * 
 */

import java.util.*;

public class HumanPlayer implements Player
{
    private ArrayList<ScumCard> hand; //holds the actual held cards 
    private ArrayList<ScumCard> displayHand; //hand that is shown while making selecitons
    private ScumGame game; //the game the player is in
    private int rank; //the rank of the player
    private String name; //the name of the player
    private boolean isDone; //if the player is done playing
    
    
    public HumanPlayer(String n, ScumGame g)
    {
        name = n; //set name
        game = g; //set game
        rank = 0; //reset rank
        hand = new ArrayList<ScumCard>(); //create a new hand
        isDone = true; //no cards -- player is done
    }
    
    public void reset() //resets the player for a new round
    {
        hand.clear(); //clears the hand
        isDone = true; //because the hand is empty
    }
    
    //accessor methods:
    public boolean isDone() 
    {
        return isDone;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getRank()
    {
        return rank;
    }
    
    public ArrayList<ScumCard> getHand()
    {
        return hand;
    }
    
    //sets the rank
    public void setRank(int n)
    {
        rank = n;
    }
    
    //adds a card to hand in order of rank
    public void addCard(ScumCard card)
    {
        int x;
        for (x = 0; x < hand.size(); x++)
            if (hand.get(x).compareToIgnoreSuit(card)>0)
                break;
        hand.add(x,card);
        isDone = false; //because player cant be done after adding a card
    }
    
    //gets the player's move
    /*
     * cards needed = the expected number of cards to return (0 if player can choose how many to return)
     * if returns null, player skips turn
     */
    public ArrayList<ScumCard> getMove(int cardsNeeded)
    {
        displayHand = new ArrayList<ScumCard>(); //sets the hand that will be visually displayed to the controller
        displayHand.addAll(hand); //adds the hand to the display
            System.out.println("------------------------------------");
            System.out.println();
            
            for (Player p : game.getPlayers())
            {
                if (p != this)
                    System.out.print(p.getHand().size() + " ");
            }
            
        if (isDone) //if the player is done, skip the turn automatically
            return null;
        ArrayList<ScumCard> selection = new ArrayList<ScumCard>(); //holds the player's move choice
        while (selection.size() < cardsNeeded || cardsNeeded == 0) //loops to get all the cards picked
        {
            System.out.println();
            displayHand(); //display the hand to player
            System.out.println( "\t" +displayHand.size() + ": SKIP/REMOVE LAST");
            if (cardsNeeded == 0 && selection.size() != 0)
                System.out.println( "\t" +(displayHand.size()+1) + ": DONE PICKING");
            displayLastMove(); //display the game's last move
            System.out.println("Current selection: ");
            if (selection.size() == 0)
                System.out.println("\t nothing selected...");
            for (ScumCard c : selection)
                System.out.println( "\t" + c);
            System.out.println();
            int c = makeMove(); //gets the player's choice to move
            if (cardsNeeded == 0 && selection.size() != 0 && c == displayHand.size()+1) //if player is done choosing
                return selection;
            else if (c < displayHand.size() && c >= 0) //updates display
                selection.add(displayHand.remove(c));
            else if (selection.size() != 0 && c == displayHand.size()) //updates display
            {
                ScumCard card = (selection.remove(selection.size() -1));
                int x;
                for (x = 0; x < displayHand.size(); x++)
                    if (displayHand.get(x).compareToIgnoreSuit(card)>0)
                        break;
                displayHand.add(x,card);
            }
            else if (cardsNeeded != 0 && selection.size() == 0 && c == displayHand.size()) //player chooses to skip turn
                return null;
        }
        return selection;
    }
    
    private void displayHand() //displays the hand to the controller
    {
        System.out.println(name +", Your hand:");
        for (int x = 0; x < displayHand.size(); x++)
            System.out.println(" \t" + x + ": "+ displayHand.get(x));
    }   
    
    private void displayLastMove() //displays the last player's move
    {
        System.out.println("Discard Pile:");
        ArrayList<ScumCard> move = game.getLastMove();
        int number;
        String value;
        if (move.size() == 0)
            System.out.println(" \t nothing in discard...");
        else
        {
            System.out.print(" \t");
            number = move.size();
            value = move.get(0).getValueName();
            switch (number)
            {
                case 1:
                    System.out.println("Single " + value);
                    break;
                case 2:
                    System.out.println("Double " + value);
                    break;
                case 3:
                    System.out.println("Triple " + value);
                    break;
                case 4:
                    System.out.println("Quad " + value);
                    break;
            }
        }
    }
    
    private int makeMove() //gets a controller's move index
    {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter selection number: ");
        int picked = kb.nextInt();
        return picked;
    }
    
    public void removeCard(ScumCard c) //removes a card from the hand
    {
        hand.remove(hand.indexOf(c));
        if (hand.size() == 0) //check if player is done
            isDone = true; 
    }
    
    public String toString() // prints the player and all related material
    {
        String answer = "";
        answer = name+":\n";
        for (ScumCard c : hand)
        {
            answer = answer + "\t" + c + "\n";
        }
        
        answer = answer + "\n Rank = " + rank + "\n";
        return answer;
    }
    
    public int findIndexMax()//finds index of highest card in hand
    {
        if(hand.size()==0)
            return -1;
        int maxIndex = 0;
        for(int i=0;i<hand.size();i++)
            if(hand.get(i).compareToIgnoreSuit(hand.get(maxIndex)) >= 0)
                maxIndex=i;
        return maxIndex;
    }
    
    public int findIndexMin()//finds index of lowest card in hand
    {
        if(hand.size()==0)
            return -1;
        int maxIndex = 0;
        for(int i=0;i<hand.size();i++)
            if(hand.get(i).compareToIgnoreSuit(hand.get(maxIndex)) <= 0 && hand.get(i).compareToIgnoreSuit(new ScumCard(0,1)) != 0)
                maxIndex=i;
        return maxIndex;
    }
}