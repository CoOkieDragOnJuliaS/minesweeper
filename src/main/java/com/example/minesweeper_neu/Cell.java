package com.example.minesweeper_neu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public class Cell extends Pane {


    public static final int CELL_SIZE = 15;
    private ImageView view;
    private List<Cell> neighbours;

    private final int row;
    private final int column;
    private boolean markedAsMine;

    private int state;
    private boolean uncovered;

    // TODO add addtional variables you need. for state...

    public Cell(Image img, int state, int row, int column) {
        view = new ImageView(img);
        getChildren().add(view);
        this.state = state;
        this.uncovered = false;
        this.row = row;
        this.column = column;
        this.markedAsMine = false;
        // TODO add stuff here if needed.
    }

    public void uncoverCell(Image image){
        this.uncovered = true;
        updateImage(image);
    }

    public void setNeighbours(List<Cell> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Cell> getNeighbours() {
        return neighbours;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isMarkedAsMine() {
        return markedAsMine;
    }

    public void setMarkedAsMine(boolean markedAsMine) {
        this.markedAsMine = markedAsMine;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isUncovered() {
        return uncovered;
    }

    public void setUncovered(boolean uncovered) {
        this.uncovered = uncovered;
    }

    public void updateImage(Image img) {
        this.view.setImage(img);
    }
}
