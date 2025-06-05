package cz.cvut.fel.interfceRealization.CreateSudokuFiled;

import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelKillerChild;
import javafx.scene.control.TextField;

import static cz.cvut.fel.logic.Main.*;

/**
 * A class representing the creation of Sudoku fields in Killer mode.
 */
public class CreateFiledKillerChild extends SudokuFiledTemplate<CreateLevelKillerChild> {

    private CreateLevelKillerChild createLevelKillerChild;

    /**
     * Constructs a CreateFiledKillerChild with the specified level.
     *
     * @param createFiledKillerChild the level template for Killer Sudoku.
     */
    public CreateFiledKillerChild(CreateLevelKillerChild createFiledKillerChild) {
        super(createFiledKillerChild);
        this.createLevelKillerChild = levelsInModeKiller.createOrGetLevel(createFiledKillerChild.getLevel());
    }

    /**
     * Proceeds to the next level in Killer Sudoku mode and loads the corresponding Sudoku field.
     */
    @Override
    public void nextLevelButton() {
        createFiledKillerChild = new CreateFiledKillerChild(levelsInModeKiller.createOrGetLevel(String.valueOf(Integer.parseInt(createLevelKillerChild.getLevel()) + 1)));
        createFiledKillerChild.loadSudokuFiled();
    }

    /**
     * Colors the cell based on its pattern and updates its style.
     *
     * @param cell the TextField representing the Sudoku cell.
     * @param row  the row index of the cell.
     * @param col  the column index of the cell.
     */
    public void colorCell(TextField cell, int row, int col){
        String color2 = "-fx-background-color: #FFFFFF;";
        String color3 = "-fx-background-color: #8386bb;";
        String color4 = "-fx-background-color: #72a6a6;";
        String color5 = "-fx-background-color: #99af76;";

        String cellStyle = "-fx-font-size: 20px; -fx-align000000ment: center; -fx-text-fill: black;";
        for(Cell c : cells){
            if(c.getRow() == row && c.getCol() == col){
                if (c.getPattern() == 2) {
                    cell.setStyle(color2 + cellStyle);
                } else if (c.getPattern() == 3) {
                    cell.setStyle(color3 + cellStyle);
                } else if (c.getPattern() == 4) {
                    cell.setStyle(color4 + cellStyle);
                } else if (c.getPattern() == 5) {
                    cell.setStyle(color5 + cellStyle);
                }
                break;
            }
        }
    }

    /**
     * Gets the cell object for the specified row and column.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the cell object, or null if no cell matches the row and column.
     */
    private Cell getCell(int row, int col){
        for(Cell cell: cells){
            if(cell.getRow() == row && cell.getCol() == col){
                return cell;
            }
        }
        return null;
    }

    /**
     * Calculates the sum of values in a specific zone defined by the pattern and square.
     *
     * @param board   the Sudoku board.
     * @param pattern the pattern of the zone.
     * @param square  the square of the zone.
     * @return the sum of values in the zone.
     */
    public int getSumInZone(int[][] board, int pattern, int square){
        int sum = 0;
        for (int r = 0; r < 9; r++){
            for(int c = 0; c< 9; c++){
                if(getCell(r, c).getPattern() == pattern && getCell(r, c).getSquare() == square){
                    sum += board[r][c];
                }
            }
        }
        return sum;
    }

    /**
     * Calculates the sum of values in the zone for the specified cell.
     *
     * @param board the Sudoku board.
     * @param row   the row index of the cell.
     * @param col   the column index of the cell.
     * @return the sum of values in the zone.
     */
    private int sum(int[][] board, int row, int col) {
        Cell cell = getCell(row, col);
        for (int r = 0; r < 9; r++){
            for(int c = 0; c< 9; c++){
                if(r == cell.getRow() && c == cell.getCol()){
                    return getSumInZone(board, cell.getPattern(), cell.getSquare());
                }
            }
        }
        return 0;
    }

    /**
     * Creates a TextField for a Sudoku cell, setting its size, color, initial value, and event handlers.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the created TextField for the Sudoku cell.
     */
    @Override
    public TextField createSudokuCell(int row, int col) {
        TextField cell = new TextField();
        cell.setPrefSize(40, 40);
        colorCell(cell, row, col);
        cell.setOnMouseClicked(event -> hintLabel.setText("Sum in this zone: " + sum(createLevelTemplate.getSolveBord(), row, col) + ", values: " + getValidNumbersForCell(row, col)));

        int savedValue = createLevelTemplate.getSaveGameBoard()[row][col];
        int levelValue = createLevelTemplate.getLevelGameBoard()[row][col];
        if (levelValue != 0 && savedValue != 0) {
            cell.setText(String.valueOf(savedValue));
            cell.setDisable(true);
        } else {
            if (levelValue == 0 && savedValue == 0) {
                cell.setText("");
                cell.setDisable(false);
            }
            if (levelValue == 0 && savedValue != 0) {
                cell.setText(String.valueOf(savedValue));
                cell.setDisable(false);
            }
            cell.textProperty().addListener((observable, oldValue, newValue) -> handleCellTextChange(cell, row, col, newValue));
        }
        return cell;
    }

    /**
     * Validates and assigns a value to a Sudoku cell, ensuring the value is within range and conforms to Sudoku rules.
     *
     * @param cell  the TextField of the cell.
     * @param row   the row index of the cell.
     * @param col   the column index of the cell.
     * @param value the value to be assigned to the cell.
     */
    @Override
    public void validateAndAssignValue(TextField cell, int row, int col, int value) {
        final String str = "The number (" + value + ") is ";
        if (!(value >= 1 && value <= 9)) {
            throw new IllegalArgumentException(str + "not between 1 and 9.");
        } else if (!checkColToSetNumber(col, value)) {
            throw new IllegalArgumentException(str + "already in the column.");
        } else if (!checkRowToSetNumber(row, value)) {
            throw new IllegalArgumentException(str + "already in the row.");
        } else if (!checkSquareToSetNumber(row, col, value)) {
            throw new IllegalArgumentException(str + "already in the square.");
        } else if (!checkSunToSetNumber(row, col, value)) {
            throw new IllegalArgumentException("Sum will be " + sum(createLevelTemplate.getSolveBord(), row, col));
        } else {
            setAssignedValue(cell, row, col, value, str);
        }
    }

    /**
     * Checks if setting a number to a cell maintains the correct sum for the zone.
     *
     * @param row   the row index of the cell.
     * @param col   the column index of the cell.
     * @param value the value to be checked.
     * @return true if the sum is maintained, false otherwise.
     */
    private boolean checkSunToSetNumber(int row, int col, int value) {
        int correctSum = sum(createLevelTemplate.getSolveBord(), row, col);
        int correctValueInSolveBoard = createLevelTemplate.getValueFromSolveBord(row, col);
        createLevelTemplate.setValueToSloveBoard(value, row, col);
        int currentSum = sum(createLevelTemplate.getSolveBord(), row, col);
        createLevelTemplate.setValueToSloveBoard(correctValueInSolveBoard, row, col);
        if(correctSum != currentSum){
            return false;
        }
        else
            return true;
    }
}
