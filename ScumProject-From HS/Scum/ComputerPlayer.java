import java.util.*;

public class ComputerPlayer implements Player
{
    private ArrayList<ScumCard> hand;
    private ScumGame currentGame;
    private int rank;
    private String name;
    private boolean isDone;
    private ArrayList<ScumCard> discard;
    private AIStack commands;
    
    public ComputerPlayer(String n, ScumGame game)
    {
        name = n;
        rank = 0;
        hand = new ArrayList<ScumCard>();
        isDone = true;
        currentGame = game;
        discard = game.getDiscardPile();
        commands = new AIStack();
    }
    
    public void reset()
    {
        hand.clear();
        isDone = true;
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
        //hand.add(card);
        int x;
        for (x = 0; x < hand.size(); x++)
            if (hand.get(x).compareToIgnoreSuit(card)>0)
                break;
        hand.add(x,card);
        isDone = false;
    }
    
    public void removeCard(ScumCard c)
    {
        hand.remove(hand.indexOf(c));
        if(hand.size()==0)
            isDone=true;
    }
    
    public boolean isDone()
    {
        return isDone;
    }
    
    public ArrayList<ScumCard> getMove(int numCards)
    {
        act(numCards);//calls act, which adds actions to the instruction stack
        int[] action = parse(numCards);//parse interprets commands from the stack
        if(action.length>=1 && action[0]==0)//if play is passed by parse
        {
            ArrayList<ScumCard> temp = new ArrayList<ScumCard>();
            if(numCards>0 && action.length==1)//if told to play any one card
            {
                ScumCard toBeat = discard.get(discard.size()-1);//get the card to beat
                for(int i=0;i<hand.size();i++)
                {
                    ScumCard toPlay = hand.get(i);
                    if(toPlay.compareToIgnoreSuit(toBeat) > 0)
                    {
                        temp.add(toPlay);//if arraylist is empty then add
                        return temp;
                    }
                }
            }
            else
            {
                if(action.length>1 && action[1]==11)//if play double is passed
                {
                    if(numCards==2)
                    {
                        ScumCard toBeat = discard.get(discard.size()-1);
                        ScumCard toPlay = new ScumCard(0,action[2]);
                        if(toPlay.compareToIgnoreSuit(toBeat)>0)
                        {
                            for(int i=0;i<hand.size();i++)
                            {
                                if(hand.get(i).compareToIgnoreSuit(toPlay)==0 && temp.size()<2)
                                {
                                    temp.add(hand.get(i));
                                }
                            }
                        }
                        
                        if(temp.size()==2)
                            return temp;
                        else
                            return null;
                    }
                    else
                    {
                        ScumCard toPlay = new ScumCard(0,action[2]);
                        for(int i=0;i<hand.size();i++)
                        {
                            if(hand.get(i).compareToIgnoreSuit(toPlay)==0 && temp.size()<2)
                            {
                                temp.add(hand.get(i));
                            }
                        }
                        if(temp.size()==2)
                            return temp;
                        else
                            return null;
                    }
                }
                else if(action.length>1 && action[1]==111)//if play triple is passed
                {
                    if(numCards==3)
                    {
                        ScumCard toBeat = discard.get(discard.size()-1);
                        ScumCard toPlay = new ScumCard(0,action[2]);
                        if(toPlay.compareToIgnoreSuit(toBeat)>0)
                        {
                            for(int i=0;i<hand.size();i++)
                            {
                                if(hand.get(i).compareToIgnoreSuit(toPlay)==0 && temp.size()<3)
                                {
                                    temp.add(hand.get(i));
                                }
                            }
                        }
                        
                        if(temp.size()==3)
                            return temp;
                        else
                            return null;
                        
                    }
                    else
                    {
                        ScumCard toPlay = new ScumCard(0,action[2]);
                        for(int i=0;i<hand.size();i++)
                        {
                            if(hand.get(i).compareToIgnoreSuit(toPlay)==0 && temp.size()<3)
                            {
                                temp.add(hand.get(i));
                            }
                        }
                        if(temp.size()==3)
                            return temp;
                        else
                            return null;
                    }
                }
                else if(numCards==0)
                {
                    temp.add(hand.get(findIndexMin()));
                }
            }
            if(temp.size()==0)
            {
                temp = null;
            }
            return temp;
        }
        else if(action.length==1 && action[0]==1)
        {
            ArrayList<ScumCard> temp= new ArrayList<ScumCard>();
            temp.add(hand.get(findIndexMax()));
            return temp;
        }
        else
            return null;
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
    
    private int[] parse(int numCards)
    {
        
        String[] cmd = commands.read().split(" ");
        if(cmd[0].equals("clear"))
        {
            int[] temp = {1};
            commands.pop();
            return temp;
        }
        else if(numCards==1)
        {
            int[] temp = {0};
            return temp;
        }
        else if(cmd[0].equals("play"))
        {
            if(cmd.length>1)
            {
                if(cmd[1].equals("double") && (numCards==0 || numCards==2))
                {
                    int[] temp = {0,11,new Integer(cmd[2])};
                    commands.pop();
                    return temp;
                }
                else if(cmd[1].equals("triple") && (numCards==0 || numCards==3))
                {
                    int[] temp = {0,111,new Integer(cmd[2])};
                    commands.pop();
                    return temp;
                }
                else
                {
                    int[] temp = {0};
                    return temp;
                }
            }
            else
            {
                int[] temp = {0};
                commands.pop();
                return temp;
            }
        }
        else
        {
            int[] temp = {2};
            commands.pop();
            return temp;
        }
    }
    
    private void act(int numCards)//needs to check before adding if already present
    {
        ArrayList<ArrayList<ScumCard>> cards = new ArrayList<ArrayList<ScumCard>>(13);
        for(int i=0;i<13;i++)
        {
            cards.add(new ArrayList<ScumCard>());
        }
        //created an arraylist of arraylists of scumcards
        for(ScumCard c:hand)
        {
            cards.get(c.getRank()).add(c);//add cards into the arraylists("piles")
        }
        ArrayList<Integer> doubles = new ArrayList<Integer>();
        ArrayList<Integer> triples = new ArrayList<Integer>();
        for(ArrayList<ScumCard> a:cards)
        {
            if(a.size()==2)
                doubles.add(cards.indexOf(a));
            else if(a.size()==3)
                triples.add(cards.indexOf(a));
        }
        //need to search for the triples. if a present triple is not found, remove
        if(triples.size()>0)
        {
            if(commands.getStack().indexOf("play triple " + (cards.get(triples.get(0)).get(0).getRank()+2))==-1)
            {
                commands.push("play triple " + (cards.get(triples.get(0)).get(0).getRank()+2));
                commands.push("clear");
            }
        }
        else if(doubles.size()>0)
        {
            if(commands.getStack().indexOf("play double " + (cards.get(doubles.get(0)).get(0).getRank()+2))==-1)
            {
                commands.push("play double " + (cards.get(doubles.get(0)).get(0).getRank()+2));
                commands.push("clear");
            }
        }
        
        //handles playing doubles and triples
        
        if((numCards==2 || numCards==0) && doubles.size()>0)
        {
            if(numCards==2)
            {
                boolean found=false;
                ScumCard toBeat = discard.get(discard.size()-1);
                for(Integer i:doubles)
                {
                    if(i.intValue()>toBeat.getRank())
                    {
                        commands.push("play double " + (i.intValue()+2));
                        found=true;
                    }
                }
                if(!found)
                    commands.push("pass");
            }
            else
            {
                commands.push("play double " + (doubles.get(0)+2));
            }
        }
        else if((numCards==3 || numCards==0) && triples.size()>0)
        {
            if(numCards==3)
            {
                boolean found=false;
                ScumCard toBeat = discard.get(discard.size()-1);
                for(Integer i:triples)
                {
                    if(i.intValue()>toBeat.getRank())
                    {
                        commands.push("play triple " + (i.intValue()+2));
                        found=true;
                    }
                }
                if(!found)
                    commands.push("pass");
            }
            else
            {
                commands.push("play triple" + (triples.get(0)+2));
            }
        }
        else if(numCards==0 && triples.size()==0 && doubles.size()==0)
            commands.push("play");
        else
            commands.push("pass");
        
        //search for old commands and remove
        for(int i=0;i<commands.getStack().size();i++)
        {
            if(commands.getStack().get(i).split(" ").length>1 && commands.getStack().get(i).split(" ")[1].equals("triple"))
            {
                boolean isPresent=false;
                for(int j=0;j<triples.size();j++)
                {
                    if(commands.getStack().get(i).equals("play triple " + (cards.get(triples.get(j)).get(0).getRank()+2)))
                    {
                        isPresent=true;
                    }
                }
                if(!isPresent)
                    commands.remove(commands.getStack().get(i));
            }
            else if(commands.getStack().get(i).split(" ").length>1 && commands.getStack().get(i).split(" ")[1].equals("double"))
            {
                boolean isPresent=false;
                for(int j=0;j<doubles.size();j++)
                {
                    if(commands.getStack().get(i).equals("play double " + (cards.get(doubles.get(j)).get(0).getRank()+2)))
                    {
                        isPresent=true;
                    }
                }
                if(!isPresent)
                    commands.remove(commands.getStack().get(i));
            }
        }
        
        //remove unwanted "pass" instructions
        for(int i=1;i<commands.getStack().size();i++)
        {
            if(commands.getStack().get(i).equals("pass"))
                commands.getStack().remove(i);
        }
        
        //if not playing triple or double then         
            
    }
    
    
    public String toString()
    {
        String answer = "";
        answer = name+":\n";
        for (ScumCard c : hand)
        {
            answer = answer + "\t" + c + "\n";
        }
        
        answer = answer + "\n Rank = " + rank;
        return answer;
    }
        
}
