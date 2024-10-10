import backend.academy.controller.UserController;
import backend.academy.controller.impl.UserControllerImpl;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerImplTest {

    @Test
    public void testReadValidInput() {
        String input = "5";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        UserController controller = new UserControllerImpl(in, new PrintStream(out));

        String result = controller.read("Введите число: ");
        assertEquals("5", result);
    }

    @Test
    public void testWriteOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        UserControllerImpl controller = new UserControllerImpl(System.in, new PrintStream(out));

        controller.write("Hello, world!");
        assertEquals("Hello, world!\n", out.toString());
    }

    @Test
    public void testInvalidNumericInput() {
        UserControllerImpl controller = new UserControllerImpl(System.in, System.out);

        assertFalse(controller.isValidNumeric("abc"));
        assertFalse(controller.isValidNumeric("12abc"));
        assertFalse(controller.isValidNumeric(""));
    }

    @Test
    public void testValidNumericInput() {
        UserControllerImpl controller = new UserControllerImpl(System.in, System.out);

        assertTrue(controller.isValidNumeric("123"));
        assertTrue(controller.isValidNumeric(" 456 "));
    }

    @Test
    public void testValidListSize() {
        UserControllerImpl controller = new UserControllerImpl(System.in, System.out);

        assertTrue(controller.isValidListSize(1, 5));
        assertTrue(controller.isValidListSize(5, 5));
        assertFalse(controller.isValidListSize(0, 5));
        assertFalse(controller.isValidListSize(6, 5));
    }
}
