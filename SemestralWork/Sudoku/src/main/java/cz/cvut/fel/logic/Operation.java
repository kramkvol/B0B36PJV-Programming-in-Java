package cz.cvut.fel.logic;

/**
 * This class represents an operation performed on a Sudoku cell.
 */
public class Operation {

    // Values for constructor
    private final String operationName;
    private final int row;
    private final int col;
    private final int value;



    /**
     * Constructs an Operation object with the specified operation details.
     *
     * @param operationName The name of the operation.
     * @param row           The row index of the Sudoku cell.
     * @param col           The column index of the Sudoku cell.
     * @param value         The value assigned to the Sudoku cell.
     */
    public Operation(String operationName, int row, int col, int value) {
        this.operationName = operationName;
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public String getOperationName() {
        return operationName;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int getValue() {
        return value;
    }
}
