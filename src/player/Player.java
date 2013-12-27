package player;

import game.Game;

import java.util.ArrayList;

import cards.Card;

/**
 * 
 * This class is a generic player class
 * defines basic functions to play a game
 * extended by human and AI
 * 
 * TODO - figure out how to instruct game to skip player if the player has completed
 * TODO - implement some sort of trading
 *
 */
public abstract class Player implements Runnable 
{
	public final String name;	//name of player
	private int rank;		//rank of player (for trading)
	protected ArrayList<Card> hand;	//listing of cards held
	protected Game game;	//game player is in
	
	/*
	 * Generic constructor, initialize fields
	 * @param String - the name of the player
	 */
	public Player(String name)
	{
		this.name = name;	
		this.rank = 0;
		this.hand = new ArrayList<Card>();
	}
	
	/*
	 * Resets the player for he next round
	 */
	public void reset()
	{
		hand.clear();
	}
	
	/*
	 * Returns the name of the player
	 * @return String - the name of the player
	 */
	public String getName()
	{
		return this.name;
	}
	
	/*
	 * Returns the rank of the player
	 * @return int - the rank of the player (-2,-1,0,+1,+2)
	 */
	public int getRank()
	{
		return rank;
	}
	
	/*
	 * Sets the players rank
	 * @param int - the rank of the player (-2,-1,0,+1,+2)
	 */
	public void setRank(int rank)
	{
		this.rank = rank;
	}
	
	/*
	 * Adds a card to the players hand
	 * @param Card - card to be added to the hand
	 */
	public void addCard(Card card)
	{
		hand.add(card);
	}
	
	/*
	 * Returns a shallow copy of the players hand
	 * @param ArrayList<Card> - Copy of hand
	 */
	public ArrayList<Card> getHand()
	{
		return  new ArrayList<Card>(hand);
	}
	
	/*
	 * Signature for move
	 * move should push a particular move to the Game game's input queue to be processed
	 */
	public abstract void makeMove();	
}
