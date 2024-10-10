package backend.academy.solvers;

import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
