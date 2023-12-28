package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Window extends JFrame {
    public static final int WINDOW_HEIGHT = 550;
    public static final int WINDOW_WIDTH = 900;
    private Table table;
    private final JLabel turn;
    private String playerWord;
    private final JLabel X;
    private final JLabel O;
    private int xCount;
    private int oCount;


    public Window() {
        this.setTitle("X_O");
        this.table = new Table(this);
        this.turn = new JLabel("X turn");
        this.turn.setFont(new Font("Arial", Font.PLAIN, 32));
        this.turn.setBounds(407, 40, 200, 70);
        this.add(this.turn);
        this.X = new JLabel("<html><font color=black>X_" + this.xCount + "</font></html>");
        this.X.setFont(new Font("Arial", Font.PLAIN, 32));
        this.X.setBounds(10, 0, 200, 70);
        this.add(this.X);
        this.O = new JLabel("<html><font color=black>O_" + this.oCount + "</font></html>");
        this.O.setFont(new Font("Arial", Font.PLAIN, 32));
        this.O.setBounds(10, 80, 200, 70);
        this.add(this.O);
        this.addTableToWindow();
        this.setLayout(null);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        new PopupFrame(table);
    }

    private void addTableToWindow() {
        List<List<Square>> squares = this.table.getTable();
        for (List<Square> list : squares) {
            for (Square square : list) {
                this.add(square);
            }
        }
    }

    private void removeTableFromWindow() {
        List<List<Square>> squares = this.table.getTable();
        for (List<Square> list : squares) {
            for (Square square : list) {
                this.remove(square);
                this.revalidate();
                this.repaint();
            }
        }
    }

    public void restartGame() {
        this.removeTableFromWindow();
        this.table = new Table(this);
        this.table.setPlayerWord(this.playerWord);
        this.setTurn("X");
        this.addTableToWindow();
    }

    public void setTurn(String currentTurn) {
        this.turn.setText(currentTurn + " turn");
    }

    public void setPlayerWord(String playerWord) {
        this.playerWord = playerWord;
    }
    public void incrementXCount(){
        this.xCount++;
        this.X.setText("<html><font color=black>X_" + this.xCount + "</font></html>");
    }
    public void incrementOCount(){
        this.oCount++;
        this.O.setText("<html><font color=black>O_" + this.oCount + "</font></html>");
    }

}
