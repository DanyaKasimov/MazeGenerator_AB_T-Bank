package backend.academy.controller.impl;


import backend.academy.controller.UserController;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

    private final InputStream inputStream;
    private final PrintStream printStream;

    public UserControllerImpl(InputStream inputStream, PrintStream printStream) {
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    @Override
    public String read(String text) {
        Scanner scanner = new Scanner(inputStream);
        printStream.print(text);
        return scanner.nextLine().trim();
    }

    @Override
    public void write(String text) {
        printStream.println(text);
    }

    @Override
    public boolean isValidNumeric(String text) {
        try {
            int in = Integer.parseInt(text.trim());
            return in >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidListSize(int input, int size) {
        return input >= 1 && input <= size;
    }
}
