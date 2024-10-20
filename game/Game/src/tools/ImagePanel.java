package tools;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel
{
    private Image image;
    int x, y;
    int height, width;

    public ImagePanel(String imagePath, int x, int y)
    {
        this.x = x;
        this.y = y;

        ImageIcon background = new ImageIcon(imagePath);
        image = background.getImage();
    }

    public ImagePanel() {
        
    }

    @Override
    public void updateUI() {
        setDoubleBuffered(false);
        setOpaque(false);
        super.updateUI();
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        g.drawImage(image, this.x, this.y, getWidth(), getHeight(), this);
    }

    @Override
    public Dimension getPreferredSize() {
        if(this.image == null) return new Dimension(500, 500);
        return new Dimension(image.getWidth(this), image.getHeight(this));
    }


}
