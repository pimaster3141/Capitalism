import java.util.*;

public class ComputerPlayer implements Player
{
	private ArrayList<ScumCard> hand;
	private int rank;
	private String name;
	private boolean isDone;
	private ArrayList<ScumCard> discard;
	
	public ComputerPlayer(String n, ArrayList<ScumCard> d)
	{
		name = n;
		rank = 0;
		hand = new ArrayList<ScumCard>();
		isDone = true;
		discard = d;
	}
	
	public void reset()
	{
		hand.clear();
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public void setRank(int n)
	{
		rank = n;
	}
	
	public ArrayList<ScumCard> getHand()
	{
		return hand;
	}
	
	public void addCard(ScumCard card)
	{
		hand.add(card);
	}
	
	public ArrayList<ScumCard> getMove(int numCards)
	{
		ArrayList<ScumCard> temp = new ArrayList<ScumCard>();
		if(numCards > 0)
		{
			ScumCard toBeat = discard.get(discard.size()-1);//get the card to beat
			for(int i=0;i<numCards;i++)
			{
				for(int j=0;j<hand.size();j++)
				{
					if(hand.get(j).compareToIgnoreSuit(toBeat) > 0 && temp.size()==0)
					{
						temp.add(hand.get(j));
					}
					else if(hand.get(j).compareToIgnoreSuit(toBeat) > 0 && hand.get(j).equals(temp.get(0)) && temp.size()<numCards)
					{
						temp.add(hand.get(j));
					}
				}
			}
		}
		else
		{
			temp.add(hand.get(findIndexMin()));
		}
		if(temp.size()==0)
		{
			temp = null;
		}
		return temp;
	}
	
	public boolean isDone()
	{
		return isDone;
	}
	
	public void removeCard(ScumCard c)
	{
		hand.remove(hand.indexOf(c));
	}
	
	public int findIndexMax()//finds index of highest card in hand
	{
		if(hand.size()==0)
			return -1;
		int maxIndex = 0;
		for(int i=0;i<hand.size();i++)
		{
			if(hand.get(i).compareToIgnoreSuit(hand.get(maxIndex)) >= 0)
			{
				maxIndex=i;
			}
		}
		return maxIndex;
	}
	
	public int findIndexMin()//finds index of lowest card in hand
	{
		if(hand.size()==0)
			return -1;
		int maxIndex = 0;
		for(int i=0;i<hand.size();i++)
		{
			if(hand.get(i).compareToIgnoreSuit(hand.get(maxIndex)) <= 0 && hand.get(i).compareToIgnoreSuit(new ScumCard(0,1)) != 0)
			{
				maxIndex=i;
			}
		}
		return maxIndex;
	}
}
