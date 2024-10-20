import tools.ImagePanel;
import tools.Token;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class SetUp {

    private static final int offsetX = 10;
    private static final int offsetY = 10;
    private static final int tileSize = 55;


    static void updateTokens(Token[][] grid) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (grid[i][j].getRefillFlag()) {
                    Random random = new Random();
                    int oldType =  grid[i][j].getType();
                    int type;
                    do{
                        type = random.nextInt(6);
                    }while(type == oldType);
                    grid[i][j].setType(type);
                    grid[i][j].setRefillFlag(false);
                }

                //grid[i][j].setX(i * 60 + 2 * offsetX);
                //grid[i][j].setY(j * 60 + 2 * offsetY);
            }
        }
    }

    static ImagePanel drawGameTokens(ImagePanel tilesPanel, BufferedImage[] tokens, Token[][] grid) throws IOException {


        //super.paintComponent(g);
        ImagePanel tokenPanel = new ImagePanel() {


            @Override
            public void updateUI() {
                setDoubleBuffered(false);
                setOpaque(false);
                super.updateUI();
            }

            @Override
            public void paintComponent(Graphics g) {
                //super.paintComponent(g);

                //updateTokens(grid);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {

                        int type = grid[i][j].getType();

                        g.drawImage(tokens[grid[i][j].getType()], i * 60 + offsetX, j * 60 + offsetY, tileSize, tileSize, null);
                    }
                }

            }
        };

        tokenPanel.setPreferredSize(new Dimension(500, 500));
        tokenPanel.setOpaque(false);
        tilesPanel.add(tokenPanel);
        return tokenPanel;
    }

}
