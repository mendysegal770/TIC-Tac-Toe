package org.example;

import javax.swing.*;
import java.awt.*;

public class Square extends JButton {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private String word;

    private String color;
    private boolean DONE;
    private Color backGround;
    private Integer index;

    public Square() {
        this.setSize(WIDTH, HEIGHT);
        this.word = "";
        this.color = "";
        this.backGround = Color.PINK;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        this.setFont(new Font("Arial", Font.PLAIN, 72));
        this.setBackground(this.backGround);
        this.setText("<html><font color=" + this.color + ">" + this.word + "</font></html>");
    }


    public void setWord(String word) {
        this.word = word;
    }

    public boolean isDONE() {
        return DONE;
    }

    public void setDONE(boolean DONE) {
        this.DONE = DONE;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNewBackGround(Color backGround) {
        this.backGround = backGround;
    }

    public Integer getIndex() {
        return index;
    }

}
