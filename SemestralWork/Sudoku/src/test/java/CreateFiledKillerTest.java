import cz.cvut.fel.interfceRealization.CreateSudokuFiled.CreateFiledKillerChild;
import cz.cvut.fel.logic.Main;
import cz.cvut.fel.vizualization.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class CreateFiledKillerTest extends ApplicationTest {

    private CreateFiledKillerChild createFiledKillerChild;
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

        createFiledKillerChild = new CreateFiledKillerChild(Main.levelsInModeKiller.createOrGetLevel("1"));
        createFiledKillerChild.loadSudokuFiled();
    }

    @BeforeEach
    public void setUp() {
        robot = new FxRobot();
    }

    @Test
    public void testSudokuCellCreation() {
        Button undoButton = robot.lookup("#undoButton").queryAs(Button.class);
        assertNotNull(undoButton, "Undo button should be present");
        assertFalse(undoButton.isDisabled(), "Undo button should be disabled initially");

        Button redoButton = robot.lookup("#redoButton").queryAs(Button.class);
        assertNotNull(redoButton, "Redo button should be present");
        assertFalse(redoButton.isDisabled(), "Redo button should be disabled initially");

        Label hintLabel = robot.lookup("#hintLabel").queryAs(Label.class);
        assertNotNull(hintLabel, "Hint label should be present");
        assertFalse(hintLabel.isDisabled(), "Hint label should be disabled initially");

        Label levelInfoLabel = robot.lookup("#levelInfoLabel").queryAs(Label.class);
        assertNotNull(levelInfoLabel, "LevelInfoLabel should be present");
        assertFalse(levelInfoLabel.isDisabled(), "LevelInfoLabel should be disabled initially");

        Label timerLabel = robot.lookup("#timerLabel").queryAs(Label.class);
        assertNotNull(timerLabel, "TimerLabel should be present");
        assertFalse(timerLabel.isDisabled(), "TimerLabel should be disabled initially");
    }

    @Test
    public void testSudokuFiledCreation() {
        GridPane gridPane = robot.lookup("#gridPane").queryAs(GridPane.class);
        assertNotNull(gridPane, "GridPane should not be null");

        assertEquals(81, gridPane.getChildren().size(), "Expected 81 children in GridPane");

        int textFieldCount = 0;
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                textFieldCount++;
                assertTrue(node.isVisible(), "Expected TextField to be visible");
            }
        }
        assertEquals(81, textFieldCount, "Expected 81 TextFields in GridPane");
    }

}
