package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Nicolas Feige
 *
 * @author Nicolas Feige
 * @version 1.0
 * @date 14.9.2020
 */


public class GameGui extends JFrame {

    private JPanel mainPanel = new JPanel();
    private JPanel gamePanel = new JPanel();

    private int marked = 0;
    private int clicked = 0;

    /**
     * Instantiates a new GameGui.
     *
     * @param x               the number of fields on the x direction
     * @param y               the number of fields on the y direction
     * @param nummbersOfBombs the nummbers of Bombs
     */
    GameGui(int x, int y, int nummbersOfBombs) {
        //makes the default settings
        super("Minesweeper");
        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        JLabel[][] fields = new JLabel[y][x];
        boolean[][] fields2;
        //creates the size of the gui based on the number of fields
        if (x == 8) {
            setSize(300, 300);
        } else if (x == 16) {
            setSize(600, 600);
        } else if (x == 25) {
            setSize(938, 938);
        }else{
            //TODO
        }
        //composes the gui
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        gamePanel.setLayout(new GridLayout(y, x));

        //creates the fields and makes the border and color
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                fields[i][j] = new JLabel();
                gamePanel.add(fields[i][j]);
                fields[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                fields[i][j].setBackground(Color.gray);
                fields[i][j].setOpaque(true);
            }
        }
        repaint();

        //creates a two-dimensional boolean and makes a few randomly true to make the bombs
        fields2 = createField(x, y, nummbersOfBombs);

