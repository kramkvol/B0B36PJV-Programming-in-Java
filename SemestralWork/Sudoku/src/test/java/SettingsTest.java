import cz.cvut.fel.interfceRealization.CreateSudokuFiled.CreateFiledKillerChild;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SettingsTest extends ApplicationTest {

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
        createFiledKillerChild.settingsOnAction();
    }

    @BeforeEach
    public void setUp() {
        robot = new FxRobot();
    }

    @Test
    public void testSettingsLoadsCorrectly() {
        Button continueButton = robot.lookup("#continueButton").queryAs(Button.class);
        assertNotNull(continueButton, "continueButton should be present");
        assertFalse(continueButton.isDisabled(), "continueButton button should be disabled initially");

        Button restartButton = robot.lookup("#restartButton").queryAs(Button.class);
        assertNotNull(restartButton, "restartButton should be present");
        assertFalse(restartButton.isDisabled(), "restartButton button should be disabled initially");

        Button mainMenuButton = robot.lookup("#mainMenuButton").queryAs(Button.class);
        assertNotNull(mainMenuButton, "mainMenuButton should be present");
        assertFalse(mainMenuButton.isDisabled(), "mainMenuButton button should be disabled initially");

        Button hintsButton = robot.lookup("#hintsButton").queryAs(Button.class);
        assertNotNull(hintsButton, "hintsButton");
        assertFalse(hintsButton.isDisabled(), "hintsButton");
    }
}