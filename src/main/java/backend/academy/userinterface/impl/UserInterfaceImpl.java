package backend.academy.userinterface.impl;

import backend.academy.constants.InterfaceTemplates;
import backend.academy.constants.enums.Describable;
import backend.academy.constants.enums.GeneratorType;
import backend.academy.constants.enums.SolverType;
import backend.academy.controller.UserController;
import backend.academy.dto.Cell;
import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import backend.academy.dto.Parameters;
import backend.academy.renders.Renderer;
import backend.academy.renders.impl.MazeRendererImpl;
import backend.academy.solvers.Solver;
import backend.academy.userinterface.UserInterface;
import java.util.List;

public class UserInterfaceImpl implements UserInterface {

    private final UserController controller;
    private final Renderer renderer;

    public UserInterfaceImpl(UserController controller) {
        this.controller = controller;
        this.renderer = new MazeRendererImpl();
    }

    @Override
    public Parameters start() {
        Parameters parameters = new Parameters();
        parameters.height(getHeight());
        parameters.width(getWidth());
        parameters.generatorType(getGeneratorType());
        parameters.solverType(getSolverType());
        return parameters;
    }

    @Override
    public void renderMaze(Maze maze) {
        write("Сгенерированный лабиринт:");
        write(renderer.render(maze));
        write(InterfaceTemplates.FLOOR_INFO);
    }

    @Override
    public void solve(Maze maze, Solver solver) {
        Coordinate start = getPoints(maze, "начальную");
        Coordinate end = getPoints(maze, "конечную");

        List<Coordinate> path = solver.solve(maze, start, end);
        if (path.isEmpty()) {
            write("Путь не найден.");
        } else {
            write(renderer.render(maze, path));
        }
    }

    public int getHeight() {
        int height = 0;
        while (height < 3) {
            height = getValidUserInput("Введите высоту: ");
            if (height <= 2) {
                write("Высота должна быть больше двойки.");
            }
        }
        return height;
    }

    public int getWidth() {
        int width = 0;
        while (width < 2) {
            width = getValidUserInput("Введите ширину: ");
            if (width <= 1) {
                write("Ширина должна быть больше единицы.");
            }
        }
        return width;
    }

    public Coordinate getPoints(Maze maze, String prompt) {
        Coordinate point = null;
        while (point == null) {
            write("Выберете " + prompt + " координату.");
            int startRow = getValidUserInput("Строка: ");
            int startCol = getValidUserInput("Столбец: ");

            if (isValidCoordinate(maze, startRow, startCol)) {
                point = new Coordinate(startRow, startCol);
            } else {
                write("Недопустимая координата.");
            }
        }
        return point;
    }

    public <T extends Enum<T>> T getTypeChoice(T[] values, String prompt) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Describable describable) {
                write((i + 1) + ". " + describable.description());
            }
        }
        int input;
        do {
            input = getValidUserInput(prompt);
            if (!controller.isValidListSize(input, values.length)) {
                write("Выберите пункт из списка.");
            }
        } while (!controller.isValidListSize(input, values.length));

        return values[input - 1];
    }


    public GeneratorType getGeneratorType() {
        return getTypeChoice(GeneratorType.values(), "Выберите алгоритм генерации лабиринта: ");
    }


    public SolverType getSolverType() {
        return getTypeChoice(SolverType.values(), "Выберите алгоритм поиска пути: ");
    }


    public int getValidUserInput(String prompt) {
        String input;
        do {
            input = getUserInput(prompt);
            if (!controller.isValidNumeric(input)) {
                write("Введите целое число.");
            }
        } while (!controller.isValidNumeric(input));

        return Integer.parseInt(input);
    }

    private String getUserInput(String prompt) {
        return controller.read(prompt);
    }

    private void write(String prompt) {
        controller.write(prompt);
    }

    private static boolean isValidCoordinate(Maze maze, int row, int col) {
        int height = maze.height();
        int width = maze.width();

        if (row >= 0 && row < height && col >= 0 && col < width) {
            Cell cell = maze.getCell(row, col);
            return cell.type() == Cell.Type.PASSAGE;
        }
        return false;
    }
}
