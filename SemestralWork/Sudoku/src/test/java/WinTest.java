import cz.cvut.fel.interfceRealization.CreateSudokuFiled.CreateFiledKillerChild;
import cz.cvut.fel.logic.Main;
import cz.cvut.fel.vizualization.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import static cz.cvut.fel.logic.Main.createFiledKillerChild;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.assertions.api.Assertions.assertThat;

public class WinTest extends ApplicationTest {
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
        int[][] solveBoard =  Main.levelsInModeKiller.createOrGetLevel("1").getSolveBord();
        Main.levelsInModeKiller.createOrGetLevel("1").setLevelGameBord(solveBoard);
        createFiledKillerChild.levelCompleted();
    }

    @BeforeEach
    public void setUp() {
        robot = new FxRobot();
    }

    @Test
    public void testMainMenuLoadsCorrectly() {
        Button nextLevelButton = robot.lookup("#nextLevelButton").queryAs(Button.class);
        assertNotNull(nextLevelButton, "nextLevelButton should be present");
        assertFalse(nextLevelButton.isDisabled(), "nextLevelButton button should be disabled initially");

        Button restartButton = robot.lookup("#restartButton").queryAs(Button.class);
        assertNotNull(restartButton, "restartButton should be present");
        assertFalse(restartButton.isDisabled(), "restartButton button should be disabled initially");

        Button mainMenuButton = robot.lookup("#mainMenuButton").queryAs(Button.class);
        assertNotNull(mainMenuButton, "mainMenuButton should be present");
        assertFalse(mainMenuButton.isDisabled(), "mainMenuButton button should be disabled initially");

        Label winLabel = robot.lookup("#winLabel").queryAs(Label.class);
        assertNotNull(winLabel, "winLabel should be present");
        assertFalse(winLabel.isDisabled(), "winLabel button should be disabled initially");
    }
}
