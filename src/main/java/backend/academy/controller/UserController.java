package backend.academy.controller;

public interface UserController {

    String read(String text);

    void write(String text);

    boolean isValidListSize(int input, int size);

    boolean isValidNumeric(String text);
}
