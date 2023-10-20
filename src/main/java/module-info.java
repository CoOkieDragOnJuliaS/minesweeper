module com.example.minesweeper_neu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.example.minesweeper_neu to javafx.fxml;
    exports com.example.minesweeper_neu;
}