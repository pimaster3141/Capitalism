/*
 * Implements a deck of cards (54), and has related methods
 * 
 * shuffle
 * deal
 * add
 * sort
 * getSize
 * getDeck (dangerous)
 * 
 */

import java.util.*;
import javax.swing.*;

public class Deck
{
    private ArrayList<Card> deck; //holds the deck of cards
    
    public Deck()
    {
        deck = new ArrayList<Card>(54); //creates a new arrayList of cards
        
        //creates all instances of cards in order
        int cardPointer = 0;
        for (int suit = 0; suit < 4; suit++)
        {
            for (int rank = 0 ; rank < 13; rank++)
            {
                deck.add(new Card(suit,rank));
            }
        }
        //adds jokers to deck
        deck.add(new Card(4,13)); 
        deck.add(new Card(4,14));
    }
    
    //selectively remove a card given card specifications
    //return true if removed, false otherwise. 
    public boolean remove(int suit, int rank)
    {
        int index = deck.indexOf(new Card(suit, rank));
        if (index == -1)
            return false;
        else
        {
            deck.remove(index);
            return true;
        }
    }
    
    //removes a card that is the same as the given instance of card c
    //returns true if removed
    //false otherwise
    public boolean remove(Card c)
    {
        int index = deck.indexOf(c);
        if (index == -1)
            return false;
        else
        {
            deck.remove(index);
            return true;
        }
    }
    
    //shuffles the deck
    public void shuffle()
    {
        ArrayList<Card> temp = new ArrayList<Card>();
        
        while (deck.size()>0)
        {
            int random = (int)(Math.random()*deck.size());
            temp.add(deck.remove(random));
        }
        while (temp.size()>0)
        {
            int random = (int)(Math.random()*temp.size());
            add(temp.remove(random));
        }
    }
    
    //returns the number of cards in deck
    public int getSize()
    {
        return deck.size();
    }
    
    // returns the entire deck
    public ArrayList<Card> getDeck()
    {
        return deck;
    }
    
    //gets the first card on top of the deck
    public Card deal()
    {
        if (deck.size()>0)
            return deck.remove(0);
        else
            return null;
    }
    
    //selectively adds a card if not already in deck
    //returns true if the card is added (not already in deck)
    //false otherwise
    public boolean add(Card card)
    {
        if (!deck.contains(card))
            return deck.add(card);
        else 
            return false;
    }
        
    //adds a card at specificed location if not already in deck
    public void add(int i, Card card)
    {
        if (!deck.contains(card))
            deck.add(i, card);
    }
    
    //sorts the remaining cards in deck 
    public void sort()
    {
        for (int n = 1; n < deck.size(); n++)
        {
            Card temp = deck.get(n);
            int i = n;
            while (i > 0 && temp.compareTo(deck.get(i-1)) < 0)
            {
                deck.set(i,deck.get(i-1));
                i--;
            }
            deck.set(i, temp);
        }
    }
    
    //prints all cards in deck
    public String toString()
    {
        String result = "";
        for (Card c : deck)
            result = result + c + "\n";
        return result;
    }
}