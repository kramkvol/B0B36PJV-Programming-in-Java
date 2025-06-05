package cz.cvut.fel.Interface;

import cz.cvut.fel.interfceRealization.CreateLevel.CreateLevelTemplate;
import java.util.List;

public interface CreateModeInterface<T extends CreateLevelTemplate> {
    List<T> getLevels();
    void addToList(T createLevel);
    T createOrGetLevel(String level);
}
