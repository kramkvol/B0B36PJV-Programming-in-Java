
import cz.cvut.fel.logic.Main;
import cz.cvut.fel.vizualization.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

public class MainMenuTest extends ApplicationTest {

    private FxRobot robot;

    @Override
    public void start(Stage primaryStage) {
        Main.setCurrentPrimaryStage(primaryStage);
        BorderPane root = new BorderPane();
        Main.setCurrentRoot(root);
        root.setStyle("-fx-background-color: #FFB366;");
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 500, 700);
        primaryStage.setTitle("Main menu");
        primaryStage.setScene(scene);
        primaryStage.show();

        Main.addCells();
        Main.levelsInModeKiller.createOrGetLevel("1");

        MainMenu mainMenu = new MainMenu();
        mainMenu.mainMenuStart();
    }

    @BeforeEach
    public void setUp() {
        robot = new FxRobot();
    }

    @Test
    public void testMainMenuLoadsCorrectly() {
        Button classicSudokuButton = robot.lookup("#classicSudokuButton").queryAs(Button.class);
        Button killerSudokuButton = robot.lookup("#killerSudokuButton").queryAs(Button.class);
        Button playButton = robot.lookup("#playButton").queryAs(Button.class);
        Button backButton = robot.lookup("#backButton").queryAs(Button.class);
        Button nextButton = robot.lookup("#nextButton").queryAs(Button.class);

        assertNotNull(classicSudokuButton, "Classic Sudoku button should be present");
        assertNotNull(killerSudokuButton, "Killer Sudoku button should be present");
        assertNotNull(playButton, "Play button should be present");
        assertNotNull(backButton, "Back button should be present");
        assertNotNull(nextButton, "Next button should be present");

        assertFalse(playButton.isDisabled(), "Play button should be disabled initially");
        assertTrue(backButton.isDisabled(), "Back button should be disabled initially");
        assertFalse(nextButton.isDisabled(), "Next button should be disabled initially");
        assertFalse(classicSudokuButton.isDisable(), "classicSudokuButton should be disabled initially");
        assertFalse(killerSudokuButton.isDisabled(), "killerSudokuButton should be disabled initially");

        assertThat(classicSudokuButton).hasText("Sudoku Classic");
        assertThat(killerSudokuButton).hasText("Sudoku Killer");
        assertThat(playButton).hasText("Play");
        assertThat(backButton).hasText("Back");
        assertThat(nextButton).hasText("Next");
    }

    @Test
    public void testMenuCreation() {
        Button button;
        for(int i = 0; i < 25; i++) {
            button = robot.lookup("#levelButton" + i).queryAs(Button.class);
            assertThat(button).hasText(String.valueOf(i+1));
            assertTrue(button.isDisabled(), "levelButton should be disabled initially");
        }
    }
}
