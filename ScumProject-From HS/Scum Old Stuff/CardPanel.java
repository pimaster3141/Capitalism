import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel
{
    private Card card;
    
    public CardPanel()
    {
        card = null;
    }

    public CardPanel(Card c){
        setBackground(Color.black);
        card = c;
    }

    public void paintComponent(Graphics g){
    super.paintComponent(g);
    Icon image;
    if (card == null){
        image = Card.getBack();
        g.setColor(Color.yellow);
        int x = (getWidth() - image.getIconWidth()) / 2;
        int y = (getHeight() - image.getIconHeight()) / 2;
        g.drawRect(x, y, image.getIconWidth(), image.getIconHeight());
    }
    else{
        image = card.getImage();
        int x = (getWidth() - image.getIconWidth()) / 2;
        int y = (getHeight() - image.getIconHeight()) / 2;
        image.paintIcon(this, g, x, y);
    }
    }
    
    public void setCard(Card c){
        card = c;
        repaint();
    }

}
