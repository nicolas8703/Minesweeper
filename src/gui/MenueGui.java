package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Nicolas Feige
 *
 * @author Nicolas Feige
 * @version 1.0
 * @date 14.9.2020
 */


public class MenueGui extends JFrame {
    private JComboBox schwierigkeitstufenAuswahl = new JComboBox();
    private JTextField eingabeFeldgrösse = new JTextField();
    private JTextField eingabeAnzahlBomben = new JTextField();

    private JPanel hauptPanel = new JPanel();
    private JPanel abschnitt1Panel = new JPanel();
    private JPanel abschnitt2Panel = new JPanel();
    private JPanel abschnitt3Panel = new JPanel();
    private JPanel startPanel = new JPanel();
    private JPanel startPanelSplit = new JPanel();
    private JLabel titelSchwierigkeit = new JLabel("Schwierigkeitsstufen: ");
    private JLabel titelFeldgrösse = new JLabel("Grösse des Feldes (Format: 10 x 10)");
    private JButton start = new JButton("Start");
    private JLabel titelAnzahlBomben = new JLabel("Anzahl Bomben: ");


    MenueGui(){
        super("Minesweeper");
        getContentPane().add(hauptPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(330, 300);
        setResizable(false);

        schwierigkeitstufenAuswahl.addItem("Anfänger");
        schwierigkeitstufenAuswahl.addItem("Fortgeschrittene");
        schwierigkeitstufenAuswahl.addItem("Profis");
        schwierigkeitstufenAuswahl.addItem("Benutzerdefiniert");
        hauptPanel.setLayout(new GridLayout(4, 1));
        hauptPanel.add(abschnitt1Panel);
        abschnitt1Panel.setLayout(new GridLayout(2, 1));
        abschnitt1Panel.add(titelSchwierigkeit);
        abschnitt1Panel.add(schwierigkeitstufenAuswahl);

        hauptPanel.add(abschnitt2Panel);
        abschnitt2Panel.setLayout(new GridLayout(2, 1));

        hauptPanel.add(abschnitt3Panel);
        abschnitt3Panel.setLayout(new GridLayout(2, 1));
        abschnitt2Panel.add(titelFeldgrösse);
        abschnitt2Panel.add(eingabeFeldgrösse);
        abschnitt3Panel.add(titelAnzahlBomben);
        abschnitt3Panel.add(eingabeAnzahlBomben);

        hauptPanel.add(startPanel);
        startPanel.setLayout(new GridLayout(2, 1));
        startPanel.add(startPanelSplit);
        startPanel.add(start);
        start.setFont(new Font("SansSerif", Font.BOLD, 12));
        showCustom();

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(schwierigkeitstufenAuswahl.getSelectedItem().equals("Anfänger")){
                    GameGui gameGui = new GameGui(8,8, 10);
                }else if(schwierigkeitstufenAuswahl.getSelectedItem().equals("Fortgeschrittene")){
                    GameGui gameGui = new GameGui(16,16, 40);
                }else if(schwierigkeitstufenAuswahl.getSelectedItem().equals("Profis")){
                    GameGui gameGui = new GameGui(25,25, 131);
                }else if(schwierigkeitstufenAuswahl.getSelectedItem().equals("Benutzerdefiniert")){
                    if(eingabeAnzahlBomben.getText().isEmpty()){
                        JLabel nachricht = new JLabel("Es muss etwas eingegeben werden!");
                        nachricht.setFont(new Font("SansSerif", Font.BOLD, 25));
                        JOptionPane.showMessageDialog(null, nachricht);
                    }else if(eingabeFeldgrösse.getText().isEmpty()) {
                        JLabel nachricht = new JLabel("Es muss etwas eingegeben werden!");
                        nachricht.setFont(new Font("SansSerif", Font.BOLD, 25));
                        JOptionPane.showMessageDialog(null, nachricht);
                    }else {
                        GameGui gameGui = new GameGui(25,25, Integer.parseInt(eingabeAnzahlBomben.getText()));
                    }

                }
            }
        });
    }

    private void showCustom(){
    }

    public static void main(String[] args) {
        MenueGui g = new MenueGui();
    }
}
