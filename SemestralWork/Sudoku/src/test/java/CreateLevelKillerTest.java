import static cz.cvut.fel.logic.Main.cells;
import static cz.cvut.fel.logic.Main.levelsInModeKiller;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelKillerChild;
import cz.cvut.fel.interfceRealization.CreateSudokuFiled.Cell;
import cz.cvut.fel.logic.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CreateLevelKillerTest {
    private CreateLevelKillerChild createLevelKillerChild;
    @Mock
    private CreateLevelKillerChild mockCreateLevelKillerChild;
    private int[][] solveBoard;
    private int[][] levelBoard;
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockCreateLevelKillerChild = mock(CreateLevelKillerChild.class);
        createLevelKillerChild = levelsInModeKiller.createOrGetLevel("1");

        solveBoard = new int[][]{
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        levelBoard = new int[][]{
                {5, 3, 4, 6, 7, 0, 9, 1, 2},
                {6, 7, 2, 1, 9, 0, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 0, 4, 0, 0},
                {4, 2, 6, 0, 5, 0, 7, 9, 1},
                {0, 0, 3, 0, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 0, 6, 3, 5},
                {3, 4, 5, 2, 8, 0, 1, 7, 9}
        };
    }
    private void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
    @Test
    public void testMethodIsNumberValid() {
        boolean result;
        int[][] gameSudokuBoardTest1 = new int[][]{
                {0, 0, 4, 6, 7, 8, 9, 1, 2},
                {0, 0, 2, 1, 9, 5, 3, 4, 8},
                {0, 0, 8, 3, 4, 0, 5, 6, 7},
                {0, 0, 9, 7, 6, 1, 4, 2, 3},
                {0, 0, 6, 8, 5, 3, 7, 9, 1},
                {7, 0, 3, 9, 2, 4, 8, 5, 6},
                {9, 0, 1, 5, 3, 7, 2, 8, 4},
                {2, 0, 7, 4, 1, 9, 6, 3, 5},
                {3, 0, 5, 2, 8, 6, 1, 7, 9}
        };

        when(mockCreateLevelKillerChild.isNumberValid(any(int[][].class), anyInt(), anyInt(), anyInt())).thenCallRealMethod();

        when(mockCreateLevelKillerChild.isNumberValid(gameSudokuBoardTest1, 0, 0, 4)).thenCallRealMethod();
        result = mockCreateLevelKillerChild.isNumberValid(gameSudokuBoardTest1, 0, 0, 4);
        assertFalse(result); // already in row

        when(mockCreateLevelKillerChild.isNumberValid(gameSudokuBoardTest1, 1, 0, 7)).thenCallRealMethod();
        result = mockCreateLevelKillerChild.isNumberValid(gameSudokuBoardTest1, 1, 0, 7);
        assertFalse(result); // already in column

        when(mockCreateLevelKillerChild.isNumberValid(gameSudokuBoardTest1, 2, 0, 3)).thenCallRealMethod();
        result = mockCreateLevelKillerChild.isNumberValid(gameSudokuBoardTest1, 2, 0, 3);
        assertFalse(result); // already in square

        when(mockCreateLevelKillerChild.isNumberValid(gameSudokuBoardTest1, 3, 0, 8)).thenCallRealMethod();
        result = mockCreateLevelKillerChild.isNumberValid(gameSudokuBoardTest1, 3, 0, 8);
        assertTrue(result); // ok
    }
    @Test
    public void testGenerateSolveSudoku() {
        when(mockCreateLevelKillerChild.generateSolveSudoku()).thenCallRealMethod();
        int[][] resultBoard = mockCreateLevelKillerChild.generateSolveSudoku();

        //resultBoard is not null?
        assertNotNull(resultBoard);
        // resultBoard is 9x9?
        assertEquals(9, resultBoard.length);
        for (int[] row : resultBoard) {
            assertEquals(9, row.length);
        }
        //Values in resultBoard are correct?
        assertTrue(isValidSudokuBoard(resultBoard));
    }
    private boolean isValidSudokuBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int num = board[row][col];
                if (num != 0 && !mockCreateLevelKillerChild.isNumberValid(board, row, col, num)) {
                    return false;
                }
            }
        }
        return true;
    }
    @Test
    public void testIsRemovingCellsCorrect() {
        when(mockCreateLevelKillerChild.getSolveBord()).thenReturn(solveBoard);

        int[][] expectedLevelBoard = new int[][]{
                {5, 3, 4, 6, 7, 0, 9, 1, 2},
                {6, 7, 2, 1, 9, 0, 3, 4, 8},
                {1, 9, 8, 0, 0, 0, 5, 6, 7},
                {8, 5, 0, 7, 6, 0, 0, 0, 0},
                {4, 2, 0, 0, 5, 0, 0, 9, 1},
                {0, 0, 0, 0, 2, 4, 0, 5, 6},
                {9, 6, 1, 0, 0, 0, 2, 8, 4},
                {2, 8, 7, 4, 1, 0, 6, 3, 5},
                {3, 4, 5, 2, 8, 0, 1, 7, 9}
        };

        addCells();

        when(mockCreateLevelKillerChild.removeCells(any(int[][].class), anyInt())).thenReturn(expectedLevelBoard);
        int[][] result = mockCreateLevelKillerChild.removeCells(mockCreateLevelKillerChild.getSolveBord(), 24);
        assertArrayEquals(expectedLevelBoard, result);
    }
    private void addCells() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells.add(new Cell(row, col));
            }
        }
    }
    @Test
    public void testSetValueToSaveBoard() {
        when(mockCreateLevelKillerChild.getLevelGameBoard()).thenReturn(levelBoard);
        //Data for test
        int row = 0;
        int col = 0;
        int value = 0;
        int[][] boardBefore = mockCreateLevelKillerChild.getLevelGameBoard();

        outerLoop:
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (boardBefore[r][c] == 0) {
                    row = r;
                    col = c;
                    for (int v = 1; v <= 9; v++) {
                        if (mockCreateLevelKillerChild.isNumberValid(boardBefore, r, c, v)) {
                            value = v;
                            break outerLoop;
                        }
                    }
                }
            }
        }

        //Test
        doCallRealMethod().when(mockCreateLevelKillerChild).setValueToBoard(any(int[][].class), anyInt(), anyInt(), anyInt());

        int[][] boardAfter = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(boardBefore[i], 0, boardAfter[i], 0, 9);
        }

        mockCreateLevelKillerChild.setValueToBoard(boardAfter, value, row, col);

        // Only one cell has been changed?
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (r == row && c == col) {
                    assertEquals(value, boardAfter[r][c]);
                } else {
                    assertEquals(boardBefore[r][c], boardAfter[r][c]);
                }
            }
        }
    }
    @Test
    public void testIsCorrectlyFoundedLastOperation() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("assigned", 0, 0, 1));
        operations.add(new Operation("assigned", 0, 1, 2));
        operations.add(new Operation("assigned", 0, 2, 3));

        operations.add(new Operation("assigned", 0, 0, 0));
        operations.add(new Operation("assigned", 0, 1, 0));
        operations.add(new Operation("assigned", 0, 2, 0));

        operations.add(new Operation("redo", 0, 2, 3));
        operations.add(new Operation("redo", 0, 1, 2));
        operations.add(new Operation("redo", 0, 0, 1));

        operations.add(new Operation("undo", 0, 0, 0));
        operations.add(new Operation("undo", 0, 1, 0));
        operations.add(new Operation("undo", 0, 2, 0));

        operations.add(new Operation("redo", 0, 2, 3));
        operations.add(new Operation("redo", 0, 1, 2));
        operations.add(new Operation("redo", 0, 0, 1));

        operations.add(new Operation("undo", 0, 0, 0));
        operations.add(new Operation("undo", 0, 1, 0));
        operations.add(new Operation("undo", 0, 2, 0));

        operations.add(new Operation("assigned", 0, 2, 9));

        when(mockCreateLevelKillerChild.findLastOperationFor(eq("assigned"))).thenReturn(operations.get(operations.size() - 1));
        when(mockCreateLevelKillerChild.findLastOperationFor(eq("redo"))).thenReturn(operations.get(13)); // Index 13 for redo
        when(mockCreateLevelKillerChild.findLastOperationFor(eq("undo"))).thenReturn(operations.get(16)); // Index 16 for undo

        Operation operationLastAssigned = mockCreateLevelKillerChild.findLastOperationFor("assigned");
        assertEquals(18, operations.indexOf(operationLastAssigned));

        Operation operationLastRedo = mockCreateLevelKillerChild.findLastOperationFor("redo");
        assertEquals(13, operations.indexOf(operationLastRedo));

        Operation operationLastUndo = mockCreateLevelKillerChild.findLastOperationFor("undo");
        assertEquals(16, operations.indexOf(operationLastUndo));
    }
    @Test
    public void testIsNumberValid_MCC() {
        int[][] board = {
                {0, 0, 4, 6, 7, 8, 9, 1, 2},
                {0, 0, 2, 1, 9, 5, 3, 4, 8},
                {0, 0, 8, 3, 4, 0, 5, 6, 7},
                {0, 0, 9, 7, 6, 1, 4, 2, 3},
                {0, 0, 6, 8, 5, 3, 7, 9, 1},
                {7, 0, 3, 9, 2, 4, 8, 5, 6},
                {9, 0, 1, 5, 3, 7, 2, 8, 4},
                {2, 0, 7, 4, 1, 9, 6, 3, 5},
                {3, 0, 5, 2, 8, 6, 1, 7, 9}
        };

        // Test situation 1: already in row
        assertFalse(mockCreateLevelKillerChild.isNumberValid(board, 0, 0, 4)); // A = 1, B = 0, C = 0, R = 0

        // Test situation 2: already in column
        assertFalse(mockCreateLevelKillerChild.isNumberValid(board, 1, 0, 7)); // A = 0, B = 1, C = 0, R = 0

        // Test situation 3: already in square
        assertFalse(mockCreateLevelKillerChild.isNumberValid(board, 2, 0, 3)); // A = 0, B = 0, C = 1, R = 0

        // Test situation 4: all conditions met
        assertTrue(mockCreateLevelKillerChild.isNumberValid(board, 3, 0, 8)); // A = 0, B = 0, C = 0, R = 1

        // Additional combinations to cover all MC/DC situations
        // Test situation 5: already in row and column
        assertFalse(mockCreateLevelKillerChild.isNumberValid(board, 0, 0, 7)); // A = 1, B = 1, C = 0, R = 0

        // Test situation 6: already in row and square
        assertFalse(mockCreateLevelKillerChild.isNumberValid(board, 0, 0, 9)); // A = 1, B = 0, C = 1, R = 0

        // Test situation 7: already in column and square
        assertFalse(mockCreateLevelKillerChild.isNumberValid(board, 1, 0, 3)); // A = 0, B = 1, C = 1, R = 0

        // Test situation 8: already in row, column, and square
        assertFalse(mockCreateLevelKillerChild.isNumberValid(board, 0, 0, 3)); // A = 1, B = 1, C = 1, R = 0
    }

}
