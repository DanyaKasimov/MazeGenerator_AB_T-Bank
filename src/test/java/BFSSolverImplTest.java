import backend.academy.dto.Cell;
import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import backend.academy.solvers.impl.BFSSolverImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class BFSSolverImplTest {

    @Test
    public void testSimpleMaze() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL), new Cell(0, 2, Cell.Type.WALL)},
            {new Cell(1, 0, Cell.Type.PASSAGE), new Cell(1, 1, Cell.Type.PASSAGE), new Cell(1, 2, Cell.Type.PASSAGE)},
            {new Cell(2, 0, Cell.Type.WALL), new Cell(2, 1, Cell.Type.WALL), new Cell(2, 2, Cell.Type.WALL)}
        };
        Maze maze = new Maze(3, 3, grid);
        Coordinate start = new Coordinate(1, 0);
        Coordinate end = new Coordinate(1, 2);

        BFSSolverImpl solver = new BFSSolverImpl();
        List<Coordinate> path = solver.solve(maze, start, end);

        List<Coordinate> expectedPath = Arrays.asList(
            new Coordinate(1, 0),
            new Coordinate(1, 1),
            new Coordinate(1, 2)
        );

        assertEquals(expectedPath, path, "Поиск пути в простом лабиринте 3 на 3.");
    }

    @Test
    public void testMazeWithObstacles() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL), new Cell(0, 2, Cell.Type.WALL), new Cell(0, 3, Cell.Type.WALL), new Cell(0, 4, Cell.Type.WALL)},
            {new Cell(1, 0, Cell.Type.PASSAGE), new Cell(1, 1, Cell.Type.WALL), new Cell(1, 2, Cell.Type.PASSAGE), new Cell(1, 3, Cell.Type.PASSAGE), new Cell(1, 4, Cell.Type.WALL)},
            {new Cell(2, 0, Cell.Type.PASSAGE), new Cell(2, 1, Cell.Type.PASSAGE), new Cell(2, 2, Cell.Type.WALL), new Cell(2, 3, Cell.Type.PASSAGE), new Cell(2, 4, Cell.Type.WALL)},
            {new Cell(3, 0, Cell.Type.WALL), new Cell(3, 1, Cell.Type.PASSAGE), new Cell(3, 2, Cell.Type.PASSAGE), new Cell(3, 3, Cell.Type.PASSAGE), new Cell(3, 4, Cell.Type.PASSAGE)},
            {new Cell(4, 0, Cell.Type.WALL), new Cell(4, 1, Cell.Type.WALL), new Cell(4, 2, Cell.Type.WALL), new Cell(4, 3, Cell.Type.WALL), new Cell(4, 4, Cell.Type.WALL)}
        };
        Maze maze = new Maze(5, 5, grid);
        Coordinate start = new Coordinate(1, 0);
        Coordinate end = new Coordinate(3, 4);

        BFSSolverImpl solver = new BFSSolverImpl();
        List<Coordinate> path = solver.solve(maze, start, end);

        List<Coordinate> expectedPath = Arrays.asList(
            new Coordinate(1, 0),
            new Coordinate(2, 0),
            new Coordinate(2, 1),
            new Coordinate(3, 1),
            new Coordinate(3, 2),
            new Coordinate(3, 3),
            new Coordinate(3, 4)
        );

        assertEquals(expectedPath, path, "Поиск пути в лабиринте с препятствием 5 на 5.");
    }

    @Test
    public void testNoPathAvailable() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL), new Cell(0, 2, Cell.Type.WALL)},
            {new Cell(1, 0, Cell.Type.PASSAGE), new Cell(1, 1, Cell.Type.WALL), new Cell(1, 2, Cell.Type.PASSAGE)},
            {new Cell(2, 0, Cell.Type.WALL), new Cell(2, 1, Cell.Type.WALL), new Cell(2, 2, Cell.Type.WALL)}
        };
        Maze maze = new Maze(3, 3, grid);
        Coordinate start = new Coordinate(1, 0);
        Coordinate end = new Coordinate(1, 2);

        BFSSolverImpl solver = new BFSSolverImpl();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertTrue(path.isEmpty(), "Поиск несуществующего пути.");
    }

    @Test
    public void testFindingMinimumPath() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL), new Cell(0, 2, Cell.Type.WALL), new Cell(0, 3, Cell.Type.WALL), new Cell(0, 4, Cell.Type.WALL)},
            {new Cell(1, 0, Cell.Type.WALL), new Cell(1, 1, Cell.Type.PASSAGE), new Cell(1, 2, Cell.Type.PASSAGE), new Cell(1, 3, Cell.Type.PASSAGE), new Cell(1, 4, Cell.Type.WALL)},
            {new Cell(2, 0, Cell.Type.PASSAGE), new Cell(2, 1, Cell.Type.PASSAGE), new Cell(2, 2, Cell.Type.WALL), new Cell(2, 3, Cell.Type.PASSAGE), new Cell(2, 4, Cell.Type.WALL)},
            {new Cell(3, 0, Cell.Type.WALL), new Cell(3, 1, Cell.Type.PASSAGE), new Cell(3, 2, Cell.Type.PASSAGE), new Cell(3, 3, Cell.Type.PASSAGE), new Cell(3, 4, Cell.Type.WALL)},
            {new Cell(4, 0, Cell.Type.WALL), new Cell(4, 1, Cell.Type.WALL), new Cell(4, 2, Cell.Type.PASSAGE), new Cell(4, 3, Cell.Type.WALL), new Cell(4, 4, Cell.Type.WALL)}
        };
        Maze maze = new Maze(5, 5, grid);
        Coordinate start = new Coordinate(2, 0);
        Coordinate end = new Coordinate(4, 2);

        BFSSolverImpl solver = new BFSSolverImpl();
        List<Coordinate> path = solver.solve(maze, start, end);
        List<Coordinate> expectedPath = Arrays.asList(
            new Coordinate(2, 0),
            new Coordinate(2, 1),
            new Coordinate(3, 1),
            new Coordinate(3, 2),
            new Coordinate(4, 2)
        );
        assertEquals(path, expectedPath, "Поиск минимального пути из существующих (2).");
    }
}
