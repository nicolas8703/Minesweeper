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


public class GameGui extends JFrame{

    private JPanel mainPanel = new JPanel();
    private JPanel sidePanel = new JPanel();
    private JPanel gamePanel = new JPanel();

    GameGui(int x, int y){
        super("Minesweeper");
        JLabel[][] fields = new JLabel[y][x];
        getContentPane().add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 600);
        setResizable(true);

        mainPanel.setLayout(new BorderLayout());
        //mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        //sidePanel.setBackground(Color.CYAN);
        //gamePanel.setBackground(Color.MAGENTA);
        gamePanel.setLayout(new GridLayout(y,x));

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                fields[i][j] = new JLabel();
                gamePanel.add(fields[i][j]);
                fields[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                fields[i][j].setBackground(Color.darkGray);
                fields[i][j].setOpaque(true);
            }
        }

        repaint();
        boolean[][] fields2;
        fields2 = createField(x,y);


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
                     fields[finalI][finalJ].setOpaque(true);
                     if(fields2[finalI][finalJ]){
                         ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeperBomb.jpg"));
                         fields[finalI][finalJ].setIcon(imageIcon);
                         lostGame(x, y, fields, fields2);
                     }else{
                         int countBombs;
                         countBombs =  countBombs(fields2, finalJ, finalI);
                         if(countBombs == 0){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper0.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }else if (countBombs == 1){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper1.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }else if (countBombs == 2){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper2.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }else if (countBombs == 3){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper3.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }else if (countBombs == 4){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper4.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }else if (countBombs == 5){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper5.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }else if (countBombs == 6){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper6.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }else if (countBombs == 7){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper7.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }else if (countBombs == 8){
                             ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeper8.jpg"));
                             fields[finalI][finalJ].setIcon(imageIcon);
                         }
                     }
                     repaint();
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

    private int countBombs(boolean[][] fields2, int x, int y) {
        int count = 0;
        if(x == 0) {
            if (fields2[y][x+1]) {
                count++;
            }
            if (fields2[y-1][x]) {
                count++;
            }
            if (fields2[y+1][x]) {
                count++;
            }
            if (fields2[y+1][x+1]) {
                count++;
            }
            if (fields2[y-1][x+1]) {
                count++;
            }
        }else if(y == 0){
            if(fields2[y][x-1]){
                count++;
            }
            if(fields2[y][x+1]){
                count++;
            }
            if(fields2[y+1][x]){
                count++;
            }
            if(fields2[y+1][x+1]){
                count++;
            }
            if(fields2[y+1][x-1]){
                count++;
            }
        }else {
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

    private void lostGame(int x, int y, JLabel[][] fields, boolean fields2[][]){
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (fields2[i][j]){
                    ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/minesweeperBomb.jpg"));
                    fields[i][j].setIcon(imageIcon);
                }
            }
        }
        JLabel nachricht = new JLabel("Du hast das Spiel verlohren!");
        nachricht.setFont(new Font("SansSerif", Font.BOLD, 25));
        JOptionPane.showMessageDialog(null, nachricht);
        dispose();
    }

    private boolean[][] createField(int x, int y) {
        boolean[][] fields2 = new boolean[y][x];
        Random random = new Random();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                fields2[i][j] = false;
            }
        }
        for (int i = 0; i < 40; i++) {
            int randomX = random.nextInt(x);
            int randomY = random.nextInt(y);
            if(fields2[randomY][randomX]){
                i--;
            }
            fields2[randomY][randomX] = true;
        }
        return fields2;
    }
}
