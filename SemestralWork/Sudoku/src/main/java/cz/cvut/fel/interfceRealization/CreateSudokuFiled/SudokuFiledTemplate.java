package cz.cvut.fel.interfceRealization.CreateSudokuFiled;

import cz.cvut.fel.Interface.CreateModeInterface;
import cz.cvut.fel.Interface.CreateSudokuField;
import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelTemplate;
import cz.cvut.fel.logic.GameTimer;
import cz.cvut.fel.logic.Operation;
import cz.cvut.fel.vizualization.Settings;
import cz.cvut.fel.vizualization.Win;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static cz.cvut.fel.logic.Main.*;

/**
 * A template for creating a Sudoku field. This class handles the UI and interactions for the Sudoku game.
 *
 * @param <T> the type of CreateLevelTemplate
 */
public abstract class SudokuFiledTemplate<T extends CreateLevelTemplate> implements CreateSudokuField<T> {
    public T createLevelTemplate;
    public GridPane gridPane;
    public GameTimer gameTimer;

    // Constructor
    /**
     * Constructor for the SudokuFiledTemplate.
     *
     * @param createLevelTemplate the template for creating the level
     */
    protected SudokuFiledTemplate(T createLevelTemplate) {
        this.createLevelTemplate = createLevelTemplate;
        this.gridPane = showSudokuGrid(gridPane);
        gridPane.setId("gridPane");
    }
    // Tools
    protected Button undoButton = tools.createShortButton("Undo");
    protected Button redoButton = tools.createShortButton("Redo");
    protected Button manualButton = tools.createLongButton("Manual");
    public Button settingsButton = tools.createLongButton("Settings");
    protected Label levelInfoLabel;
    protected Label hintLabel;
    protected Label timerLabel = tools.createLabel("", 200, 60, true);
    protected Thread timerThread;

    /**
     * Configures event handlers for various buttons within the Sudoku game.
     * <p>
     * This method sets up actions for the settings, undo, and redo buttons.
     * <ul>
     *     <li>The settings button pauses the game timer, sets the timer in the current level template,
     *     displays the settings menu, updates the window title, hides game mode buttons, and updates their visibility.</li>
     *     <li>The undo button performs an undo action.</li>
     *     <li>The redo button performs a redo action.</li>
     * </ul>
     * </p>
     *
     * <p>Detailed actions:</p>
     * <ul>
     *     <li><b>Settings Button:</b>
     *         <ul>
     *             <li>Pauses the game timer using {@code gameTimer.pause()}.</li>
     *             <li>Sets the current timer value in the level template using {@code createLevelTemplate.setTimer(gameTimer.getElapsedTime())}.</li>
     *             <li>Creates a new {@code Settings} object and sets it in the center of the root layout using {@code currentRoot.setCenter(settings.settingBox())}.</li>
     *             <li>Updates the window title to "Settings" using {@code currentPrimaryStage.setTitle("Settings")}.</li>
     *             <li>Sets {@code gameModeButtonsVisible} to {@code false} and updates the visibility of game mode buttons using {@code updateVisibility()}.</li>
     *         </ul>
     *     </li>
     *     <li><b>Undo Button:</b>
     *         <ul>
     *             <li>Calls the {@code undoOnAction()} method to perform the undo action.</li>
     *         </ul>
     *     </li>
     *     <li><b>Redo Button:</b>
     *         <ul>
     *             <li>Calls the {@code redoOnAction()} method to perform the redo action.</li>
     *         </ul>
     *     </li>
     * </ul>
     */
    @Override
    public void events() {
        settingsButton.setOnAction(event -> settingsOnAction());
        undoButton.setOnAction(event -> undoOnAction());
        redoButton.setOnAction(event -> redoOnAction());
    }

    public void settingsOnAction(){
        gameTimer.pause();
        createLevelTemplate.setTimer(gameTimer.getElapsedTime());
        Settings settings = new Settings(this);
        currentRoot.setCenter(settings.settingBox());
        currentPrimaryStage.setTitle("Settings");
        gameModeButtonsVisible = false;
        updateVisibility();
    }

    /**
     * Updates the visibility of certain UI elements based on the current game state.
     * <p>
     * This method controls the visibility of the hint label and the undo and redo buttons.
     * </p>
     * <ul>
     *     <li>If hints are enabled (indicated by the {@code logs} variable), the hint label is made visible.</li>
     *     <li>The undo and redo buttons are shown or hidden based on the {@code gameModeButtonsVisible} flag.</li>
     * </ul>
     */
    @Override
    public void updateVisibility() {
        hintLabel.setVisible(logs);
        redoButton.setVisible(gameModeButtonsVisible);
        undoButton.setVisible(gameModeButtonsVisible);
    }

