public class ScumCardOrig extends Card
{
    private int rank;    

    public ScumCardOrig(int s, int r)
    {
        super(s,r);
        if (r == 0)
            rank = 11;
        else if (r == 1)
            rank = 12;
        else
            rank = r - 2;
    }
    
    public int getRank()
    {
        return rank;
    }
    
    public int getCardNumber()
    {
        return super.getRank() + 1;
    }
    
    public int compareTo(Object other) //compares suit then rank
    {
        Card compare = (Card)other;
        return (this.getSuit()-compare.getSuit())*100 + (rank-compare.getRank());
    }
    
    public int compareToIgnoreSuit(Object other) //compares only rank
    {
        Card compare = (Card)other;
        return (rank-compare.getRank());
    }
}