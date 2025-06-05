package cz.cvut.fel.interfceRealization.CreateMode;

import cz.cvut.fel.Interface.CreateModeInterface;
import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelKillerChild;
import java.util.ArrayList;
import java.util.List;

/**
 * The CreateModeKillerChild class implements the CreateModeInterface for the Killer Sudoku mode.
 * It manages a list of levels specific to Killer Sudoku.
 */
public class CreateModeKillerChild implements CreateModeInterface<CreateLevelKillerChild> {

    /**
     * Constructor for CreateModeKillerChild.
     *
     * @param mode the mode of the game
     */
    private List<CreateLevelKillerChild> levelsList;
    private String mode;

    public CreateModeKillerChild(String mode) {
        this.mode = mode;
        this.levelsList = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CreateLevelKillerChild> getLevels() {
        return this.levelsList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToList(CreateLevelKillerChild createLevelKillerChild) {
        levelsList.add(createLevelKillerChild);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateLevelKillerChild createOrGetLevel(String level) {
        for (CreateLevelKillerChild l : this.getLevels()) {
            if (l != null && l.getLevel().equalsIgnoreCase(level)) {
                return l;
            }
        }
        CreateLevelKillerChild createLevelKillerChild = new CreateLevelKillerChild(this.mode, level);
        addToList(createLevelKillerChild);
        return createLevelKillerChild;
    }
}
