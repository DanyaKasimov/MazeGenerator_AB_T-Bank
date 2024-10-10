package backend.academy;

import backend.academy.constants.enums.GeneratorType;
import backend.academy.constants.enums.SolverType;
import backend.academy.controller.UserController;
import backend.academy.controller.impl.UserControllerImpl;
import backend.academy.dto.Maze;
import backend.academy.dto.Parameters;
import backend.academy.generators.Generator;
import backend.academy.generators.impl.KruskalMazeGenerator;
import backend.academy.generators.impl.PrimMazeGenerator;
import backend.academy.solvers.Solver;
import backend.academy.solvers.impl.BFSSolverImpl;
import backend.academy.solvers.impl.DijkstraSolverImpl;
import backend.academy.userinterface.UserInterface;
import backend.academy.userinterface.impl.UserInterfaceImpl;

public class MazeApp {

    public void start() {
        UserController controller = new UserControllerImpl(System.in, System.out);
        UserInterface display = new UserInterfaceImpl(controller);
        Parameters parameters = display.start();

        Generator generator = parameters.generatorType().equals(GeneratorType.PRIM)
            ? new PrimMazeGenerator() : new KruskalMazeGenerator();

        Solver solver = parameters.solverType().equals(SolverType.BFS)
            ? new BFSSolverImpl() : new DijkstraSolverImpl();

        Maze maze = generator.generate(parameters.height(), parameters.width());

        display.renderMaze(maze);
        display.solve(maze, solver);
    }

}