        //creates a MouseListener for each field and creates variables that can be changed and used in the MouseListener
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                int finalI = i;
                int finalJ = j;
                fields[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            //checks if the clicked field is a bomb
                            if (fields2[finalI][finalJ]) {
                                lostGame(x, y, fields, fields2);
                            } else {
                                setFieldPictures(fields, fields2, finalJ, finalI, this);
                            }
                            repaint();
                        }
                        //marks the field or unmarks it
                        if (SwingUtilities.isRightMouseButton(e)) {
                            if (fields[finalI][finalJ].getIcon() != null) {
                                fields[finalI][finalJ].setIcon(null);
                                countMarkedMinus();
                            } else {
                                ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeperMarked.jpg"));
                                fields[finalI][finalJ].setIcon(imageIcon);
                                countMarked();
                            }
                        }
                        //checks if the game has already been won by checking if all fields have been drawn correctly
                        if (marked + clicked == x * y && marked == nummbersOfBombs) {
                            JLabel nachricht = new JLabel("Du hast das Spiel gewonnen!");
                            nachricht.setFont(new Font("SansSerif", Font.BOLD, 25));
                            JOptionPane.showMessageDialog(null, nachricht);
                            dispose();
                        }
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
            }
        }
    }

    /**
     * Add one to the number of clicked fields (because this is not possible in the MouseListener)
     */
    private void countClicked() {
        clicked++;
    }

    /**
     * Add one to the number of marked fields (because this is not possible in the MouseListener)
     */
    private void countMarked() {
        marked++;
    }

    /**
     * Counts down the number of marked fields (because this is not possible in the MouseListener)
     */
    private void countMarkedMinus() {
        marked--;
    }

    /**
     * Find all empty fields near the given field (x,y).
     *
     * @param fields2       the two-dimensional boolean that stored the bomb locations
     * @param x             the x coordinate of the given field
     * @param y             the y coordinate of the given field
     * @param fields        the two-dimensional JLabel array that stores all fields
     * @param mouseListener the mouse listener to deactivate the clicked field
     */
    private void findAllEmptyFields(boolean[][] fields2, int x, int y, JLabel[][] fields, MouseListener mouseListener) {
        //checks how many bombs are touched and quits the method if it touches at least one bomb
        int n = countBombs(fields2, x, y);
        if (n != 0) {
            return;
        }
        //Shows the image with no bomb nearby, removes the MouseListener and increases the count
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper0.jpg"));
        fields[y][x].setIcon(imageIcon);
        countClicked();
        fields[y][x].removeMouseListener(mouseListener);

        //shows all fields that do not touch a bomb and touch the first field (that was clicked and does not touch a bomb)
        if (x < fields.length - 1) {
            if (fields[y][x + 1].getIcon() == null) {
                findAllEmptyFields(fields2, x + 1, y, fields, mouseListener);
            }
        }
        if (y < fields.length - 1) {
            if (fields[y + 1][x].getIcon() == null) {
                findAllEmptyFields(fields2, x, y + 1, fields, mouseListener);
            }
        }
        if (y < fields.length - 1 && x > 0) {
            if (fields[y + 1][x - 1].getIcon() == null) {
                findAllEmptyFields(fields2, x - 1, y + 1, fields, mouseListener);
            }
        }
        if (x < fields.length - 1 && y > 0) {
            if (fields[y - 1][x + 1].getIcon() == null) {
                findAllEmptyFields(fields2, x + 1, y - 1, fields, mouseListener);
            }
        }
        if (x > 0) {
            if (fields[y][x - 1].getIcon() == null) {
                findAllEmptyFields(fields2, x - 1, y, fields, mouseListener);
            }
        }
        if (y > 0) {
            if (fields[y - 1][x].getIcon() == null) {
                findAllEmptyFields(fields2, x, y - 1, fields, mouseListener);
            }
        }
        //shows the border fields with the numbers on them
        if (x < fields.length - 1) {
            if (fields[y][x + 1].getIcon() == null) {
                setFieldPictures(fields, fields2, x + 1, y, mouseListener);
            }
        }
        if (y < fields.length - 1) {
            if (fields[y + 1][x].getIcon() == null) {
                setFieldPictures(fields, fields2, x, y + 1, mouseListener);
            }
        }
        if (y < fields.length - 1 && x > 0) {
            if (fields[y + 1][x - 1].getIcon() == null) {
                setFieldPictures(fields, fields2, x - 1, y + 1, mouseListener);
            }
        }
        if (x < fields.length - 1 && y > 0) {
            if (fields[y - 1][x + 1].getIcon() == null) {
                setFieldPictures(fields, fields2, x + 1, y - 1, mouseListener);
            }
        }
        if (x > 0) {
            if (fields[y][x - 1].getIcon() == null) {
                setFieldPictures(fields, fields2, x - 1, y, mouseListener);
            }
        }
        if (y > 0) {
            if (fields[y - 1][x].getIcon() == null) {
                setFieldPictures(fields, fields2, x, y - 1, mouseListener);
            }
        }
        if (y > 0 && x > 0) {
            if (fields[y - 1][x - 1].getIcon() == null) {
                setFieldPictures(fields, fields2, x - 1, y - 1, mouseListener);
            }
        }
        if (x < fields.length - 1 && y < fields.length - 1) {
            if (fields[y + 1][x + 1].getIcon() == null) {
                setFieldPictures(fields, fields2, x + 1, y + 1, mouseListener);
            }
        }
    }

    /**
     * sets the images to the fields that were clicked
     *
     * @param fields        the two-dimensional JLabel array that stores all fields
     * @param fields2       the two-dimensional boolean that stored the bomb locations
     * @param x             the x coordinate of the clicked field
     * @param y             the y coordinate of the clicked field
     * @param mouseListener the mouse listener to deactivate the clicked field
     */
    private void setFieldPictures(JLabel[][] fields, boolean[][] fields2, int x, int y, MouseListener mouseListener) {
        int countBombs = countBombs(fields2, x, y);
        if (countBombs == 0) {
            findAllEmptyFields(fields2, x, y, fields, mouseListener);
        } else if (countBombs == 1) {
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper1.jpg"));
            fields[y][x].setIcon(imageIcon);
            countClicked();
            fields[y][x].removeMouseListener(mouseListener);
        } else if (countBombs == 2) {
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper2.jpg"));
            fields[y][x].setIcon(imageIcon);
            countClicked();
            fields[y][x].removeMouseListener(mouseListener);
        } else if (countBombs == 3) {
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper3.jpg"));
            fields[y][x].setIcon(imageIcon);
            countClicked();
            fields[y][x].removeMouseListener(mouseListener);
        } else if (countBombs == 4) {
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper4.jpg"));
            fields[y][x].setIcon(imageIcon);
            countClicked();
            fields[y][x].removeMouseListener(mouseListener);
        } else if (countBombs == 5) {
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper5.jpg"));
            fields[y][x].setIcon(imageIcon);
            countClicked();
            fields[y][x].removeMouseListener(mouseListener);
        } else if (countBombs == 6) {
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper6.jpg"));
            fields[y][x].setIcon(imageIcon);
            countClicked();
            fields[y][x].removeMouseListener(mouseListener);
        } else if (countBombs == 7) {
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper7.jpg"));
            fields[y][x].setIcon(imageIcon);
            countClicked();
            fields[y][x].removeMouseListener(mouseListener);
        } else if (countBombs == 8) {
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper8.jpg"));
            fields[y][x].setIcon(imageIcon);
            countClicked();
            fields[y][x].removeMouseListener(mouseListener);
        }
    }

    /**
     * A more efficient way to check how many bombs are around a field.
     * (Unfortunately not yet fully functional, needs to be improved or removed) TODO
     *
     * @param fields2 the two-dimensional boolean that stored the bomb locations
     * @param x       the x coordinates of the field to be tested
     * @param y       the y coordinates of the field to be tested
     * @return the number of bombs around the given field
     */
    private int countBombs2(boolean[][] fields2, int x, int y) {
        int count = 0;

        if (x > 0) {
            if (fields2[x - 1][y]) {
                count++;
            }
        }
        if (y > 0) {
            if (fields2[x][y - 1]) {
                count++;
            }
        }
        if (x < fields2.length - 1) {
            if (fields2[x + 1][y]) {
                count++;
            }
        }
        if (y < fields2.length - 1) {
            if (fields2[x][y + 1]) {
                count++;
            }
        }
        if (y > 0 && x > 0) {
            if (fields2[x - 1][y - 1]) {
                count++;
            }
        }
        if (x > 0 && y < fields2.length - 1) {
            if (fields2[x - 1][y + 1]) {
                count++;
            }
        }
        if (y > 0 && x < fields2.length - 1) {
            if (fields2[x + 1][y - 1]) {
                count++;
            }
        }
        if (y < fields2.length - 1 && x < fields2.length - 1) {
            if (fields2[x + 1][y + 1]) {
                count++;
            }
        }
        return count;
    }


    /**
     * Counts the number of bombs around a given field
     * (inefficient method still needs to be changed or replaced) TODO
     *
     * @param fields2 the two-dimensional boolean that stored the bomb locations
     * @param x       the x coordinates of the field to be tested
     * @param y       the y coordinates of the field to be tested
     * @return the number of bombs around the given field
     */
    private int countBombs(boolean[][] fields2, int x, int y) {
        int count = 0;
        if (x == 0 && y == 0) {
            if (fields2[y][x + 1]) {
                count++;
            }
            if (fields2[y + 1][x]) {
                count++;
            }
            if (fields2[y + 1][x + 1]) {
                count++;
            }
        } else if (x == 0 && y == fields2.length - 1) {
            if (fields2[y][x + 1]) {
                count++;
            }
            if (fields2[y - 1][x]) {
                count++;
            }
            if (fields2[y - 1][x + 1]) {
                count++;
            }
        } else if (y == 0 && x == fields2.length - 1) {
            if (fields2[y][x - 1]) {
                count++;
            }
            if (fields2[y + 1][x]) {
                count++;
            }
            if (fields2[y + 1][x - 1]) {
                count++;
            }
        } else if (y == fields2.length - 1 && x == fields2.length - 1) {
            if (fields2[y][x - 1]) {
                count++;
            }
            if (fields2[y - 1][x]) {
                count++;
            }
            if (fields2[y - 1][x - 1]) {
                count++;
            }
        } else if (x == 0) {
            if (fields2[y][x + 1]) {
                count++;
            }
            if (fields2[y - 1][x]) {
                count++;
            }
            if (fields2[y + 1][x]) {
                count++;
            }
            if (fields2[y + 1][x + 1]) {
                count++;
            }
            if (fields2[y - 1][x + 1]) {
                count++;
            }
        } else if (y == 0) {
            if (fields2[y][x - 1]) {
                count++;
            }
            if (fields2[y][x + 1]) {
                count++;
            }
            if (fields2[y + 1][x]) {
                count++;
            }
            if (fields2[y + 1][x + 1]) {
                count++;
            }
            if (fields2[y + 1][x - 1]) {
                count++;
            }
        } else if (x == fields2.length - 1) {
            if (fields2[y][x - 1]) {
                count++;
            }
            if (fields2[y - 1][x]) {
                count++;
            }
            if (fields2[y + 1][x]) {
                count++;
            }
            if (fields2[y - 1][x - 1]) {
                count++;
            }
            if (fields2[y + 1][x - 1]) {
                count++;
            }
        } else if (y == fields2.length - 1) {
            if (fields2[y][x - 1]) {
                count++;
            }
            if (fields2[y][x + 1]) {
                count++;
            }
            if (fields2[y - 1][x]) {
                count++;
            }
            if (fields2[y - 1][x - 1]) {
                count++;
            }
            if (fields2[y - 1][x + 1]) {
                count++;
            }
        } else {
            if (fields2[y][x - 1]) {
                count++;
            }
            if (fields2[y][x + 1]) {
                count++;
            }
            if (fields2[y - 1][x]) {
                count++;
            }
            if (fields2[y + 1][x]) {
                count++;
            }
            if (fields2[y - 1][x - 1]) {
                count++;
            }
            if (fields2[y + 1][x + 1]) {
                count++;
            }
            if (fields2[y - 1][x + 1]) {
                count++;
            }
            if (fields2[y + 1][x - 1]) {
                count++;
            }
        }
        return count;
    }

    /**
     * Resets the field if lost
     *
     * @param x       the number of fields on the x direction
     * @param y       the number of fields on the y direction
     * @param fields  the two-dimensional JLabel array that stores all fields
     * @param fields2 the two-dimensional boolean that stored the bomb locations
     */
    private void lostGame(int x, int y, JLabel[][] fields, boolean fields2[][]) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (fields2[i][j]) {
                    ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeperBomb.jpg"));
                    fields[i][j].setIcon(imageIcon);
                }
            }
        }
        JLabel nachricht = new JLabel("Du hast das Spiel verloren!");
        nachricht.setFont(new Font("SansSerif", Font.BOLD, 25));
        JOptionPane.showMessageDialog(null, nachricht);
        dispose();
    }

    /**
     * Creates the bombs for the playing field
     *
     * @param x           the number of fields on the x direction
     * @param y           the number of fields on the y direction
     * @param anzahlBombs the nummber of bombs
     * @return the two-dimensional boolean that stored the bomb locations
     */
    private boolean[][] createField(int x, int y, int anzahlBombs) {
        boolean[][] fields2 = new boolean[y][x];
        Random random = new Random();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                fields2[i][j] = false;
            }
        }
        for (int i = 0; i < anzahlBombs; i++) {
            int randomX = random.nextInt(x);
            int randomY = random.nextInt(y);
            if (fields2[randomY][randomX]) {
                i--;
            }
            fields2[randomY][randomX] = true;
        }
        return fields2;
    }
}
