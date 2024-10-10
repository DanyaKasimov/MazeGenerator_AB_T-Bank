import backend.academy.dto.Cell;
import backend.academy.dto.Maze;
import backend.academy.renders.Renderer;
import backend.academy.renders.impl.MazeRendererImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeRendererImplTest {

    private Maze maze;
    private Renderer renderer;

    @BeforeEach
    void setUp() {
        Cell[][] grid = {
            {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL), new Cell(0, 2, Cell.Type.WALL), new Cell(0, 3, Cell.Type.WALL), new Cell(0, 4, Cell.Type.WALL)},
            {new Cell(1, 0, Cell.Type.WALL), new Cell(1, 1, Cell.Type.PASSAGE, Cell.Surface.GOOD_FLOOR), new Cell(1, 2, Cell.Type.PASSAGE, Cell.Surface.GOOD_FLOOR), new Cell(1, 3, Cell.Type.PASSAGE, Cell.Surface.GOOD_FLOOR), new Cell(1, 4, Cell.Type.WALL)},
            {new Cell(2, 0, Cell.Type.PASSAGE), new Cell(2, 1, Cell.Type.PASSAGE), new Cell(2, 2, Cell.Type.WALL), new Cell(2, 3, Cell.Type.PASSAGE, Cell.Surface.GOOD_FLOOR), new Cell(2, 4, Cell.Type.PASSAGE, Cell.Surface.GOOD_FLOOR)},
            {new Cell(3, 0, Cell.Type.WALL), new Cell(3, 1, Cell.Type.PASSAGE, Cell.Surface.SWAMP), new Cell(3, 2, Cell.Type.PASSAGE, Cell.Surface.SWAMP), new Cell(3, 3, Cell.Type.PASSAGE, Cell.Surface.SWAMP), new Cell(3, 4, Cell.Type.PASSAGE)},
            {new Cell(4, 0, Cell.Type.WALL), new Cell(4, 1, Cell.Type.WALL), new Cell(4, 2, Cell.Type.WALL), new Cell(4, 3, Cell.Type.WALL), new Cell(4, 4, Cell.Type.WALL)}
        };
        maze = new Maze(5, 5, grid);

        renderer = new MazeRendererImpl();
    }

    @Test
    public void testMazeRenderer() {
        String renderedMaze = renderer.render(maze);

        String actualMaze = """
                 0  1  2  3  4\s
             0 \u001B[40m   \u001B[0m\u001B[40m   \u001B[0m\u001B[40m   \u001B[0m\u001B[40m   \u001B[0m\u001B[40m   \u001B[0m
             1 \u001B[40m   \u001B[0m\u001B[46m   \u001B[0m\u001B[46m   \u001B[0m\u001B[46m   \u001B[0m\u001B[40m   \u001B[0m
             2 \u001B[47m   \u001B[0m\u001B[47m   \u001B[0m\u001B[40m   \u001B[0m\u001B[46m   \u001B[0m\u001B[46m   \u001B[0m
             3 \u001B[40m   \u001B[0m\u001B[42m\u001B[30m ~ \u001B[0m\u001B[42m\u001B[30m ~ \u001B[0m\u001B[42m\u001B[30m ~ \u001B[0m\u001B[47m   \u001B[0m
             4 \u001B[40m   \u001B[0m\u001B[40m   \u001B[0m\u001B[40m   \u001B[0m\u001B[40m   \u001B[0m\u001B[40m   \u001B[0m
            """;


        assertEquals(actualMaze, renderedMaze, "Проверка на корректное отображение лабиринтов в консоли.");
    }
}

