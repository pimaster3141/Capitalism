import javax.swing.*;
import java.awt.*;

public class CardApp1 extends JFrame{

    public CardApp1(){
	CardPanel panel = new CardPanel();
	Container c = getContentPane();
	c.add("Center", panel);
    }

    public static void main(String[] args){
       CardApp1 theGUI = new CardApp1();
       theGUI.setSize(200, 200);
       theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       theGUI.setVisible(true);
    }

}
