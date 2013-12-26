/*
 * Stores information about a particular card; ace low
 * 
 * suit and rank adjusted using modulo math
 * 
 * suit:
 *  0 = daimonds
 *  1 = clubs
 *  2 = hearts
 *  3 = spades
 *  4 = jokers
 *  
 * rank:
 *  0-7 = 3-10
 *  8 = Jack
 *  9 = Queen
 *  10 = King
 *  11 = Ace
 *  12 = 2
 * 
 */
import javax.swing.*;

public class ScumCard
{
    //fields for card info
    private final int suit;
    private final int rank;
    private final int number;
    private boolean faceUp; //if the card is face down or face up
    private final Icon image; // image of card
    private static final Icon CARD_BACK = new ImageIcon("Cards/b1fv.png");
    
    public ScumCard(int s, int r)
    {
        image = new ImageIcon("Cards/"+s+r+".png");
        suit = s;
        number = r+1;
        faceUp = false; // starts face down
        //Adjusts ranks for a game of scum
        if (r == 0)
            rank = 11;
        else if (r == 1)
            rank = 12;
        else
            rank = r - 2;
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
    
    public int getCardNumber()
    {
        return number;
    }
    
    public String getValueName()
    {
        switch (rank)
        {
            case 8:
                return "J";
            case 9:
                return "Q";
            case 10: 
                return "K";
            case 11:
                return "A";
            case 12:
                return "2";
            default:
                return ("" + (rank+3));
        }
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
                result = "Diamonds ";
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
            case 8:
                result = result + "J";
                break;
            case 9:
                result = result + "Q";
                break;
            case 10: 
                result = result + "K";
                break;
            case 11:
                result = result + "A";
                break;
            case 12:
                result = result + "2";
                break;
            default:
                result = result + (rank+3);
                break;
        }
        return result;
    }
    
    public boolean equals(Object other) //compares rank and suit
    {
        if (other != null)
        {
            ScumCard compare = (ScumCard)other;
            return rank==compare.getRank() && suit==compare.getSuit();
        }
        else
            return false;
    }
    
    public int compareTo(Object other) //compares suit then rank
    {
        ScumCard compare = (ScumCard)other;
        return (suit-compare.getSuit())*100 + (rank-compare.getRank());
    }
    
    public int compareToIgnoreSuit(Object other) //compares only rank
    {
        ScumCard compare = (ScumCard)other;
        return (rank-compare.getRank());
    }
}