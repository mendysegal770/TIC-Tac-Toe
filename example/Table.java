package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Table {
    private int xSquare = (Window.WINDOW_WIDTH - Square.WIDTH) / 2 - Square.WIDTH;
    private int ySquare = (Window.WINDOW_HEIGHT - Square.HEIGHT * 3 - 50) / 2;
    private final int TABLE_SIZE = 3;
    private List<List<Square>> copyTable;
    private List<List<Square>> table;
    private List<List<Integer>> playerIntegers;
    private List<List<Integer>> computerIntegers;
    private List<List<Integer>> copyIntegers;
    private List<Integer> markRow;
    private String playerWord;
    private String computerWord;
    private Window window;

    public Table(Window window) {
        this.window = window;
        this.initializationList();
        this.setIndexes();
        this.create6OptionsToWine();
        this.playerMove();
    }

    private void initializationList() {
        this.copyTable = new ArrayList<>();
        this.table = new ArrayList<>();
        this.markRow = new ArrayList<>();
        int initial = this.xSquare;
        for (int i = 0; i < this.TABLE_SIZE; i++) {
            this.xSquare = initial;
            List<Square> row = new ArrayList<>();
            this.copyTable.add(this.initializationRow(row));
            this.ySquare += Square.HEIGHT;
        }
    }

    private List<Square> initializationRow(List<Square> row) {
        for (int i = 0; i < this.TABLE_SIZE; i++) {
            Square square = new Square();
            square.setBounds(this.xSquare, this.ySquare, Square.WIDTH, Square.HEIGHT);
            row.add(square);
            this.xSquare += Square.WIDTH;
        }
        return row;
    }

    private void setIndexes() {
        int index = 1;
        for (List<Square> squares : this.copyTable) {
            for (Square square : squares) {
                square.setIndex(index);
                index++;
            }
        }
        this.copyTableToTable();
    }

    private void copyTableToTable() {
        for (List<Square> squares : this.copyTable) {
            this.table.add(new ArrayList<>(squares));
        }
    }

    private void create6OptionsToWine() {
        this.playerIntegers = new ArrayList<>();
        this.computerIntegers = new ArrayList<>();
        this.copyIntegers = new ArrayList<>();
        List<Integer> subIntegers = new ArrayList<>();
        for (int i = 1; i <= TABLE_SIZE * TABLE_SIZE; i++) {
            subIntegers.add(i);
            if (i % TABLE_SIZE == 0) {
                this.playerIntegers.add(subIntegers);
                subIntegers = new ArrayList<>();
            }
        }
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                Integer value = this.playerIntegers.get(j).get(i);
                subIntegers.add(value);
            }
            this.playerIntegers.add(subIntegers);
            subIntegers = new ArrayList<>();
        }

        for (int i = 0; i < TABLE_SIZE; i++) {
            subIntegers.add(this.playerIntegers.get(i).get(i));
        }
        this.playerIntegers.add(subIntegers);
        subIntegers = new ArrayList<>();
        for (int i = 0; i < TABLE_SIZE; i++) {
            subIntegers.add(this.playerIntegers.get(i).get(TABLE_SIZE - i - 1));
        }
        this.playerIntegers.add(subIntegers);
        this.copyListToLists();
    }

    private void copyListToLists() {
        for (List<Integer> list : this.playerIntegers) {
            this.computerIntegers.add(new ArrayList<>(list));
            this.copyIntegers.add(new ArrayList<>(list));
        }
    }


    private void playerMove() {
        if (!this.copyTable.isEmpty()) {
            for (List<Square> list : this.copyTable) {
                for (Square square : list) {
                    square.addActionListener(e -> {
                        if (!square.isDONE()) {
                            square.setWord(this.playerWord);
                            square.setDONE(true);
                            square.setColor("black");
                            this.window.setTurn(this.computerWord);
                            list.remove(square);
                            if (list.size() == 0) {
                                this.copyTable.remove(list);
                            }
                            this.updateTheTable(square.getIndex(), -1, this.playerIntegers);
                            if (this.checkWinner(-TABLE_SIZE, this.playerIntegers)) {
                                new Thread(() -> {
                                   this.upDateStatus(this.playerWord);
                                    Utils.sleep(1000);
                                    new WinnerMessage(this.window, this.playerWord + " is winner");
                                }).start();

                            } else {
                                this.computerMove();
                            }
                        }
                    });
                }
            }
        }
        else {
            new WinnerMessage(this.window, "nobody is winner");
        }
    }

    private void computerMove() {
        if (!this.copyTable.isEmpty()) {
            new Thread(() -> {
                Random random = new Random();
                Utils.sleep(1000);
                int index = random.nextInt(this.copyTable.size());
                List<Square> list = this.copyTable.get(index);
                int i = random.nextInt(list.size());
                Square square = list.get(i);
                square.setWord(this.computerWord);
                square.setDONE(true);
                square.setColor("white");
                this.window.setTurn(this.playerWord);
                list.remove(square);
                if (list.size() == 0) {
                    this.copyTable.remove(list);
                }
                this.updateTheTable(square.getIndex(), 0, this.computerIntegers);
                if (this.checkWinner(0, this.computerIntegers)) {
                    new Thread(() -> {
                        this.upDateStatus(this.computerWord);
                        Utils.sleep(1000);
                        new WinnerMessage(this.window, this.computerWord + " is winner");
                    }).start();
                }
                else {
                    this.playerMove();
                }
            }).start();
        }
        else {
            new WinnerMessage(this.window, "nobody is winner");
        }
    }

    private void updateTheTable(int index, int change, List<List<Integer>> integers) {
        for (List<Integer> list : integers) {
            for (int i = 0; i < list.size(); i++) {
                Integer value = list.get(i);
                if (value == index) {
                    list.set(i, change);
                    break;
                }
            }
        }
    }

    private boolean checkWinner(int sum, List<List<Integer>> integers) {
        for (List<Integer> list : integers) {
            if (sum(list) == sum) {
                int index = integers.indexOf(list);
                this.markRow = this.copyIntegers.get(index);
                this.markTheWinner(Color.red);
                return true;
            }
        }
        return false;
    }

    private void markTheWinner(Color newColor) {
        for (Integer value : this.markRow) {
            for (List<Square> row : this.table) {
                for (Square square : row) {
                    if (square.getIndex().equals(value)) {
                        square.setNewBackGround(newColor);
                        break;
                    }
                }
            }
        }
    }

    public List<List<Square>> getTable() {
        return this.table;
    }

    private static int sum(List<Integer> list) {
        int sum = 0;
        for (Integer value : list) {
            sum += value;
        }
        return sum;
    }

    public void setPlayerWord(String playerWord) {
        this.playerWord = playerWord;
        this.initializationWords();
    }

    public void initializationWords() {
        if (this.playerWord.equals("X")) {
            this.computerWord = "O";
        } else {
            this.computerWord = "X";
            this.computerMove();
        }
        this.window.setPlayerWord(this.playerWord);
    }
    private void upDateStatus(String someOne){
        if (someOne.equals("X")){
            this.window.incrementXCount();
        }
        else {
            this.window.incrementOCount();
        }
    }

}

