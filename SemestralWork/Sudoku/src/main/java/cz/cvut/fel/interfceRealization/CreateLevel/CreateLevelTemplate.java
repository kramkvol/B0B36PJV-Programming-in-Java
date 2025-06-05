package cz.cvut.fel.interfceRealization.CreateLevel;

import cz.cvut.fel.Interface.CreateLevelInterface;
import cz.cvut.fel.logic.Operation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cz.cvut.fel.logic.Main.logger;

/**
 * The CreateLevelTemplate class implements the CreateLevelInterface and provides the base functionality
 * for creating and managing a Sudoku game level, including generating the solved board, the game board,
 * and handling operations such as setting values and timers.
 */
public class CreateLevelTemplate implements CreateLevelInterface  {

    protected final String mode;
    protected final String level;
    protected long savedTimer;
    protected int[][] solveBord;
    protected int[][] levelGameBoard;
    protected int[][] lastSaveGameBoard;
    protected List<Operation> operations;

    /**
     * Constructor for CreateLevelTemplate.
     *
     * @param mode  the mode of the game
     * @param level the level of difficulty
     */
    public CreateLevelTemplate(String mode, String level) {
        this.mode = mode;
        this.level = level;
        this.savedTimer = 0;
        this.solveBord = generateSolveSudoku();
        this.levelGameBoard = generateLevelGameBoard(level);
        this.lastSaveGameBoard = getCopy(levelGameBoard);
        this.operations = new ArrayList<>();
    }

    @Override
    public String getMode() {
        return mode;
    }

    public void setSolveBord(int [][] bord){
        solveBord = bord;
    }
    @Override
    public List<Operation> getOperations(){
        return this.operations;
    }
    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public int[][] getLevelGameBoard() {
        return levelGameBoard;
    }

    @Override
    public int[][] getSolveBord() {
        return solveBord;
    }

    @Override
    public int[][] getSaveGameBoard() {
        return lastSaveGameBoard;
    }

    /**
     * Creates a deep copy of the given 2D array.
     *
     * @param original the original 2D array to copy
     * @return a deep copy of the original 2D array
     */
    @Override
    public int[][] getCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    @Override
    public Long getSaveTimer() {
        return savedTimer;
    }

    @Override
    public int getValueFromSolveBord(int row, int col){
        return this.solveBord[row][col];
    }

    @Override
    public void setValueToBoard(int[][] lastSaveGameBoard, int value, int row, int col) {
        lastSaveGameBoard[row][col] = value;
    }
    @Override
    public void setValueToSloveBoard(int value, int row, int col) {
        this.solveBord[row][col] = value;
    }

    /**
     * Resets the saved timer, last saved game board, and operations list.
     */
    @Override
    public void setForDeleteLastGame() {
        this.savedTimer = 0;
        this.lastSaveGameBoard = getCopy(levelGameBoard);
        this.operations.clear();
    }

    @Override
    public void setTimer(long time) {
        savedTimer = time;
    }

    /**
     * Generates a game board with cells removed based on the level of difficulty.
     *
     * @return a game board with cells removed
     */
    @Override
    public int[][] generateLevelGameBoard(String level) {
        try {
            int index = 2;
            int intValue = Integer.parseInt(level);
            if (index * intValue + 10 <= 85) {
                return removeCells(solveBord, index * intValue + 15);
            }
            else
                return removeCells(solveBord, 85);
        }
        catch (NumberFormatException e){
            logger.error(e.getMessage());
        }

        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }
        return board;
    }

    /**
     * Generates a solved Sudoku board.
     *
     * @return a solved Sudoku board
     */
    @Override
    public int[][] generateSolveSudoku() {
        int[][] board = new int[9][9];
        if (solveSudoku(board)) {
            return board;
        } else {
            return new int[9][9];
        }
    }

    /**
     * Removes a specified number of cells from the board by setting them to 0.
     *
     * @param board the original Sudoku board
     * @param numToRemove the number of cells to remove
     * @return a new board with the specified number of cells removed
     */
    @Override
    public int[][] removeCells(int[][] board, int numToRemove) {
        int[][] newBoard = getCopy(board);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < numToRemove; i++) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (newBoard[row][col] != 0) {
                newBoard[row][col] = 0;
            }
        }
        return newBoard;
    }

    /**
     * Solves the given Sudoku board using a backtracking algorithm.
     *
     * @param board the Sudoku board to solve
     * @return true if the board was solved, false otherwise
     */
    @Override
    public boolean solveSudoku(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int candidateNumber = 1; candidateNumber <= 9; candidateNumber++) {
                        if (isNumberValid(board, row, col, candidateNumber)) {
                            board[row][col] = candidateNumber;
                            if (solveSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds the last operation based on the given filter.
     *
     * @param filter the filter to apply (e.g., "undo" or "redo")
     * @return the last operation matching the filter, or null if no match is found
     */
    @Override
    public Operation findLastOperationFor(String filter) {
        if (operations.isEmpty()) {
            return null;
        }
        Operation operation;
        if (Objects.equals(filter, "undo")) {
            for (int i = operations.size() - 1; i >= 0; i--) {
                operation = operations.get(i);
                if (this.getSaveGameBoard()[operation.getRow()][operation.getCol()] != 0 &&
                        Objects.equals(operation.getOperationName(), "assigned")) {
                    return operation;
                }
            }
        }
        if (Objects.equals(filter, "redo")) {
            for (Operation value : operations) {
                operation = value;
                if (this.getSaveGameBoard()[operation.getRow()][operation.getCol()] == 0 &&
                        Objects.equals(operation.getOperationName(), "assigned")) {
                    return operation;
                }
            }
        }
        return null;
    }

    /**
     * Checks if a candidate number is valid for a given cell in the board.
     *
     * @param board the Sudoku board
     * @param row the row of the cell
     * @param col the column of the cell
     * @param candidateNumber the candidate number to check
     * @return true if the candidate number is valid, false otherwise
     */
    @Override
    public boolean isNumberValid(int[][] board, int row, int col, int candidateNumber) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == candidateNumber || board[i][col] == candidateNumber ||
                    board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == candidateNumber) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addOperationToList(String operationName, int row, int col, int value) {
        operations.add(new Operation(operationName, row, col, value));
    }
}
