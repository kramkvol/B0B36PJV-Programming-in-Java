package cz.cvut.fel.interfceRealization.CreateMode;

import cz.cvut.fel.Interface.CreateModeInterface;
import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelClassicChild;
import java.util.ArrayList;
import java.util.List;

/**
 * The CreateModeClassicChild class implements the CreateModeInterface for the classic Sudoku mode.
 * It manages a list of levels specific to classic Sudoku.
 */

public class CreateModeClassicChild implements CreateModeInterface<CreateLevelClassicChild> {

    private List<CreateLevelClassicChild> levelsList;
    private String mode;

    /**
     * Constructor for CreateModeClassicChild.
     *
     * @param mode the mode of the game
     */
    public CreateModeClassicChild(String mode) {
        this.mode = mode;
        this.levelsList = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CreateLevelClassicChild> getLevels() {
        return this.levelsList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToList(CreateLevelClassicChild createLevelClassicChild) {
        levelsList.add(createLevelClassicChild);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateLevelClassicChild createOrGetLevel(String level) {
        for (CreateLevelClassicChild l : this.getLevels()) {
            if (l != null && l.getLevel().equalsIgnoreCase(level)) {
                return l;
            }
        }
        CreateLevelClassicChild createLevelClassicChild = new CreateLevelClassicChild(this.mode, level);
        addToList(createLevelClassicChild);
        return createLevelClassicChild;
    }
}
