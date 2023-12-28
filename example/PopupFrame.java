package org.example;

import javax.swing.*;
import java.awt.*;


public class PopupFrame extends JFrame {
    private final JButton xButton;
    private final JButton oButton;
    private final JLabel infoLabel;


    public PopupFrame(Table table) {
        this.xButton = new JButton("X");
        this.oButton = new JButton("O");
        this.infoLabel = new JLabel("<html><div style='text-align: center;'>Choose your player! Remember, the X is always first!.</div></html>");
        this.componentLocation();
        this.buttonsJob(table);
        this.setResizable(true);
        int WINDOW_WIDTH = 310;
        int WINDOW_HEIGHT = 150;
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void componentLocation() {
        this.infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        infoLabel.setForeground(Color.RED);
        this.getContentPane().add(infoLabel);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(xButton);
        buttonPanel.add(oButton);
        this.add(buttonPanel, BorderLayout.PAGE_END);
    }

    private void buttonsJob(Table table) {
        this.buttonJob(this.xButton, "X", table);
        this.buttonJob(this.oButton, "O", table);
    }

    private void buttonJob(JButton button, String c, Table table) {
        button.addActionListener(e -> {
            table.setPlayerWord(c);
            this.dispose();
        });
    }

}
