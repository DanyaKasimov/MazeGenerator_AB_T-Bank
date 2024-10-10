package backend.academy.userinterface;

import backend.academy.dto.Maze;
import backend.academy.dto.Parameters;
import backend.academy.solvers.Solver;

public interface UserInterface {
    Parameters start();

    void renderMaze(Maze maze);

    void solve(Maze maze, Solver solver);
}