    /**
     * Updates the current level template and the associated grid pane.
     * <p>
     * This method sets the current {@code createLevelTemplate} to the provided {@code createLevelParent}
     * and updates the {@code gridPane} to reflect the new template.
     * </p>
     *
     * @param createLevelParent the new level template to be set
     */
    @Override
    public void update(T createLevelParent) {
        this.createLevelTemplate = createLevelParent;
        this.gridPane = getGridPane();
    }

    /**
     * Loads and displays the Sudoku field in the application's main window.
     * <p>
     * This method sets up the layout and style for the Sudoku field, initializes event handlers,
     * starts the game timer, and updates the visibility of UI elements. It also updates the level information label.
     * </p>
     * <ul>
     *     <li>Sets padding and background color for the root layout.</li>
     *     <li>Creates and sets the top, center, and bottom sections of the layout.</li>
     *     <li>Initializes event handlers by calling {@code events()}.</li>
     *     <li>Sets {@code gameModeButtonsVisible} to {@code true} and starts the game timer.</li>
     *     <li>Starts a new thread for the game timer and updates the visibility of UI elements.</li>
     *     <li>Updates the {@code levelInfoLabel} with the current game mode and level information.</li>
     * </ul>
     */
    @Override
    public void loadSudokuFiled() {
        currentRoot.setPadding(new Insets(20));
        currentRoot.setStyle("-fx-background-color: #FFB366;");
        currentRoot.setTop(createTopBoxForSudokuField());
        currentRoot.setCenter(createCentralBoxForSudokuField());
        currentRoot.setBottom(createBottomBoxForSudokuField());
        currentPrimaryStage.setTitle("Sudoku Field");
        events();
        gameModeButtonsVisible = true;
        startGameTimer();
        timerThread = new Thread(gameTimer);
        timerThread.start();
        updateVisibility();
        levelInfoLabel.setText(createLevelTemplate.getMode() + "\nLevel: " + createLevelTemplate.getLevel());

        undoButton.setId("undoButton");
        redoButton.setId("redoButton");
        hintLabel.setId("hintLabel");
        levelInfoLabel.setId("levelInfoLabel");
        timerLabel.setId("timerLabel");
    }

