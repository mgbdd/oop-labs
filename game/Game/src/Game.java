import tools.Config;
import tools.ImagePanel;
import tools.Token;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.abs;


public class Game implements MouseListener {
    MouseEvent mouseEvent;
    JFrame gameFrame;
    Token[][] grid;
    ImagePanel tokenPanel;
    ImagePanel tilesPanel;
    BufferedImage[] tokens;
    int x1, y1, x2, y2;
    int click = 0;
    int posX, posY;
    boolean isSwap = false, isRunning = false;
    int tileSize = 55;
    int offsetX = 10, offsetY = 10;
    int moves, matches, taskChoice;
    String tokenChoice;
    JLabel moveStr, matchStr;
    Config conf;


    public void startGame() {
        this.gameFrame = new JFrame();
        conf = new Config();
        Start.setStartMenu(this, gameFrame, conf);
    }

    public void playGame() throws IOException {

        moves = Integer.parseInt(conf.getProperty("moves"));
        matches = Integer.parseInt(conf.getProperty("combinations"));

        tilesPanel = new ImagePanel(conf.getProperty("tilesPath"), 0, 0);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);

        gameFrame.add(tilesPanel, gbc);
        gameFrame.setVisible(true);

        grid = new Token[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j] = new Token();
            }
        }

        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j].setType(random.nextInt(6));
                grid[i][j].setRow(j);
                grid[i][j].setCol(i);
            }
        }

        tokens = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            String imagePath = "images/" + (i + 1) + ".png";
            File imageFile = new File(imagePath);
            tokens[i] = ImageIO.read(imageFile);
        }

        tilesPanel.addMouseListener(this);
        tokenPanel = new ImagePanel();
        tokenPanel = SetUp.drawGameTokens(tilesPanel, tokens, grid);

        setTask(gbc);
        tokenPanel.repaint();
        boolean clear;
        do {
            clear = checkTokenPositions();
        } while (!clear);
        isRunning = true;

    }


    void swapTokens() throws IOException {
        if (mouseEvent != null && mouseEvent.getID() == MouseEvent.MOUSE_PRESSED && isRunning) {
            System.out.println("x: " + mouseEvent.getX() + ", y: " + mouseEvent.getY());
            if (mouseEvent.getButton() == MouseEvent.BUTTON1) {

                isSwap = false;
                click++;

                posX = mouseEvent.getX() - 3 * offsetX;
                posY = mouseEvent.getY() - 3 * offsetY;

                if (click == 1) {
                    x1 = posX / tileSize + 1;
                    y1 = posY / tileSize + 1;
                    System.out.println("x1: " + x1 + ", y1: " + y1 + " type: " + grid[x1 - 1][y1 - 1].getType());
                }


                if (click == 2) {
                    x2 = posX / tileSize + 1;
                    y2 = posY / tileSize + 1;

                    System.out.println("x2: " + x2 + ", y2: " + y2 + " type: " + grid[x2 - 1][y2 - 1].getType());

                    if (abs(x1 - x2) + abs(y1 - y2) == 1) {

                        boolean match;
                        match = checkSwap(x1, x2, y1, y2);
                        if (!match) match = checkSwap(x2, x1, y2, y1);

                        if (match) {
                            swap(grid[x1 - 1][y1 - 1], grid[x2 - 1][y2 - 1]);
                        } else if (isRunning) checkTokenPositions();
                        tokenPanel.repaint();
                        isSwap = true;
                    }
                    click = 0;

                }
            }
        }
    }

    boolean checkTokenPositions() //проверяет стоит ли что-то три в ряд
    {
        boolean clear = true;
        if (matches <= 0 && isRunning) {
            isRunning = false;
            gameFinish(this, 1);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i <= 5 && grid[i][j].getType() == grid[i + 1][j].getType()) {
                    if (grid[i][j].getType() == grid[i + 2][j].getType()) {
                        System.out.println("case 1");
                        System.out.println("token: " + grid[i][j].getCol() + " " + grid[i][j].getRow());

                        if (grid[i][j].getType() + 1 == taskChoice && isRunning) {
                            matches--;
                            clear = false;
                            if (matches <= 0 && isRunning) {
                                isRunning = false;
                                gameFinish(this, 1);
                                break;
                            }
                            matchStr.setText("<html>" + "Task: <br>" + matches + " matches of <br>" + tokenChoice + "</html>");
                        }

                        refillTokens(i, j, 1);
                        SetUp.updateTokens(grid);
                    }
                } else if (j <= 5 && grid[i][j].getType() == grid[i][j + 1].getType()) {
                    if (grid[i][j].getType() == grid[i][j + 2].getType()) {
                        System.out.println("case 2");
                        System.out.println("token: " + grid[i][j].getCol() + " " + grid[i][j].getRow());

                        if (grid[i][j].getType() + 1 == taskChoice && isRunning) {
                            matches--;
                            clear = false;
                            if (matches <= 0 && isRunning) {
                                isRunning = false;
                                gameFinish(this, 1);
                                break;
                            }
                            matchStr.setText("<html>" + "Task: <br>" + matches + " matches of <br>" + tokenChoice + "</html>");
                        }

                        refillTokens(i, j, 2);
                        SetUp.updateTokens(grid);
                    }
                }

            }
        }
        return clear;

    }

    void refillTokens(int x, int y, int type) {
        grid[x][y].setRefillFlag(true);
        if (type == 1) {
            for (int i = 1; i < 8 - x; i++) {
                if (grid[x][y].getType() == grid[x + i][y].getType()) {
                    grid[x + i][y].setRefillFlag(true);
                } else break;
            }
        } else if (type == 2) {
            for (int i = 1; i < 8 - y; i++) {
                if (grid[x][y].getType() == grid[x][y + i].getType()) {
                    grid[x][y + i].setRefillFlag(true);
                } else break;
            }
        }
    }

    void swap(Token t1, Token t2) {
        int tmpRow = t1.getRow();
        t1.setRow(t2.getRow());
        t2.setRow(tmpRow);

        int tmpCol = t1.getCol();
        t1.setCol(t2.getCol());
        t2.setCol(tmpCol);

        grid[t1.getCol()][t1.getRow()] = t1;
        grid[t2.getCol()][t2.getRow()] = t2;
    }

    boolean checkSwap(int x1, int x2, int y1, int y2) {
        boolean match = false;

        if ((x1 - 1) != x2 && (x1 - 1) != (x2 - 2) && x2 < 8 && grid[x1 - 1][y1 - 1].getType() == grid[x2][y2 - 1].getType() && x2 > 1 && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 2][y2 - 1].getType())
            match = true;
        else if ((y1 - 1) != y2 && (y1 - 1) != (y2 - 2) && y2 < 8 && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 1][y2].getType() && y2 > 1 && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 1][y2 - 2].getType())
            match = true;
        else if ((x1 - 1) != x2 && (x1 - 1) != (x2 + 1) && x2 < 7 && grid[x1 - 1][y1 - 1].getType() == grid[x2][y2 - 1].getType() && grid[x1 - 1][y1 - 1].getType() == grid[x2 + 1][y2 - 1].getType())
            match = true;
        else if ((y1 - 1) != y2 && (y1 - 1) != (y2 + 1) && y2 < 7 && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 1][y2].getType() && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 1][y2 + 1].getType())
            match = true;
        else if ((x1 - 1) != (x2 - 3) && (x1 - 1) != (x2 - 2) && x2 >= 3 && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 2][y2 - 1].getType() && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 3][y2 - 1].getType())
            match = true;
        else if ((y1 - 1) != (y2 - 3) && (y1 - 1) != (y2 - 2) && y2 >= 3 && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 1][y2 - 2].getType() && grid[x1 - 1][y1 - 1].getType() == grid[x2 - 1][y2 - 3].getType())
            match = true;

        return match;
    }

    void setTask(GridBagConstraints constraints) {
        Random random = new Random(System.currentTimeMillis());
        taskChoice = random.nextInt(6) + 1;
        tokenChoice = conf.getProperty("type." + taskChoice);

        String task = "Task: <br>" + matches + " matches of <br>" + tokenChoice;
        matchStr = new JLabel("<html>" + task + "</html>");
        matchStr.setFont(new Font(conf.getProperty("font"), Font.BOLD, 20));

        constraints.gridx = 2; // Координата x
        constraints.gridy = 0; // Координата y
        constraints.gridwidth = matchStr.getWidth();
        constraints.gridheight = matchStr.getHeight();
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.NORTH;

        gameFrame.add(matchStr, constraints);

        String movesLeft = "You have " + moves + " <br>moves left";
        moveStr = new JLabel("<html>" + movesLeft + "</html>");
        moveStr.setFont(new Font(conf.getProperty("font"), Font.BOLD, 20));
        constraints.gridx = 2; // Координата x
        constraints.gridy = 0; // Координата y
        moveStr.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        constraints.gridwidth = moveStr.getWidth();
        constraints.gridheight = moveStr.getHeight();
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.NORTH;

        gameFrame.add(moveStr, constraints);


    }

    void gameFinish(Game game, int type) {
        System.out.println("game is finished");

        Container contentPane = gameFrame.getContentPane();
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        JLabel finish;
        if (type == 1) finish = new JLabel("The task is completed");
        else finish = new JLabel("You ran out of moves");
        finish.setFont(new Font(conf.getProperty("font"), Font.BOLD, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gameFrame.add(finish, gbc);

        JButton againButton = new JButton("Again");
        againButton.setFont(new Font(conf.getProperty("font"), Font.BOLD, 20));
        againButton.setForeground(Color.DARK_GRAY);
        againButton.setBackground(Color.pink);
        againButton.setSize(40, 40);
        againButton.setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gameFrame.add(againButton, gbc);

        againButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isRunning = true;
                Container contentPane = gameFrame.getContentPane();
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();

                Start.setStartMenu(game, gameFrame, conf);

                gameFrame.revalidate();
                gameFrame.repaint();
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

        gameFrame.add(finish);
        gameFrame.add(againButton);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseEvent = e;

        try {
            swapTokens();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        if (isSwap && isRunning) {
            checkTokenPositions();
        }
        if (isSwap) {
            moves--;
            if (moves == 0) gameFinish(this, 2);
            moveStr.setText("<html>You have " + moves + " <br>moves left</html>");

        }
        isSwap = false;


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

}









