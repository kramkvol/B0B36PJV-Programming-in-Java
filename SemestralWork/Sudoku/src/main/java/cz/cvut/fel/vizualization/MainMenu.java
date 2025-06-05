package cz.cvut.fel.vizualization;

import cz.cvut.fel.interfceRealization.CreateSudokuFiled.CreateFiledClassicChild;
import cz.cvut.fel.interfceRealization.CreateSudokuFiled.CreateFiledKillerChild;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.security.InvalidParameterException;

import static cz.cvut.fel.logic.Main.*;

/**
 * The MainMenu class represents the main menu of the Sudoku application.
 * It provides functionality to navigate through different game modes and levels.
 */
public class MainMenu {
    private final Button classicSudokuButton = tools.createLongButton("Sudoku Classic");
    private final Button killerSudokuButton = tools.createLongButton("Sudoku Killer");
    private final Button playButton = tools.createButton("Play", false, 150, 30);
    private final Button backButton = tools.createShortButton("Back");
    private final Button nextButton = tools.createShortButton("Next");
    private final Button manualButton = tools.createLongButton("Manual");
    private final Button[] levelButtons = new Button[25];
    private Button currentMode = classicSudokuButton;
    private Button selectedLevelButton;
    private int currentPage = 1;

    private void events(){
        backButton.setOnAction(event -> actionOnBackButton());
        nextButton.setOnAction(event -> actionOnNextButton());
        killerSudokuButton.setOnAction(event -> actionOnKillerSudokuButton());
        classicSudokuButton.setOnAction(event -> actionOnClassicSudokuButton());
        playButton.setOnAction(event -> actionOnPlayButton());

        for (Button levelButton : levelButtons) {
            levelButton.setOnAction(event -> actionOnLevelButton(levelButton));
        }
    }

    /**
     * Initializes and starts the main menu.
     */
    public void mainMenuStart(){
        classicSudokuButton.setId("classicSudokuButton");
        killerSudokuButton.setId("killerSudokuButton");
        playButton.setId("playButton");
        backButton.setId("backButton");
        nextButton.setId("nextButton");

        loadMainMenu();
        updateButtons();
        events();
        tools.selectedButton(currentMode);
        showPassedLevelsOnPage();
    }

    private void loadMainMenu(){
        currentRoot.setTop(createTopBox());
        currentRoot.setCenter(createCentralBox());
        currentRoot.setBottom(createBottomBox());
        currentPrimaryStage.setTitle("Main menu");
    }

    /**
     * Creates the top box of the main menu.
     *
     * @return The VBox representing the top box.
     */
    private VBox createTopBox() {
        HBox buttonBoxManual = new HBox();
        buttonBoxManual.getChildren().addAll(manualButton);
        buttonBoxManual.setAlignment(Pos.CENTER);

        HBox buttonBoxClassicAndKiller = new HBox();
        buttonBoxClassicAndKiller.getChildren().addAll(classicSudokuButton, killerSudokuButton);
        buttonBoxClassicAndKiller.setAlignment(Pos.CENTER);
        buttonBoxClassicAndKiller.setSpacing(10);

        VBox buttonBoxTop = new VBox();
        buttonBoxTop.getChildren().addAll(buttonBoxManual, buttonBoxClassicAndKiller);
        buttonBoxTop.setAlignment(Pos.CENTER);
        buttonBoxTop.setSpacing(20);

        return buttonBoxTop;
    }

    /**
     * Creates a box containing a range of level buttons.
     *
     * @param buttons The array of level buttons.
     * @param start   The start index of the range.
     * @param end     The end index of the range.
     * @return The HBox containing the specified range of level buttons.
     */
    private HBox createLevelBox(Button[] buttons, int start, int end) {
        HBox levelBox = new HBox();
        for (int i = start - 1; i < end && i < buttons.length; i++) {
            if (buttons[i] != null) {
                levelBox.getChildren().add(buttons[i]);
            }
        }
        levelBox.setSpacing(40);
        levelBox.setAlignment(Pos.CENTER);
        return levelBox;
    }

