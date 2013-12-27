import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;


public class CardRunner
{
    public static void main(String [] args)
    {
        
        ScumDeck myDeck = new ScumDeck();
        System.out.println(myDeck);
        System.out.println(myDeck.getDeck().indexOf(new Card(3,12)));
        if (myDeck.getDeck().contains(new Card(2,1)));
            System.out.println("good");
        myDeck.shuffle();
        System.out.println("----------------------");
        for(ScumCard c : myDeck.getDeck())
            System.out.println(c);
        
        myDeck.sort();
        System.out.println("================");
        System.out.println(myDeck);
        System.out.println();
        
        for(ScumCard c : myDeck.getDeck())
            System.out.println(c);
            
        
        /*
        myDeck.shuffle();
        Player p = new HumanPlayer("test");
        for (int x = 0; x < 13; x++)
            p.addCard(myDeck.deal());
        System.out.println(p);
        
        
        while (!p.isDone())
        {
            p.getMove();
        }
       
       
        ArrayList<Player> players = new ArrayList<Player>(4);
        for (int x = 0; x < 4; x++ )
        {
            players.add(new HumanPlayer("Player" + x));
        }
        
        ScumGame game = new ScumGame(players);
        for (Player p : players)
            System.out.println(p);
         */   
        
           
        
    }
}