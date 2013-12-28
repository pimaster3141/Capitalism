package cards;

/**
 * Class for representing cards in game. 
 * Immutable. 
 *
 */
public class Card implements Comparable<Card>
{
    /**
     * Enum type for defining the four suits of a card 
     * (and the joker if you want). 
     */
    public enum Suit{
        CLUB, DIAMOND, HEART, SPADE, JOKER
    }
    private final Suit suit;
    
    /**
     * Numbers: 2-10, then 11 for Jack, 12 for Queen, 13 for King, 1 for Ace. 
     */
    private final int number;
    
    //TODO private final Icon image
    //TODO private boolean faceUp
    
    /**
     * Constructor: creates card with a given suit and rank
     * @param suitString must be one of: "club", "diamond", 
     * "heart", or "spade", not case sensitive. 
     * @param rank must be integer between 1 and 13, inclusive
     */
    public Card(String suitString, int num){
        suit=Suit.valueOf(suitString.toUpperCase());
        number=num;
    }
    
    public int getNumber(){
        return number;
    }
    
    public String getSuit(){
        return suit.toString();
    }
    
    /**
     * Gets color of card based on suit, output as String
     * Clubs and spades are "black"
     * Diamonds and hearts are "red"
     * Jokers are... idk what yet. 
     * @return String with color
     */
    public String getColor(){
        if (this.suit.equals(Suit.CLUB) || this.suit.equals(Suit.SPADE)){
            return "black";
        }
        else if (this.suit.equals(Suit.DIAMOND) || this.suit.equals(Suit.HEART)){
            return "red";
        }
        return "idk";
    }
    
    /**
     * Equality test between cards. Assumes other object is not null. 
     */
    public boolean equals(Object other){
        if (other.getClass()!=Card.class) return false;
        Card card2= (Card) other;
        return this.suit.equals(card2.suit) && this.number==card2.number;
    }
    
    public int compareTo(Card other){
        //TODO
        return -1;
    }
    
    public String numberToString(){
        switch (number){
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            case 1:
                return "Ace";
            default:
                return ""+number;
        }
    }
    
    /**
     * String representation of card. Lazy implementation.
     * Suit first then number/ rank. 
     */
    public String toString(){
        return suit.toString() + " " + this.numberToString();
        
    }
}