    /**
     * Creates the central box of the main menu containing level buttons.
     *
     * @return The VBox representing the central box.
     */
    private VBox createCentralBox() {
        for (int i = 0; i < levelButtons.length; i++) {
            levelButtons[i] = tools.createButton(String.valueOf(i + 1), true,45, 45);
            levelButtons[i].setId("levelButton" + String.valueOf(i));
        }
        HBox levelBox1 = createLevelBox(levelButtons, 1, 5);
        HBox levelBox2 = createLevelBox(levelButtons, 6, 10);
        HBox levelBox3 = createLevelBox(levelButtons, 11, 15);
        HBox levelBox4 = createLevelBox(levelButtons, 16, 20);
        HBox levelBox5 = createLevelBox(levelButtons, 21, 25);

        VBox levelsBox = new VBox();
        levelsBox.getChildren().addAll(levelBox1, levelBox2, levelBox3, levelBox4, levelBox5);
        levelsBox.setAlignment(Pos.CENTER);
        levelsBox.setSpacing(10);

        return levelsBox;
    }

    /**
     * Creates the bottom box of the main menu containing navigation buttons.
     *
     * @return The VBox representing the bottom box.
     */
    private VBox createBottomBox() {
        HBox buttonBoxBackAndNext = new HBox();
        buttonBoxBackAndNext.getChildren().addAll(backButton, nextButton);
        buttonBoxBackAndNext.setAlignment(Pos.CENTER);
        buttonBoxBackAndNext.setSpacing(10);

        HBox buttonBoxPlay = new HBox();
        buttonBoxPlay.getChildren().addAll(playButton);
        buttonBoxPlay.setAlignment(Pos.CENTER);

        VBox buttonBoxBottom = new VBox();
        buttonBoxBottom.getChildren().addAll(buttonBoxBackAndNext, buttonBoxPlay);
        buttonBoxBottom.setAlignment(Pos.CENTER);
        buttonBoxBottom.setSpacing(20);

        return buttonBoxBottom;
    }

    /**
     * Checks whether the back button can be used based on the current page.
     *
     * @return true if the back button can be used, false otherwise.
     */
    private boolean canUseBackButton() {
        return currentPage > 1;
    }

    /**
     * Checks whether the next button can be used based on the current page and the maximum count of levels.
     *
     * @return true if the next button can be used, false otherwise.
     */
    private boolean canUseNextButton() {
        return getMaxCountOfLevels() - levelButtons.length * currentPage > 0;
    }

    //For update
    /**
     * Shows the passed levels on the page by updating the level buttons' text and appearance.
     */
    private void showPassedLevelsOnPage(){
        for (Button levelButton : levelButtons) {
            tools.disabledButton(levelButton);
            levelButton.setText("?");
        }

        int firstLevelOnPage = getFirstLevelOnPage();
        int indexOfLastGeneratedLevelOnPage = getIndexOfLastLevelOnPage(getMaxCountOfLevels());
        int indexOfLastOpenedLevelOnPage = getIndexOfLastLevelOnPage(getCountOfLevels());

        for (int i = 0; i <= indexOfLastGeneratedLevelOnPage; i++) {
            levelButtons[i].setText(String.valueOf(firstLevelOnPage));
            if(i <= indexOfLastOpenedLevelOnPage){
                tools.standardButton(levelButtons[i]);
            }
            if(i == indexOfLastOpenedLevelOnPage){
                selectedLevelButton = levelButtons[i];
                tools.selectedButton(selectedLevelButton);
            }
            firstLevelOnPage++;
        }
    }

    /**
     * Updates the state of the navigation buttons based on the current page and the maximum count of levels.
     */
    private void updateButtons() {
        if (!canUseNextButton()) {
            tools.disabledButton(nextButton);
        } else {
            tools.standardButton(nextButton);
        }
        if (!canUseBackButton()) {
            tools.disabledButton(backButton);
        } else {
            tools.standardButton(backButton);
        }
    }

