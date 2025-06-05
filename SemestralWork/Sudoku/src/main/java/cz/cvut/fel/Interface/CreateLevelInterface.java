package cz.cvut.fel.Interface;

import cz.cvut.fel.logic.Operation;

import java.util.List;

public interface CreateLevelInterface {

    //Getters
    String getMode();

    List<Operation> getOperations();

    String getLevel();
    int[][] getLevelGameBoard();

    int[][] getSolveBord();

    int[][] getSaveGameBoard();

    int[][] getCopy(int[][] original);
    Long getSaveTimer();

    int getValueFromSolveBord(int row, int col);

    //Setters
    void setValueToBoard(int[][] lastSaveGameBoard, int value, int row, int col);

    void setValueToSloveBoard(int value, int row, int col);

    void setForDeleteLastGame();
    void setTimer(long time);

    //Generating values
    int[][] generateLevelGameBoard(String level);

    int[][] generateSolveSudoku();

    int[][] removeCells(int[][] board, int numToRemove);

    boolean solveSudoku(int[][] board);

    boolean isNumberValid(int[][] board, int row, int col, int candidateNumber);

    //Operations with level list
    Operation findLastOperationFor(String filter);
    void addOperationToList( String operationName, int row, int col, int value);
}

