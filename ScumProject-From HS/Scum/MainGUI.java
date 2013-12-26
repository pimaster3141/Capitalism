/*
 * MainGUI.java
 * @author Christian Gibson (Iacoi)
 * Created on May 13, 2010, 12:25:10 PM
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Transparency;
import java.awt.Image;
import java.awt.image.*;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class MainGUI extends javax.swing.JFrame {

    /** Creates new form MainGUI */
    public MainGUI() 
    {   initComponents();
    }

    //Load the images with ImageIcon. Create a JToggleButton. Then apply the
    //icons with AbstractButton.setIcon/setPressedIcon/setSelectedIcon. Remove
    //the border with AbstractButton.setBorderPainted(false)
   
    //Many thanks to the find/replace and copy/paste functions
    
    private void initComponents() 
    {  jScrollPane1 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        jToggleButton8 = new javax.swing.JToggleButton();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButton11 = new javax.swing.JToggleButton();
        jToggleButton12 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Play Card(s)");
        jButton1.addActionListener(new java.awt.event.ActionListener() 
        {   public void actionPerformed(java.awt.event.ActionEvent evt) 
            {   jButton1ActionPerformed(evt);
            }
        });

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/00.png"))); // NOI18N
        jToggleButton1.setText(" ");
        jToggleButton1.setHideActionText(true);
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/00.png")))); // NOI18N

        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/01.png"))); // NOI18N
        jToggleButton2.setText(" ");
        jToggleButton2.setHideActionText(true);
        jToggleButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton2.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/01.png")))); // NOI18N

        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/011.png"))); // NOI18N
        jToggleButton3.setText(" ");
        jToggleButton3.setHideActionText(true);
        jToggleButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton3.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/011.png")))); // NOI18N

        jToggleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/010.png"))); // NOI18N
        jToggleButton4.setText(" ");
        jToggleButton4.setHideActionText(true);
        jToggleButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton4.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/010.png")))); // NOI18N

        jToggleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/03.png"))); // NOI18N
        jToggleButton5.setText(" ");
        jToggleButton5.setHideActionText(true);
        jToggleButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton5.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/03.png")))); // NOI18N

        jToggleButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/04.png"))); // NOI18N
        jToggleButton6.setText(" ");
        jToggleButton6.setHideActionText(true);
        jToggleButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton6.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/04.png")))); // NOI18N

        jToggleButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/012.png"))); // NOI18N
        jToggleButton7.setText(" ");
        jToggleButton7.setHideActionText(true);
        jToggleButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton7.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/012.png")))); // NOI18N

        jToggleButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/02.png"))); // NOI18N
        jToggleButton8.setText(" ");
        jToggleButton8.setAlignmentY(0.0F);
        jToggleButton8.setBorderPainted(false);
        jToggleButton8.setDisplayedMnemonicIndex(0);
        jToggleButton8.setHideActionText(true);
        jToggleButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton8.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/02.png")))); // NOI18N

        jToggleButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/07.png"))); // NOI18N
        jToggleButton9.setText(" ");
        jToggleButton9.setAlignmentY(0.0F);
        jToggleButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton9.setHideActionText(true);
        jToggleButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton9.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/07.png")))); // NOI18N

        jToggleButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/05.png"))); // NOI18N
        jToggleButton11.setText(" ");
        jToggleButton11.setHideActionText(true);
        jToggleButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton11.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/05.png")))); // NOI18N

        jToggleButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cards/06.png"))); // NOI18N
        jToggleButton12.setText(" ");
        jToggleButton12.setHideActionText(true);
        jToggleButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton12.setSelectedIcon(reverse(new javax.swing.ImageIcon(getClass().getResource("/Cards/06.png")))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 204, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 852, Short.MAX_VALUE));
            
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(//This just spaces the buttons -- makes the GUI astetically appealing (horiz)
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap()));
                
        layout.setVerticalGroup( //This just spaces the buttons -- makes the GUI astetically appealing (vert)
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(15, 15, 15)));

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {   //TODO add button action code
    }
    private ImageIcon reverse(String imgName)
    {   byte invert[] = new byte[256];
        for (int i = 0; i < 256; i++)
        {   invert[i] = (byte)(255 - i);
        }
        LookupOp op = new LookupOp(new ByteLookupTable(0, invert), null);
        Image icon = new ImageIcon(imgName).getImage();
        BufferedImage buffIcon = toBufferedImage(icon);
        return new ImageIcon(op.filter(buffIcon, buffIcon));
    }
    private ImageIcon reverse(Icon img)
    {   BufferedImage bimg = toBufferedImage(((ImageIcon)img).getImage()); // not part of JDK1.4
        ConvolveOp sobel = new ConvolveOp(new Kernel(3,3,new float[]{ 1, 0,-1,2,0,-2,1,0,-1})); // vertical Sobel filter
        return new ImageIcon(sobel.filter(bimg, null));
    }
    private ImageIcon reverse(Image img)
    {   return reverse(img);
    }
    
    /**
     * @author alpha02
     */
    
    public static BufferedImage toBufferedImage(Image image) 
    {   if (image instanceof BufferedImage) {return (BufferedImage)image;}
    
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
    
        // Determine if the image has transparent pixels
        boolean hasAlpha = hasAlpha(image);
    
        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try 
        {   // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha == true) {transparency = Transparency.BITMASK;}
    
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } 
        catch (HeadlessException e) {} //No screen
    
        if (bimage == null) 
        {   // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha == true) {type = BufferedImage.TYPE_INT_ARGB;}
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
    
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
    
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
    
        return bimage;
    }
    
    /**
     * @author alpha02
     */
    
    public static boolean hasAlpha(Image image) 
    {   // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) 
        {   return ((BufferedImage)image).getColorModel().hasAlpha();
        }
         
        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {pg.grabPixels();} catch (InterruptedException e) {}
         
        // Get the image's color model
        return pg.getColorModel().hasAlpha();
    }
    public static void main(String args[]) 
    {   java.awt.EventQueue.invokeLater(new Runnable() 
        {   public void run() 
            {   new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - no touchy
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton11;
    private javax.swing.JToggleButton jToggleButton12;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JToggleButton jToggleButton7;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButton9;
    // End of variables declaration

}