    /**
     * Changes the current page by the specified delta.
     *
     * @param delta The change in the current page (positive or negative).
     */
    private void changeCurrentPage(int delta) {
        try {
            if (currentPage + delta <= 0) {
                throw new InvalidParameterException("Invalid page.");
            } else {
                currentPage += delta;
                showPassedLevelsOnPage();
                updateButtons();
            }
        } catch (InvalidParameterException exception) {
            logger.error(exception.getMessage());
        }
    }

    // Getters

    /**
     * Retrieves the maximum count of levels.
     *
     * @return The maximum count of levels.
     */
    private int getMaxCountOfLevels() {
        return 66;
    }

    /**
     * Retrieves the count of levels based on the current game mode.
     *
     * @return The count of levels.
     */
    private int getCountOfLevels() {
        if (currentMode == classicSudokuButton) {
            return levelsInModeClassic.getLevels().size();
        } else {
            return levelsInModeKiller.getLevels().size();
        }
    }

    /**
     * Calculates the number of the first level on the current page.
     *
     * @return The number of the first level on the page.
     */
    private int getFirstLevelOnPage() {
        return ((currentPage - 1) * levelButtons.length) + 1;
    }

    /**
     * Calculates the index of the last level on the current page.
     *
     * @param countOfLevel The total count of levels.
     * @return The index of the last level on the page.
     */
    private int getIndexOfLastLevelOnPage(int countOfLevel) {
        int lastIndexLevelOnPage;
        if (countOfLevel >= levelButtons.length * currentPage) {
            lastIndexLevelOnPage = levelButtons.length - 1;
        } else {
            lastIndexLevelOnPage = countOfLevel - (currentPage - 1) * levelButtons.length - 1;
        }
        return lastIndexLevelOnPage;
    }

    // Action on buttons
    /**
     * Performs the action associated with the back button.
     */
    private void actionOnBackButton() {
        changeCurrentPage(-1);
    }

    /**
     * Performs the action associated with the next button.
     */
    public void actionOnNextButton() {
        changeCurrentPage(1);
    }

    /**
     * Performs the action associated with the classic Sudoku button.
     */
    private void actionOnClassicSudokuButton() {
        tools.selectedButton(classicSudokuButton);
        tools.standardButton(killerSudokuButton);
        currentMode = classicSudokuButton;
        currentPage = 1;
        showPassedLevelsOnPage();
        updateButtons();
    }

    /**
     * Performs the action associated with the killer Sudoku button.
     */
    private void actionOnKillerSudokuButton() {
        tools.selectedButton(killerSudokuButton);
        tools.standardButton(classicSudokuButton);
        currentMode = killerSudokuButton;
        currentPage = 1;
        showPassedLevelsOnPage();
        updateButtons();
    }

    /**
     * Performs the action associated with a level button.
     *
     * @param button The button clicked by the user.
     */
    private void actionOnLevelButton(Button button) {
        selectedLevelButton = button;
        tools.selectedButton(button);
        for (Button levelButton : levelButtons) {
            if(selectedLevelButton != levelButton && !levelButton.isDisable()){
                tools.standardButton(levelButton);
            }
        }
    }

    /**
     * Performs the action associated with the play button.
     */
    private void actionOnPlayButton() {
        if (currentMode == classicSudokuButton) {
            createFiledClassicChild = new CreateFiledClassicChild(levelsInModeClassic.createOrGetLevel(selectedLevelButton.getText()));
            createFiledClassicChild.loadSudokuFiled();
        } else {
            createFiledKillerChild = new CreateFiledKillerChild(levelsInModeKiller.createOrGetLevel(selectedLevelButton.getText()));
            createFiledKillerChild.loadSudokuFiled();
        }
    }
}

