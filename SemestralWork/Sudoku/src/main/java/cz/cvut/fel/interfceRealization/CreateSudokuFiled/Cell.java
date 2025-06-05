package cz.cvut.fel.interfceRealization.CreateSudokuFiled;

/**
 * The Cell class represents a cell in a Sudoku grid. It contains information about its position
 * (row and column), its corresponding square in the Sudoku grid, and its pattern.
 */
public class Cell {
    private final int square;
    private final int pattern;
    private final int row;
    private final int col;

    /**
     * Constructor for creating a Cell with the specified row and column.
     * It also initializes the square and pattern based on the row and column.
     *
     * @param row the row index of the cell (0-8)
     * @param col the column index of the cell (0-8)
     */
    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.square = getSquare(row, col);
        this.pattern = getPattern(row, col);
    }

    public int getSquare() {
        return square;
    }

    public int getPattern() {
        return pattern;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Determines the square index based on the row and column.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the square index (1-9)
     */
    private int getSquare(int row, int col) {
        //first column
        if(row >= 0 && row <= 2 && col >= 0 && col <= 2){
            return 1;
        }
        if(row >= 3 && row <= 5 && col >= 0 && col <= 2){
            return 2;
        }
        if(row >= 6 && row <= 8 && col >= 0 && col <= 2){
            return 3;
        }
        //second column
        if(row >= 0 && row <= 2 && col >= 3 && col <= 5){
            return 4;
        }
        if(row >= 3 && row <= 5 && col >= 3 && col <= 5){
            return 5;
        }
        if(row >= 6 && row <= 8 && col >= 3 && col <= 5){
            return 6;
        }
        //third column
        if(row >= 0 && row <= 2 && col >= 5 && col <= 8){
            return 7;
        }
        if(row >= 3 && row <= 5 && col >= 5 && col <= 8){
            return 8;
        }
        if(row >= 5 && row <= 8 && col >= 5 && col <= 8){
            return 9;
        }
        return 0;
    }

    /**
     * Determines the pattern of the cell based on the row and column.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the pattern (an integer representing the pattern type)
     */
    public int getPattern(int row, int col) {
        int[][] filesPattern = new int[][]{
                {4, 4, 5, 4, 4, 2, 4, 4, 5},
                {4, 4, 5, 4, 4, 2, 4, 4, 5},
                {5, 5, 5, 3, 3, 3, 5, 5, 5},
                {4, 4, 3, 5, 5, 2, 3, 2, 2},
                {4, 4, 3, 2, 5, 2, 3, 4, 4},
                {2, 2, 3, 2, 5, 5, 3, 4, 4},
                {5, 5, 5, 3, 3, 3, 5, 5, 5},
                {4, 4, 5, 4, 4, 2, 4, 4, 5},
                {4, 4, 5, 4, 4, 2, 4, 4, 5}
        };
        return filesPattern[row][col];
    }
}
