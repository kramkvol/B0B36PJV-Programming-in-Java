package cz.cvut.fel.logic;

import cz.cvut.fel.interfceRealization.CreateMode.CreateModeClassicChild;
import cz.cvut.fel.interfceRealization.CreateMode.CreateModeKillerChild;
import cz.cvut.fel.interfceRealization.CreateSudokuFiled.Cell;
import cz.cvut.fel.interfceRealization.CreateSudokuFiled.CreateFiledClassicChild;
import cz.cvut.fel.interfceRealization.CreateSudokuFiled.CreateFiledKillerChild;
import cz.cvut.fel.vizualization.MainMenu;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main class serves as the entry point of the Sudoku application.
 * It extends the JavaFX Application class.
 */
public class Main extends Application {

    // Static variables
    public static Stage currentPrimaryStage;
    public static BorderPane currentRoot;

    // Setters for static variables
    public static void setCurrentPrimaryStage(Stage primaryStage) {
        currentPrimaryStage = primaryStage;
    }
    public static void setCurrentRoot(BorderPane root) {
        currentRoot = root;
    }

    // General static constants
    public static CreateModeClassicChild levelsInModeClassic = new CreateModeClassicChild("Sudoku Classic");
    public static CreateModeKillerChild levelsInModeKiller = new CreateModeKillerChild("Sudoku Killer");
    public static CreateFiledClassicChild createFiledClassicChild;
    public static CreateFiledKillerChild createFiledKillerChild;
    public static MainMenu mainMenu;
    public static final Tools tools = new Tools();
    public static final Logger logger = LogManager.getLogger();
    public static boolean logs = true;
    public static boolean gameModeButtonsVisible = true;

    public static List<Cell> cells = new ArrayList<>();

    /**
     * Adds cells to the list for Sudoku grid.
     */
    public static void addCells(){
        for(int row = 0; row < 9; row++)
        {
            for(int col = 0; col < 9; col++) {
                cells.add(new Cell(row, col));
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setCurrentPrimaryStage(primaryStage);
        BorderPane root = new BorderPane();
        setCurrentRoot(root);
        currentRoot.setStyle("-fx-background-color: #FFB366;");
        currentRoot.setPadding(new Insets(20));

        Scene scene = new Scene(currentRoot, 500, 700);
        currentPrimaryStage.setTitle("Main menu");
        currentPrimaryStage.setScene(scene);
        currentPrimaryStage.show();

        levelsInModeClassic.createOrGetLevel("1");
        addCells();
        levelsInModeKiller.createOrGetLevel("1");

        mainMenu = new MainMenu();
        mainMenu.mainMenuStart();
    }
}
