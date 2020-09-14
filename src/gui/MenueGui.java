package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Nicolas Feige
 *
 * @author Nicolas Feige
 * @version 1.0
 * @date 14.9.2020
 */


public class MenueGui extends JFrame {
    private JComboBox algorithmusAuswahl = new JComboBox();
    private JTextField eingabePause = new JTextField();
    private JTextField eingabeZahlen = new JTextField();

    private JPanel hauptPanel = new JPanel();
    private JPanel abschnitt1Panel = new JPanel();
    private JPanel abschnitt2Panel = new JPanel();
    private JPanel abschnitt3Panel = new JPanel();
    private JPanel startPanel = new JPanel();
    private JPanel startPanelSplit = new JPanel();
    private JLabel titelAlgortihmus = new JLabel("Wie gross soll das Feld sein: ");
    private JLabel titelPause = new JLabel("Pause zwischen den Sortierungen: (in Millisekunden)");
    private JButton start = new JButton("Start");


    MenueGui(){
        super("Sortieralgorithmus Visualisierung");
        getContentPane().add(hauptPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(330, 300);
        setResizable(false);

        algorithmusAuswahl.addItem("8 x 8");
        algorithmusAuswahl.addItem("16 x 16");
        algorithmusAuswahl.addItem("25 x 25");
        hauptPanel.setLayout(new GridLayout(4, 1));
        hauptPanel.add(abschnitt1Panel);
        abschnitt1Panel.setLayout(new GridLayout(2, 1));
        abschnitt1Panel.add(titelAlgortihmus);
        abschnitt1Panel.add(algorithmusAuswahl);


        hauptPanel.add(startPanel);
        startPanel.setLayout(new GridLayout(2, 1));
        startPanel.add(startPanelSplit);
        startPanel.add(start);
        start.setFont(new Font("SansSerif", Font.BOLD, 12));


        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(algorithmusAuswahl.getSelectedItem().equals("8 x 8")){
                    GameGui gameGui = new GameGui(8,8);
                }else if(algorithmusAuswahl.getSelectedItem().equals("16 x 16")){
                    GameGui gameGui = new GameGui(16,16);
                }else if(algorithmusAuswahl.getSelectedItem().equals("25 x 25")){
                    GameGui gameGui = new GameGui(25,25);
                }
            }
        });
    }

    public static void main(String[] args) {
        MenueGui g = new MenueGui();
    }
}
