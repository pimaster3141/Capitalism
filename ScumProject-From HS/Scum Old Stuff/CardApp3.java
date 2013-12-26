import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CardApp3 extends JFrame{

    public CardApp3(){
        final Deck deck = new Deck();
        deck.shuffle();
    final CardPanel panel = new CardPanel();
        JButton button = new JButton("Deal");
        button.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
              Card card = deck.deal();
              if (card !=  null)
              {
                card.flipCard();
                panel.setCard(card);
            }
           }});
    Container c = getContentPane();
    c.add("Center", panel);
        c.add("South", button);
    }

    public static void main(String[] args){
       CardApp3 theGUI = new CardApp3();
       theGUI.setSize(200, 200);
       theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       theGUI.setVisible(true);
    }

}
