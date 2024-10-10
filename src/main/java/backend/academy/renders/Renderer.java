package backend.academy.renders;

import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
