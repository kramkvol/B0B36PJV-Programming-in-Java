package cz.cvut.fel.interfceRealization.CreateMode;

import cz.cvut.fel.Interface.CreateModeInterface;
import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * The CreateModeTemplate class is an abstract class that provides a template for creating different game modes.
 * It implements the CreateModeInterface and manages a list of levels for a specific mode.
 *
 * @param <T> the type of CreateLevelTemplate
 */
public abstract class CreateModeTemplate<T extends CreateLevelTemplate> implements CreateModeInterface<T> {
    protected List<T> levelsList;
    protected String mode;

    /**
     * Constructor for CreateModeTemplate.
     *
     * @param mode the mode of the game
     */
    public CreateModeTemplate(String mode) {
        this.mode = mode;
        this.levelsList = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getLevels() {
        return this.levelsList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToList(T createLevel) {
        levelsList.add(createLevel);
    }

}
