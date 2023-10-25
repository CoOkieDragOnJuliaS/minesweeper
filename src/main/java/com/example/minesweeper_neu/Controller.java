package com.example.minesweeper_neu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class Controller {

    // Model
    private Board board;

    // private
    private boolean isActive;

    // View Fields
    @FXML
    private Label message;
    @FXML
    private GridPane grid;
    @FXML
    private Button restart;


    @FXML
    public void initialize() {
        grid.getChildren().clear();

        isActive = true;
        this.board = new Board();
        Cell[][] cells = this.board.getCells();
        for(int i = 0; i < Board.ROWS; i++){
            RowConstraints rowConstraints = new RowConstraints();
            grid.getRowConstraints().add(rowConstraints);
            for(int j = 0; j < Board.COLS; j++) {
                ColumnConstraints colConstraints = new ColumnConstraints();
                grid.getColumnConstraints().add(colConstraints);
                grid.add(cells[i][j], j, i);
            }
        }
    }

    @FXML
    public void update(MouseEvent event){
        if(isActive) {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                int col = ((int) event.getX()) / Board.CELL_SIZE;
                int row = ((int) event.getY()) / Board.CELL_SIZE;

               /* Node source = (Node)event.getSource();
                int colIndex = GridPane.getColumnIndex(source) / Board.CELL_SIZE;
                int rowIndex = GridPane.getRowIndex(source) / Board.CELL_SIZE;*/
                //try of different values for correct row/column
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (board.uncover(row, col)) {
                        if(board.isGameOver()){
                            board.uncoverAllCells();
                            message.setText("Sorry. Leider verloren.");
                            board.uncoverAllCells();
                            isActive = false;
                        }
                    } else {
                        //TODO Give hint when one tries to uncover a marked cell.
                    }
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    board.markCell(row, col);
                }
                if (board.getCellsUncovered() == (Board.ROWS * Board.COLS - Board.NUM_MINES)
                        && board.getMinesMarked() == Board.NUM_MINES) {
                    message.setText("Glückwunsch! Du hast gewonnen.");
                    isActive = false;
                }
                if(isActive)
                    message.setText(" Marker: " + board.getMinesMarked() + "/" + Board.NUM_MINES);
            }
        }
    }

    @FXML
    public void restart(ActionEvent actionEvent) {
        grid.getChildren().clear();
        initialize();
        message.setText(" Marker: " + board.getMinesMarked() + "/" + Board.NUM_MINES);

        // Testing ;) add Methode fügt nur hinzu. das bestehende bleibt. daher vorher clear.
        /*
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            Cell c = (Cell) node;
            System.out.println(GridPane.getColumnIndex(c) + " " + GridPane.getRowIndex(c));
        }
        */
    }

    public int getWidthOfGrid(){
        return (Board.ROWS * Board.CELL_SIZE) + 10;
    }

    public int getHeightOfGrid(){
        return (Board.COLS * Board.CELL_SIZE) + 10;
    }
}