package cz.cvut.fel.interfceRealization.CreateLevel;

/**
 * The CreateLevelClassicChild class extends CreateLevelTemplate and provides a specific implementation
 * for the classic Sudoku variant. It inherits all the functionality from CreateLevelTemplate without
 * adding any new behavior.
 */
public class CreateLevelClassicChild extends CreateLevelTemplate {

    /**
     * Constructor for CreateLevelClassicChild.
     *
     * @param mode  the mode of the game
     * @param level the level of difficulty
     */
    public CreateLevelClassicChild(String mode, String level) {
        super(mode, level);
    }
}
