package backend.academy.generators;

import backend.academy.dto.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
