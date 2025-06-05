package cz.cvut.fel.interfceRealization.CreateLevel;

import cz.cvut.fel.interfceRealization.CreateSudokuFiled.Cell;
import cz.cvut.fel.logic.Operation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.logic.Main.cells;

/**
 * The CreateLevelKillerChild class extends CreateLevelTemplate and provides a specific implementation
 * for the Killer Sudoku variant, where cells are removed based on predefined patterns.
 */
public class CreateLevelKillerChild extends CreateLevelTemplate {

    /**
     * Constructor for CreateLevelKillerChild.
     *
     * @param mode  the mode of the game
     * @param level the level of difficulty
     */
    public CreateLevelKillerChild(String mode, String level) {
        super(mode, level);
    }

    /**
     * Removes cells from the board based on predefined patterns.
     *
     * @param board the original Sudoku board
     * @param numToRemove the number of cells to remove
     * @return a new board with the specified number of cells removed based on patterns
     */
    @Override
    public int[][] removeCells(int[][] board, int numToRemove) {
        int[][] newBoard = getCopy(board);
        SecureRandom random = new SecureRandom();
        List<Cell> cellsWithPattern2 = new ArrayList<>();
        List<Cell> cellsWithPattern3 = new ArrayList<>();
        List<Cell> cellsWithPattern4 = new ArrayList<>();
        List<Cell> cellsWithPattern5 = new ArrayList<>();

        for (Cell c : cells) {
            switch (c.getPattern()) {
                case 2 -> cellsWithPattern2.add(c);
                case 3 -> cellsWithPattern3.add(c);
                case 4 -> cellsWithPattern4.add(c);
                case 5 -> cellsWithPattern5.add(c);
                default -> {}
            }
        }

        for (int counter = 0; counter < numToRemove; counter++) {
            if (counter <= 12) {
                if (!cellsWithPattern2.isEmpty()) {
                    int cellForRemove = random.nextInt(cellsWithPattern2.size());
                    newBoard[cellsWithPattern2.get(cellForRemove).getRow()][cellsWithPattern2.get(cellForRemove).getCol()] = 0;
                    cellsWithPattern2.remove(cellForRemove);
                }
            } else if (counter <= 24) {
                if (!cellsWithPattern3.isEmpty()) {
                    int cellForRemove = random.nextInt(cellsWithPattern3.size());
                    newBoard[cellsWithPattern3.get(cellForRemove).getRow()][cellsWithPattern3.get(cellForRemove).getCol()] = 0;
                    cellsWithPattern3.remove(cellForRemove);
                }
            } else if (counter <= 56) {
                if (!cellsWithPattern4.isEmpty()) {
                    int cellForRemove = random.nextInt(cellsWithPattern4.size());
                    newBoard[cellsWithPattern4.get(cellForRemove).getRow()][cellsWithPattern4.get(cellForRemove).getCol()] = 0;
                    cellsWithPattern4.remove(cellForRemove);
                }
            } else if (counter <= 81 && !cellsWithPattern5.isEmpty()) {
                    int cellForRemove = random.nextInt(cellsWithPattern5.size());
                    newBoard[cellsWithPattern5.get(cellForRemove).getRow()][cellsWithPattern5.get(cellForRemove).getCol()] = 0;
                    cellsWithPattern5.remove(cellForRemove);
                }

        }
        return newBoard;
    }

    public void setLevelGameBord(int[][] levelGameBord) {
        this.levelGameBoard = levelGameBord;
    }

    public void setSaveBord(int[][] levelBord) {
        this.lastSaveGameBoard = levelBord;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