    /**
     * Updates the visibility of certain UI elements based on the current game state.
     * <p>
     * This method controls the visibility of the hint label and the undo and redo buttons.
     * </p>
     * <ul>
     *     <li>If hints are enabled (indicated by the {@code logs} variable), the hint label is made visible.</li>
     *     <li>The undo and redo buttons are shown or hidden based on the {@code gameModeButtonsVisible} flag.</li>
     * </ul>
     */
    @Override
    public void updateGridWithSavedState() {
        int[][] board = createLevelTemplate.getLevelGameBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board[row][col];
                for (Node node : gridPane.getChildren()) {
                    if (node instanceof TextField) {
                        Integer rowIndex = GridPane.getRowIndex(node);
                        Integer colIndex = GridPane.getColumnIndex(node);
                        if (rowIndex != null && colIndex != null && rowIndex == row && colIndex == col) {
                            ((TextField) node).setText(value == 0 ? "" : String.valueOf(value));
                        }
                    }
                }
            }
        }
    }

    /**
     * Starts the game timer and initializes it with the elapsed time from the current level template.
     * <p>
     * This method sets up a listener for the game timer, which updates the timer label with the elapsed time.
     * It also initializes the game timer with the saved elapsed time from the current level template.
     * </p>
     */
    @Override
    public void startGameTimer() {
        GameTimer.TimerListener listener = time -> Platform.runLater(() -> timerLabel.setText("Time\n" + time));
        gameTimer = new GameTimer(listener);
        gameTimer.setElapsedTime(createLevelTemplate.getSaveTimer());
    }

    /**
     * Creates the top box for the Sudoku field UI.
     *
     * @return the top VBox
     */
    @Override
    public VBox createTopBoxForSudokuField() {
        if (manualButton == null) {
            manualButton = tools.createLongButton("Manual");
        }
        if (settingsButton == null) {
            settingsButton = tools.createLongButton("Settings");
        }
        if (levelInfoLabel == null) {
            levelInfoLabel = tools.createLabel("", 200, 60, true);
        }
        if (timerLabel == null) {
            timerLabel = tools.createLabel("", 200, 60, true);
        }
        if (hintLabel == null) {
            hintLabel = tools.createLabel("Move the cursor over the cell to see the hint.", 420, 30, logs);
        }

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(manualButton, settingsButton);
        hBox1.setSpacing(20);
        hBox1.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(levelInfoLabel, timerLabel);
        hBox2.setSpacing(20);
        hBox2.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox1, hBox2, hintLabel);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    /**
     * Creates the central box for the Sudoku field UI.
     *
     * @return the central VBox
     */
    @Override
    public VBox createCentralBoxForSudokuField() {
        gridPane = getGridPane();
        HBox hBox = new HBox(gridPane);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    /**
     * Shows the Sudoku grid.
     *
     * @return the GridPane with the Sudoku grid
     */
    @Override
    public GridPane showSudokuGrid(GridPane gridPane) {
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = createSudokuCell(row, col);
                gridPane.add(cell, col, row);
            }
        }

        return gridPane;
    }

    /**
     * Creates the bottom box for the Sudoku field UI.
     *
     * @return the bottom VBox
     */
    @Override
    public VBox createBottomBoxForSudokuField() {
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(undoButton, redoButton);
        hBox1.setSpacing(20);
        hBox1.setAlignment(Pos.CENTER);

        VBox bottomBox = new VBox(hBox1);
        bottomBox.setAlignment(Pos.CENTER);
        return bottomBox;
    }

    // All for creating Cells
    /**
     * Shows the Sudoku grid.
     *
     * @return the GridPane with the Sudoku grid
     */
    @Override
    public GridPane getGridPane() {
        return gridPane;
    }

    /**
     * Handles changes to the text in a cell.
     *
     * @param cell the TextField representing the cell
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param newValue the new value of the cell
     */
    @Override
    public void handleCellTextChange(TextField cell, int row, int col, String newValue) {
        try {
            if (canParseInt(newValue)) {
                validateAndAssignValue(cell, row, col, Integer.parseInt(newValue));
            } else if (newValue.isEmpty()) {
                setEmptyValue(cell, row, col);
            } else {
                throw new IllegalArgumentException("This is not a number");
            }
        } catch (IllegalArgumentException exception) {
            setException(cell, row, col, exception);
            setEmptyValue(cell, row, col);
        }
        if (!containsZero(createLevelTemplate.getSaveGameBoard())) {
            levelCompleted();
        }
    }


    /**
     * Sets the assigned value to the cell.
     *
     * @param cell the TextField representing the cell
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param value the value to assign
     * @param str the string to display in the hint label
     */
    @Override
    public void setAssignedValue(TextField cell, int row, int col, int value, String str) {
        createLevelTemplate.setValueToBoard(createLevelTemplate.getSaveGameBoard(), value, row, col);
        createLevelTemplate.addOperationToList("assigned", row, col, value);

        cell.setText(String.valueOf(value));
        logger.info("{} assigned", str);
        hintLabel.setText(str + "assigned");
    }

    /**
     * Sets an empty value to the cell.
     *
     * @param cell the TextField representing the cell
     * @param row the row index of the cell
     * @param col the column index of the cell
     */
    @Override
    public void setEmptyValue(TextField cell, int row, int col) {
        createLevelTemplate.setTimer(gameTimer.getElapsedTime());
        createLevelTemplate.setValueToBoard(createLevelTemplate.getSaveGameBoard(), 0, row, col);
        createLevelTemplate.addOperationToList("assigned", row, col, 0);
        cell.setText("");
    }

    /**
     * Sets an exception message for the cell.
     *
     * @param cell the TextField representing the cell
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param exception the exception to set
     */
    @Override
    public void setException(TextField cell, int row, int col, IllegalArgumentException exception) {
        logger.error(exception.getMessage());
        if (hintLabel == null) {
            hintLabel = tools.createLabel(exception.getMessage(), 420, 30, logs);
        }
        else {
            hintLabel.setText(exception.getMessage());
        }
    }

    /**
     * Checks if the given string can be parsed to an integer.
     *
     * @param strValue the string to check
     * @return true if the string can be parsed to an integer, false otherwise
     */
    @Override
    public boolean canParseInt(String strValue) {
        try {
            Integer.parseInt(strValue);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the given array contains a zero.
     *
     * @param array the array to check
     * @return true if the array contains a zero, false otherwise
     */
    @Override
    public boolean containsZero(int[][] array) {
        for (int[] row : array) {
            for (int value : row) {
                if (value == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Completes the current level.
     */
    @Override
    public void levelCompleted() {
        gameTimer.pause();
        getCurrentLevelMode().createOrGetLevel(String.valueOf(Integer.parseInt(createLevelTemplate.getLevel()) + 1));
        currentPrimaryStage.setTitle("Level complete");
        Win win = new Win(this);
        win.winBox();
        createLevelTemplate.setForDeleteLastGame();
        gameModeButtonsVisible = false;
        updateVisibility();
        hintLabel.setVisible(false);
        currentRoot.setCenter(win.winBox());
    }


    /**
     * Checks if the given number can be set in the specified row.
     *
     * @param row the row index
     * @param num the number to check
     * @return true if the number can be set, false otherwise
     */
    @Override
    public boolean checkRowToSetNumber(int row, int num) {
        for (int i = 0; i < 9; i++) {
            if (createLevelTemplate.getLevelGameBoard()[row][i] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given number can be set in the specified column.
     *
     * @param col the column index
     * @param num the number to check
     * @return true if the number can be set, false otherwise
     */
    @Override
    public boolean checkColToSetNumber(int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (createLevelTemplate.getLevelGameBoard()[i][col] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given number can be set in the specified 3x3 square.
     *
     * @param row the row index
     * @param col the column index
     * @param num the number to check
     * @return true if the number can be set, false otherwise
     */
    @Override
    public boolean checkSquareToSetNumber(int row, int col, int num) {
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (createLevelTemplate.getSaveGameBoard()[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Action for the continue button.
     */
    @Override
    public void actionOnContinueButton() {
        gameTimer.resume();
        currentRoot.setCenter(createCentralBoxForSudokuField());
        currentPrimaryStage.setTitle("Sudoku Field");
        gameModeButtonsVisible = true;
        updateVisibility();
    }

    /**
     * Handles the action for the Main Menu button, saving the elapsed game time,
     * stopping the game timer, and returning to the main menu.
     */
    @Override
    public void actionOnMainMenuButton() {
        createLevelTemplate.setTimer(gameTimer.getElapsedTime());
        gameTimer.stop();
        mainMenu.mainMenuStart();
    }

    /**
     * Returns the current level mode based on the mode of the current level template.
     *
     * @return the current level mode.
     */
    @Override
    public CreateModeInterface<? extends CreateLevelTemplate> getCurrentLevelMode() {
        if ("Sudoku Classic".equals(createLevelTemplate.getMode())) {
            return levelsInModeClassic;
        } else {
            return levelsInModeKiller;
        }
    }

    /**
     * Handles the action for the Restart button, resetting the game timer,
     * clearing the last game state, updating the GUI with the initial state,
     * and resetting the stage title.
     */
    @Override
    public void actionOnRestartButton() {
        gameTimer.reset();
        createLevelTemplate.setForDeleteLastGame();
        currentRoot.setCenter(createCentralBoxForSudokuField());
        updateGridWithSavedState();
        currentPrimaryStage.setTitle("Sudoku Field");
        gameModeButtonsVisible = true;
        updateVisibility();
    }

    /**
     * Creates a TextField for a Sudoku cell, setting its size, style, initial value,
     * and event handlers for user interactions.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the created TextField for the Sudoku cell.
     */
    @Override
    public TextField createSudokuCell(int row, int col) {
        TextField cell = new TextField();
        cell.setPrefSize(40, 40);
        cell.setStyle("-fx-font-size: 20; -fx-alignment: center;");
        cell.setOnMouseClicked(event -> hintLabel.setText("Valid values: " + getValidNumbersForCell(row, col)));

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
     * Gets the valid numbers for a specific cell based on Sudoku rules.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return a string containing valid numbers for the cell.
     */
    @Override
    public String getValidNumbersForCell(int row, int col) {
        StringBuilder str = new StringBuilder();
        for (int num = 1; num <= 9; num++) {
            if (checkRowToSetNumber(row, num) && checkColToSetNumber(col, num) && checkSquareToSetNumber(row, col, num)) {
                str.append(num).append(" ");
            }
        }
        return str.toString();
    }

    /**
     * Validates and assigns a value to a Sudoku cell, checking if the value is between 1 and 9,
     * and if it complies with Sudoku rules (row, column, and square constraints).
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
        } else {
            setAssignedValue(cell, row, col, value, str);
        }
    }

    /**
     * Handles the Undo action, reverting the last change made by the user.
     */
    public void undoOnAction() {
        Operation currentOperation = createLevelTemplate.findLastOperationFor( "undo");
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);
                try {
                    if (rowIndex != null && colIndex != null && rowIndex == currentOperation.getRow() && colIndex == currentOperation.getCol()) {
                        ((TextField) node).setText("");
                        createLevelTemplate.setValueToBoard(createLevelTemplate.getSaveGameBoard(), 0, currentOperation.getRow(), currentOperation.getCol());
                    }
                } catch (NullPointerException exception) {
                    logger.error(exception.getMessage());
                    break;
                }
            }
        }
    }

    /**
     * Handles the Redo action, reapplying the last undone change made by the user.
     */
    @Override
    public void redoOnAction() {
        Operation currentOperation = createLevelTemplate.findLastOperationFor("redo");
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);
                try {
                    if (rowIndex != null && colIndex != null && rowIndex == currentOperation.getRow() && colIndex == currentOperation.getCol()) {
                        ((TextField) node).setText(String.valueOf(currentOperation.getValue()));
                        createLevelTemplate.setValueToBoard(createLevelTemplate.getSaveGameBoard(), currentOperation.getValue(), currentOperation.getRow(), currentOperation.getCol());
                    }
                } catch (NullPointerException exception) {
                    logger.error(exception.getMessage());
                    break;
                }
            }
        }
    }

}
