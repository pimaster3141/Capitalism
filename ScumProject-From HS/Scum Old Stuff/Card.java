/*
 * Stores information about a particular card; ace low
 * 
 * suit and rank adjusted using modulo math
 * 
 * suit:
 *  0 = diamonds
 *  1 = clubs
 *  2 = hearts
 *  3 = spades
 *  4 = jokers
 *  
 * rank:
 *  0-9 = A-10
 *  10 = Jack
 *  11 = Queen
 *  12 = King
 *  13 = joker1
 *  14 = joker2
 * 
 * 
 * methods in this class:
 *  getSuit
 *  getRank
 *  getCardNumber
 *  getImage
 *  getBack
 *  isFaceUp
 *  
 *  flipCard
 *  toString
 *  equals
 *  compareTo
 *  compareToIgnoreCase
 */
import javax.swing.*;

public class Card
{
    //fields for card info
    private final int suit;
    private final int rank;
    private boolean faceUp; //if the card is face down or face up
    private Icon image; // image of card
    private static final Icon CARD_BACK = new ImageIcon("Cards/b1fv.png");
    
    public Card(int s, int r)
    {
        suit = s;
        rank = r;
        faceUp = false; // starts face down
        image = new ImageIcon("Cards/"+suit+rank+".png"); //assigns new images
    }
    
    //accessor methods
    public int getSuit()
    {
        return suit;
    }
    
    public int getRank()
    {
        return rank;
    }
    
    public int getCardNumber() //returns the real card number(not modded for arrays)
    {
        return rank + 1;
    }
    
    public Icon getImage()
    {
        if (faceUp)
            return image;
        else
            return getBack();
    }
    
    public static Icon getBack()
    {
        return CARD_BACK;
    }
    
    public boolean isFaceUp()
    {
        return faceUp;
    }
    
    public void flipCard() //flips card over
    {
        faceUp = !faceUp;
    }
    
    public String toString() //prints card name
    {
        String result = "";
        
        switch (suit)
        {
            case 0:
                result = "Daimonds ";
                break;
            case 1:
                result = "Clubs ";
                break;
            case 2:
                result = "Hearts ";
                break;
            case 3:
                result = "Spades ";
                break;
            default:
                break;
        }
        
        switch (rank)
        {
            case 0:
                result = result + "A";
                break;
            case 10: 
                result = result + "J";
                break;
            case 11:
                result = result + "Q";
                break;
            case 12:
                result = result + "K";
                break;
            case 13:
            case 14:
                result = "Joker";
                break;
            default:
                result = result + (rank+1);
                break;
        }
        return result;
    }
    
    public boolean equals(Object other) //compares rank and suit
    {
        if (other != null)
        {
            Card compare = (Card)other;
            return rank==compare.getRank() && suit==compare.getSuit();
        }
        else
            return false;
    }
    
    public int compareTo(Object other) //compares suit then rank
    {
        Card compare = (Card)other;
        return (suit-compare.getSuit())*100 + (rank-compare.getRank());
    }
    
    public int compareToIgnoreSuit(Object other) //compares only rank
    {
        Card compare = (Card)other;
        return (rank-compare.getRank());
    }
}         
