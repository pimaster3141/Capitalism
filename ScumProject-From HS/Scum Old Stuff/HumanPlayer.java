/*
 * Creates a human player
 * 
 */

import java.util.*;

public class HumanPlayer implements Player
{
    private ArrayList<ScumCard> hand; //holds the actual held cards 
    private ArrayList<ScumCard> displayHand; //hand that is shown while making selecitons
    private ScumGame game;
    private int rank;
    private String name;
    private boolean isDone; //if the player is done playing
    
    
    public HumanPlayer(String n, ScumGame g)
    {
        name = n;
        game = g;
        rank = 0;
        hand = new ArrayList<ScumCard>();
        isDone = true;
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
    
    //sets the rank
    public void setRank(int n)
    {
        rank = n;
    }
    
    public ArrayList<ScumCard> getHand()
    {
        return hand;
    }
    
    //adds a card to hand in order of power
    public void addCard(ScumCard card)
    {
        int x;
        for (x = 0; x < hand.size(); x++)
            if (hand.get(x).compareToIgnoreSuit(card)>0)
                break;
        hand.add(x,card);
        isDone = false;
    }
    
    public ArrayList<ScumCard> getMove(int cardsNeeded)
    {
        displayHand = new ArrayList<ScumCard>();
        displayHand.addAll(hand);
            System.out.println("------------------------------------");
            System.out.println();
            
        if (isDone)
            return null;
        ArrayList<ScumCard> selection = new ArrayList<ScumCard>();
        while (selection.size() < cardsNeeded || cardsNeeded == 0)
        {
            System.out.println();
            displayHand();           
            System.out.println( "\t" +displayHand.size() + ": SKIP/REMOVE LAST");
            if (cardsNeeded == 0 && selection.size() != 0)
                System.out.println( "\t" +(displayHand.size()+1) + ": DONE PICKING");
            displayLastMove();
            System.out.println("Current selection: ");
            if (selection.size() == 0)
                System.out.println("\t nothing selected...");
            for (ScumCard c : selection)
                System.out.println( "\t" + c);
            System.out.println();
            int c = makeMove();
            if (cardsNeeded == 0 && selection.size() != 0 && c == displayHand.size()+1)
                return selection;
            else if (c < displayHand.size() && c >= 0)
                selection.add(displayHand.remove(c));
            else if (selection.size() != 0 && c == displayHand.size())
            {
                ScumCard card = (selection.remove(selection.size() -1));
                int x;
                for (x = 0; x < displayHand.size(); x++)
                    if (displayHand.get(x).compareToIgnoreSuit(card)>0)
                        break;
                displayHand.add(x,card);
            }
            else if (cardsNeeded != 0 && selection.size() == 0 && c == displayHand.size())
                return null;
        }
        return selection;
    }
    
    private void displayHand()
    {
        System.out.println(name +", Your hand:");
        for (int x = 0; x < displayHand.size(); x++)
            System.out.println(" \t" + x + ": "+ displayHand.get(x));
    }   
    
    private void displayLastMove()
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
    
    private int makeMove()
    {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter selection number: ");
        int picked = kb.nextInt();
        return picked;
    }
    
    public void removeCard(ScumCard c)
    {
        hand.remove(hand.indexOf(c));
        if (hand.size() == 0)
        isDone = true;
    }
    
    public String toString()
    {
        String answer = "";
        answer = name+":\n";
        for (ScumCard c : hand)
        {
            answer = answer + c + "\n";
        }
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