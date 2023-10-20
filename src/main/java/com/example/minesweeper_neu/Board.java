package com.example.minesweeper_neu;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    public static final int CELL_SIZE = 15;
    public static final int ROWS = 25;
    public static final int COLS = 25;
    public static final int NUM_IMAGES = 13;
    public static final int NUM_MINES = 50;

    // Add further constants or let the cell keep track of its state.

    private Cell cells[][];
    private Image[] images;
    private int cellsUncovered;
    private int minesMarked;
    private boolean gameOver;

    /**
     * Constructor preparing the game. Playing a new game means creating a new Board.
     */
    public Board(){
        cells = new Cell[ROWS][COLS];
        cellsUncovered = 0;
        minesMarked = 0;
        gameOver = false;
        loadImages();
        // at the beginning every cell is covered

        for(int rows = 0; rows < ROWS; rows++){
            for(int cols = 0; cols < COLS; cols++){
                cells[rows][cols] = new Cell(images[10], 0, rows, cols);
            }
        }

        // set neighbours for convenience
        for(int rows = 0; rows < ROWS; rows++){
            for(int cols = 0; cols < COLS; cols++){
                //All neighbours
                cells[rows][cols].setNeighbours(computeNeighbours(rows, cols));
            }
        }

        // then we place NUM_MINES on the board and adjust the neighbours (1,2,3,4,... if not a mine already)
        placeRandomMines();

    }

    public void placeRandomMines(){
        for(int i = 0; i < NUM_MINES; i++){
            //https://www.geeksforgeeks.org/generating-random-numbers-in-java/
            //int randomRow = ThreadLocalRandom.current().nextInt(0, ROWS - 1);
            //int randomColumn = ThreadLocalRandom.current().nextInt(0, COLS - 1);
            int randomRow = getRandomNumberInts(0, ROWS - 1);
            int randomColumn = getRandomNumberInts(0, COLS - 1);

            Cell currentCell = cells[randomRow][randomColumn];
            currentCell.setState(9);
            List<Cell> neighboursOfCurrentCell = currentCell.getNeighbours();
            //https://howtodoinjava.com/java/collections/arraylist/arraylist-foreach/
            currentCell.getNeighbours().forEach((neighbourCell) -> {
                if(neighbourCell.getState() != 9) {  //9 is the mine state
                    neighbourCell.setState(neighbourCell.getState() + 1);
                    neighbourCell.updateImage(images[neighbourCell.getState()]);
                }else{
                    List<Cell> sameNeighbours = neighbourCell.getNeighbours().stream()
                            .filter(neighboursOfCurrentCell::contains)
                            .toList();
                    neighbourCell.setState(neighbourCell.getState() + 1);
                    neighbourCell.updateImage(images[neighbourCell.getState()]);
                }
            });
        }
    }

    public boolean uncover(int row, int col) {
        Cell currentCell = cells[row][col];
        // TODO uncover the cell, check if it is a bomb, if it is an empty cell you may! uncover all adjacent empty cells.
        if(currentCell.getState() == 9){
            gameOver = true;
        }else if(currentCell.getState() == 0){
            uncoverEmptyCells(currentCell);
        }
        else{
            currentCell.uncoverCell(images[currentCell.getState()]);
        }
        return true; // could be a void function as well
    }

    public boolean markCell(int row, int col) {
        Cell currentCell = cells[row][col];
        if(!currentCell.isMarkedAsMine()){
            currentCell.setMarkedAsMine(true);
            this.minesMarked += 1;
            currentCell.updateImage(images[11]); //Flag for marking as mine
        }
        // TODO mark the cell if it is not already marked.
        return true;
    }

    public void uncoverEmptyCells(Cell cell) {
        cell.getNeighbours()
                .forEach((cellNeighbour) -> {
                    if(cellNeighbour.getState() == 0){
                        uncover(cellNeighbour.getRow(), cellNeighbour.getColumn());
                    }
                });
    }


    public void uncoverAllCells(){
        for(int rows = 0; rows < ROWS; rows++) {
            for (int cols = 0; cols < COLS; cols++) {
                Cell currentCell = cells[rows][cols];
                currentCell.uncoverCell(images[currentCell.getState()]);
            }
        }
        //TODO Uncover everything in case a mine was hit and the game is over.
    }


    public List<Cell> computeNeighbours(int rows, int cols){
        List<Cell> listOfNeighbours = new ArrayList<>();
        // TODO get all the neighbours for a given cell. this means coping with mines at the borders.
        //https://stackoverflow.com/questions/47295392/minesweeper-algorithm-for-finding-neighbours
                //neighbour upper left
                if(rows - 1 >= 0 && cols - 1 >= 0){
                    listOfNeighbours.add(cells[rows - 1][cols - 1]);
                }
                //neighbour above
                if(rows - 1 >= 0){
                    listOfNeighbours.add(cells[rows - 1][cols]);
                }
                //neighbour upper right
                if(rows - 1 >= 0 && cols + 1 < COLS){
                    listOfNeighbours.add(cells[rows - 1][cols + 1]);
                }
                //neighbour righthand
                if(cols + 1 < COLS){
                    listOfNeighbours.add(cells[rows][cols + 1]);
                }
                //neighbour down right
                if(rows + 1 < ROWS && cols + 1 < COLS){
                    listOfNeighbours.add(cells[rows + 1][cols + 1]);
                }
                //neighbour below
                if(rows + 1 < ROWS){
                    listOfNeighbours.add(cells[rows + 1][cols]);
                }
                //neighbour down left
                if(rows + 1 < ROWS && cols - 1 >= 0){
                    listOfNeighbours.add(cells[rows + 1][cols - 1]);
                }
                //neighbour lefthand
                if(cols - 1 >= 0){
                    listOfNeighbours.add(cells[rows][cols - 1]);
                }

                cells[rows][cols].setNeighbours(listOfNeighbours);

        return listOfNeighbours;
    }

    /**
     * Loads the given images into memory. Of course you may use your own images and layouts.
     */
    private void loadImages(){
        images = new Image[NUM_IMAGES];
        for(int i = 0; i < NUM_IMAGES; i++){
            var path = "src/main/resources/com/example/minesweeper_neu/" + i + ".png";
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);
                this.images[i] = new Image(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Computes a random int number between min and max.
     * @param min the lower bound. inclusive.
     * @param max the upper bound. inclusive.
     * @return a random int.
     */
    private int getRandomNumberInts(int min, int max){
        Random random = new Random();
        return random.ints(min,(max+1)).findFirst().getAsInt();
    }

    public int getMinesMarked() {
        return minesMarked;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getCellsUncovered() {
        return cellsUncovered;
    }

}