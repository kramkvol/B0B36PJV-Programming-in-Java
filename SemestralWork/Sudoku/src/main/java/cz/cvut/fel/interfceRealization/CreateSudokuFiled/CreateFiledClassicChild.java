package cz.cvut.fel.interfceRealization.CreateSudokuFiled;

import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelClassicChild;
import static cz.cvut.fel.logic.Main.*;

/**
 * A class representing the creation of Sudoku fields in Classic mode.
 */
public class CreateFiledClassicChild extends SudokuFiledTemplate<CreateLevelClassicChild> {

    private CreateLevelClassicChild createLevelClassicChild;

    /**
     * Constructs a CreateFiledClassicChild with the specified level.
     *
     * @param createFiledClassicChild the level template for Classic Sudoku.
     */
    public CreateFiledClassicChild(CreateLevelClassicChild createFiledClassicChild) {
        super(createFiledClassicChild);
        this.createLevelClassicChild = levelsInModeClassic.createOrGetLevel(createFiledClassicChild.getLevel());
    }

    /**
     * Moves to the next level in Classic Sudoku mode and loads the corresponding Sudoku field.
     */
    @Override
    public void nextLevelButton() {
        createFiledClassicChild = new CreateFiledClassicChild(levelsInModeClassic.createOrGetLevel(String.valueOf(Integer.parseInt(createLevelClassicChild.getLevel()) + 1)));
        createFiledClassicChild.loadSudokuFiled();
    }

}
