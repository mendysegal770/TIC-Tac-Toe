package org.example;

import javax.swing.*;
import java.awt.*;

public class WinnerMessage extends JFrame {

    private final JLabel messageLabel;
    private final JButton restartButton;
    private final Window window;

    public WinnerMessage(Window window, String winner) {
        this.window = window;
        this.messageLabel = new JLabel(winner);
        this.restartButton = new JButton("restart");
        this.messageLocation();
        this.restartJub();
        this.setResizable(true);
        int WINDOW_WIDTH = 310;
        int WINDOW_HEIGHT = 150;
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void messageLocation() {
        this.messageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.getContentPane().add(messageLabel);
        this.restartButton.setBackground(Color.YELLOW);
        this.add(this.restartButton, BorderLayout.PAGE_END);
    }

    private void restartJub() {
        this.restartButton.addActionListener(e -> {
            this.dispose();
            this.window.restartGame();
        });
    }
}
