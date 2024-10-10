package backend.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }
}
