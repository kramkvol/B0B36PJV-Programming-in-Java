package cz.cvut.fel.Interface;

import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelTemplate;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public interface CreateSudokuField<T extends CreateLevelTemplate> {
    void events();
    void updateVisibility();
    // Action on buttons
    void actionOnContinueButton();
    void actionOnRestartButton();
    void actionOnMainMenuButton();
    CreateModeInterface<? extends CreateLevelTemplate> getCurrentLevelMode();

    // Setters
    void update(T createLevelParent);

    // Sudoku grid
    void loadSudokuFiled();
    void updateGridWithSavedState();
    void startGameTimer();
    VBox createTopBoxForSudokuField();
    VBox createCentralBoxForSudokuField();
    GridPane showSudokuGrid(GridPane gridPane);
    VBox createBottomBoxForSudokuField();
    TextField createSudokuCell(int row, int col);
    String getValidNumbersForCell(int row, int col);

    // All for creating Cells
    GridPane getGridPane();
    void handleCellTextChange(TextField cell, int row, int col, String newValue);
    void validateAndAssignValue(TextField cell, int row, int col, int value);
    void setAssignedValue(TextField cell, int row, int col, int value, String str);

    void setEmptyValue(TextField cell, int row, int col);

    void setException(TextField cell, int row, int col, IllegalArgumentException exception);
    boolean canParseInt(String strValue);
    boolean containsZero(int[][] array);
    void levelCompleted();
    boolean checkRowToSetNumber(int row, int num);
    boolean checkColToSetNumber(int col, int num);
    boolean checkSquareToSetNumber(int row, int col, int num);
    void nextLevelButton();
    void undoOnAction();
    void redoOnAction();
}
