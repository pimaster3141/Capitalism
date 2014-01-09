package cards;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards= new ArrayList<Card>();
    
    /**
     * Default constructor: 1 set of 52 cards, no Jokers
     */
    public Deck(){
        addCards(1,false);
    }
    
    /**
     * Constructor for additional decks as specified by numRep
     * @param numRep the number of decks to include 
     *  (sets of 52 cards, no Jokers)
     * @throws Exception if numRep<1
     */
    public Deck(int numRep) throws Exception{
        if (numRep<1) throw new Exception("must have at least one deck");
        addCards(numRep, false);
    }
    
    /**
     * Constructor for numRep additional decks, optional Jokers
     * @param numRep the number of decks to include 
     * @param jokers true if you want to include 2 jokers/ deck (numRep), else false
     * @throws Exception if numRep<1
     */
    public Deck(int numRep, boolean jokers) throws Exception{
        if (numRep<1) throw new Exception("must have at least one deck");
        addCards(numRep, jokers);
    }
    
    private void addCards(int numRep, boolean includeJokers){
        for (int i=0; i<numRep; i++){
            for (Card.Suit s: Card.Suit.values()){
                //excluding Jokers
                if (s!=Card.Suit.valueOf("JOKER")){
                    for (int val=1; val<13; val++){
                        cards.add(new Card(s.toString(),val));
                    }
                }
                //potentially including 2 jokers per deck
                else{
                    if(includeJokers){
                        cards.add(new Card("Joker",2));
                        cards.add(new Card("Joker",2));
                    }
                }
            }
        }
    }
    
    /**
     * Returns the number of cards per player, if the deck was divided evenly,
     * rounding downwards 
     * @param numPlayers
     * @return floor(size cards/ numPlayers)
     */
    public int divide(int numPlayers){
        return cards.size()/numPlayers;
    }
    
    /**
     * Randomly selects a card from a collection, and removes it
     * @param c a list of cards
     * @return a randomly selected card that has been removed from c
     */
    private static Card removeRandomCard(ArrayList<Card> c) throws Exception{
        if (c.isEmpty()) throw new Exception("list is empty");
        int out= (int) Math.floor(c.size()*Math.random());
        return c.remove(out);
    }

    /**
     * Randomly selects Cards from deck, returning them and removing them
     * from the deck. 
     * @param numCards the number of cards to remove
     * @return an arraylist of randomly selected cards removed from deck
     */
    public ArrayList<Card> dealCards(int numCards){
        ArrayList<Card> outList= new ArrayList<Card>();
        try{
            for (int i=0; i<numCards; i++){
                outList.add(removeRandomCard(this.cards));
            }
        }catch (Exception e){
            //do nothing? just return a smaller list
        }
        return outList;
    }
 
    /**
     * Determines if there are still cards in the deck
     * @return true if there are still cards, false if there are no more cards
     */
    public boolean hasCards(){
        return !(this.cards.isEmpty());
    }
}
