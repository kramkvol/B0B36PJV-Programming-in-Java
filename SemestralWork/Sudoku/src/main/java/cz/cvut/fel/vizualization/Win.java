package cz.cvut.fel.vizualization;

import cz.cvut.fel.interfceRealization.CreateSudokuFiled.SudokuFiledTemplate;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static cz.cvut.fel.logic.Main.tools;

/**
 * The Win class represents the win dialog displayed when a player completes a Sudoku level.
 * It includes options to proceed to the next level, restart the current level, or return to the main menu.
 */
public class Win {

    /** The SudokuField instance associated with this win dialog. */
    private SudokuFiledTemplate sudokuFiledTemplate;

    /**
     * Constructs a new Win instance with the specified SudokuField template.
     *
     * @param sudokuFiledTemplate the SudokuField template associated with this win dialog
     */
    public Win(SudokuFiledTemplate sudokuFiledTemplate)  {
        this.sudokuFiledTemplate = sudokuFiledTemplate;
    }
    public final Label  winLabel = tools.createLabel("Level complete!", 200, 30, true);
    public final Button restartButton = tools.createLongButton("Restart");
    public final Button mainMenuButton = tools.createLongButton("Main menu");
    public final Button nextLevelButton = tools.createLongButton("Next level");

    /**
     * Sets up event handlers for the buttons in the win dialog.
     * <p>
     * This method assigns actions to the win dialog buttons, defining their behavior when clicked:
     * <ul>
     *   <li><code>nextLevelButton</code>: Proceeds to the next level by calling {@link SudokuFiledTemplate#nextLevelButton()}.</li>
     *   <li><code>restartButton</code>: Restarts the current level by calling {@link SudokuFiledTemplate#actionOnRestartButton()}.</li>
     *   <li><code>mainMenuButton</code>: Returns to the main menu by calling {@link SudokuFiledTemplate#actionOnMainMenuButton()}.</li>
     * </ul>
     */
    private void events(){
        nextLevelButton.setOnAction(event1 -> sudokuFiledTemplate.nextLevelButton());
        restartButton.setOnAction(event1 -> sudokuFiledTemplate.actionOnRestartButton());
        mainMenuButton.setOnAction(event1 -> sudokuFiledTemplate.actionOnMainMenuButton());
    }

    /**
     * Creates and returns a VBox layout for the win dialog.
     * <p>
     * This method constructs a VBox containing the win label and the action buttons, sets their alignment and spacing,
     * and initializes the event handlers.
     *
     * @return the VBox layout for the win dialog
     */
    public VBox winBox(){
        VBox settingBox = new VBox(10);
        settingBox.getChildren().addAll(winLabel, nextLevelButton, restartButton, mainMenuButton);
        settingBox.setSpacing(10);
        settingBox.setAlignment(Pos.CENTER);
        events();

        nextLevelButton.setId("nextLevelButton");
        restartButton.setId("restartButton");
        mainMenuButton.setId("mainMenuButton");
        winLabel.setId("winLabel");
        return  settingBox;
    }
}
