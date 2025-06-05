package cz.cvut.fel.vizualization;

import cz.cvut.fel.interfceRealization.CreateSudokuFiled.SudokuFiledTemplate;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import static cz.cvut.fel.logic.Main.*;

/**
 * The Settings class represents the settings panel in a Sudoku game.
 * It provides buttons for continuing the game, restarting it, and returning to the main menu.
 * Additionally, it includes a button to toggle hints on and off.
 */
public class Settings {

    /** The SudokuField instance associated with these settings. */
    private final SudokuFiledTemplate sudokuFiledTemplate;

    /**
     * Constructs a Settings object with the specified SudokuField instance.
     *
     * @param sudokuFiledTemplate The SudokuField instance associated with these settings.
     */
    public Settings(SudokuFiledTemplate sudokuFiledTemplate){
        this.sudokuFiledTemplate = sudokuFiledTemplate;
    }

    private final Button continueButton = tools.createLongButton("Continue");
    private final Button restartButton = tools.createLongButton("Restart");
    private final Button mainMenuButton = tools.createLongButton("Main menu");

    private final Button hintsButton = tools.createLongButton("Click to " + getLogStatus() + " hints");

    /**
     * Sets up the event handlers for the buttons in the settings panel.
     * <p>
     * This method attaches the appropriate actions to each button:
     * <ul>
     *   <li>The continue button resumes the current Sudoku game.</li>
     *   <li>The restart button restarts the current Sudoku game.</li>
     *   <li>The main menu button navigates back to the main menu.</li>
     *   <li>The hints button toggles the display of hints on and off.</li>
     * </ul>
     * <p>
     * When the hints button is clicked, it toggles the value of the global `logs` variable and updates
     * the button text to reflect the current status ("Click to on hints" or "Click to off hints").
     * It also sets the `gameModeButtonsVisible` variable to false and updates the visibility of the game mode buttons
     * by calling the `updateVisibility()` method on the `SudokuFiledTemplate` instance.
     */
    private void events() {
        continueButton.setOnAction(event1 -> sudokuFiledTemplate.actionOnContinueButton());
        restartButton.setOnAction(event1 -> sudokuFiledTemplate.actionOnRestartButton());
        mainMenuButton.setOnAction(event1 -> sudokuFiledTemplate.actionOnMainMenuButton());
        hintsButton.setOnAction(event1 -> {
            if(logs == true){
                logs = false;
            } else
                logs = true;
            hintsButton.setText("Click to " + getLogStatus() + " hints");
            gameModeButtonsVisible = false;
            sudokuFiledTemplate.updateVisibility();
        });
    }

    /**
     * Retrieves the status of the hints button (on/off).
     *
     * @return The status of the hints button.
     */
    private String getLogStatus(){
        if(logs){
            return "off";
        }
        else
            return "on";
    }

    /**
     * Constructs and returns the settings panel as a VBox.
     *
     * @return The VBox containing the settings panel.
     */
    public VBox settingBox(){
        continueButton.setId("continueButton");
        restartButton.setId("restartButton");
        mainMenuButton.setId("mainMenuButton");
        hintsButton.setId("hintsButton");

        VBox settingBox = new VBox(10);
        settingBox.getChildren().addAll(continueButton, restartButton, mainMenuButton, hintsButton);
        settingBox.setSpacing(10);
        settingBox.setAlignment(Pos.CENTER);
        events();
        return  settingBox;
    }
}
