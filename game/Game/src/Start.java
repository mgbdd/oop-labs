import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import tools.Config;
import tools.ImagePanel;

public class Start {
    public static void setStartMenu(Game myGame, JFrame gameFrame, Config conf) {
        gameFrame.setTitle("Match 3");
        gameFrame.setLocation(400, 100);
        gameFrame.setSize(700, 700);
        ImageIcon icon = new ImageIcon(conf.getProperty("iconPath"));
        gameFrame.setIconImage(icon.getImage());

        ImagePanel backgroundPanel = new ImagePanel(conf.getProperty("backgroundPath"), 0, 0);
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setPreferredSize(new Dimension(gameFrame.getWidth(), gameFrame.getHeight()));

        JLabel startText = new JLabel("Match 3");
        startText.setFont(new Font(conf.getProperty("font"), Font.BOLD, 40));
        startText.setForeground(Color.darkGray);

        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.gridy = -1;
        textConstraints.gridx = 0;
        textConstraints.insets = new Insets(10,10,50,10);
        backgroundPanel.add(startText, textConstraints);

        JButton startButton = new JButton("start");
        startButton.setFont(new Font(conf.getProperty("font"), Font.BOLD, 20));
        startButton.setForeground(Color.DARK_GRAY);
        startButton.setBackground(Color.pink);
        startButton.setSize(40, 40);
        startButton.setFocusable(false);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.insets = new Insets(50, 10, 10, 10); // задает отступы вокруг кнопки
        backgroundPanel.add(startButton, buttonConstraints);
        gameFrame.setContentPane(backgroundPanel);

        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    backgroundPanel.removeAll();
                    backgroundPanel.revalidate();
                    backgroundPanel.repaint();
                    myGame.playGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });



        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }



}