import backend.academy.constants.enums.GeneratorType;
import backend.academy.constants.enums.SolverType;
import backend.academy.controller.UserController;
import backend.academy.dto.Cell;
import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import backend.academy.userinterface.impl.UserInterfaceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserInterfaceImplTest {

    private UserController controller;
    private UserInterfaceImpl userInterface;

    @BeforeEach
    public void setUp() {
        controller = mock(UserController.class);
        userInterface = new UserInterfaceImpl(controller);
    }

    @Test
    public void testGetWidth_withInvalidAndValidInputs() {
        when(controller.read(anyString()))
                .thenReturn("1")
                .thenReturn("3");

        when(controller.isValidNumeric("1")).thenReturn(true);
        when(controller.isValidNumeric("3")).thenReturn(true);

        int width = userInterface.getWidth();

        verify(controller, times(2)).read(anyString());

        verify(controller).write("Ширина должна быть больше единицы.");

        assertEquals(3, width);
    }

    @Test
    public void testGetHeight_withInvalidAndValidInputs() {
        when(controller.read(anyString()))
                .thenReturn("1")
                .thenReturn("5");

        when(controller.isValidNumeric("1")).thenReturn(true);
        when(controller.isValidNumeric("5")).thenReturn(true);

        int height = userInterface.getHeight();

        verify(controller, times(2)).read(anyString());

        verify(controller).write("Высота должна быть больше двойки.");

        assertEquals(5, height);
    }

    @Test
    public void testGetPoints_withInvalidAndValidInputs() {
        Maze maze = mock(Maze.class);
        Cell cellPassage = mock(Cell.class);
        Cell cellWall = mock(Cell.class);

        when(maze.height()).thenReturn(5);
        when(maze.width()).thenReturn(5);

        when(cellPassage.type()).thenReturn(Cell.Type.PASSAGE);
        when(cellWall.type()).thenReturn(Cell.Type.WALL);

        when(maze.getCell(2, 2)).thenReturn(cellWall);
        when(maze.getCell(1, 1)).thenReturn(cellPassage);

        when(controller.read(anyString()))
                .thenReturn("2")
                .thenReturn("2")
                .thenReturn("1")
                .thenReturn("1");

        when(controller.isValidNumeric("2")).thenReturn(true);
        when(controller.isValidNumeric("1")).thenReturn(true);

        Coordinate point = userInterface.getPoints(maze, "начальную");

        verify(controller, times(4)).read(anyString());

        verify(controller).write("Недопустимая координата.");

        assertEquals(1, point.row());
        assertEquals(1, point.col());
    }

    @Test
    public void testGetSolverType_withValidInput() {
        SolverType[] solverTypes = SolverType.values();

        when(controller.read(anyString())).thenReturn("2");

        when(controller.isValidNumeric("2")).thenReturn(true);
        when(controller.isValidListSize(2, solverTypes.length)).thenReturn(true);

        SolverType solverType = userInterface.getSolverType();

        assertEquals(solverTypes[1], solverType);
    }

    @Test
    public void testGetGeneratorType_withValidInput() {
        GeneratorType[] generatorTypes = GeneratorType.values();

        when(controller.read(anyString())).thenReturn("1");

        when(controller.isValidNumeric("1")).thenReturn(true);
        when(controller.isValidListSize(1, generatorTypes.length)).thenReturn(true);

        GeneratorType generatorType = userInterface.getGeneratorType();

        assertEquals(generatorTypes[0], generatorType);
    }

}
