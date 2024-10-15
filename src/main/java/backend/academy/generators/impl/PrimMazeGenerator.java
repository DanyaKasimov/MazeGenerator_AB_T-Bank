package backend.academy.generators.impl;

import backend.academy.constants.Config;
import backend.academy.dto.Cell;
import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import backend.academy.generators.Generator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Генератор лабиринта с использованием алгоритма Прима.
 * Лабиринт представляет собой сетку, где стены и проходы размещены на определенных позициях.
 * Алгоритм начинает со случайной клетки-прохода и постепенно распространяется,
 * добавляя соседние клетки в качестве потенциальных стен для разрушения.
 * <p>
 * Лабиринт создается по следующей логике:
 * <ul>
 *     <li>Инициализируется сетка, где все клетки являются стенами.</li>
 *     <li>Выбирается случайная стартовая клетка и помечается как проход.</li>
 *     <li>Соседние клетки-стены добавляются в список стен.</li>
 *     <li>Пока есть стены в списке:
 *         <ul>
 *             <li>Случайно выбирается стена из списка.</li>
 *             <li>Если стена имеет только одного соседа-прохода, стена превращается в проход, разрушая стену между
 *             собой и этим соседом.</li>
 *             <li>Соседние стены добавляются в список стен.</li>
 *         </ul>
 *     </li>
 *     <li>Поверхности клеток проходов задаются случайно, с различными типами
 *     (обычные, болота, монеты и хорошие покрытия), которые могут влиять на прохождение пути.</li>
 * </ul>
 * Алгоритм гарантирует, что лабиринт будет иметь единственный связный путь между любыми двумя точками.
 */

public class PrimMazeGenerator implements Generator {
    /**
     * Генерирует лабиринт с указанными высотой и шириной.
     *
     * @param heightP высота сетки лабиринта
     * @param widthP  ширина сетки лабиринта
     * @return сгенерированный объект {@link Maze}
     */
    @Override
    public Maze generate(int heightP, int widthP) {
        int height = heightP;
        int width = widthP;

        if (height % 2 == 0) {
            height += 1;
        }
        if (width % 2 == 0) {
            width += 1;
        }

        Cell[][] grid = new Cell[height][width];
        Random random = new Random();

        // Инициализация сетки стенами
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL);
            }
        }

        // Выбор случайной стартовой точки
        int startRow = randomOdd(random, height);
        int startCol = randomOdd(random, width);
        grid[startRow][startCol] = new Cell(startRow, startCol, Cell.Type.PASSAGE, Cell.Surface.randomSurface(random));

        // Инициализация списка стен соседями стартовой клетки
        Set<Cell> wallList = new HashSet<>();
        for (Coordinate coord : getNeighbors(startRow, startCol, grid, Cell.Type.WALL, Config.PRIM_STEP)) {
            wallList.add(grid[coord.row()][coord.col()]);
        }

        // Основной цикл алгоритма Прима
        while (!wallList.isEmpty()) {
            Cell[] walls = wallList.toArray(new Cell[0]);
            Cell wall = walls[random.nextInt(walls.length)];
            wallList.remove(wall);

            int row = wall.row();
            int col = wall.col();

            List<Coordinate> passageNeighbors = getNeighbors(row, col, grid, Cell.Type.PASSAGE, Config.PRIM_STEP);

            if (passageNeighbors.size() == 1) {
                grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE, Cell.Surface.randomSurface(random));

                // Соединение клетки с соседней клеткой-проходом
                Coordinate passageNeighbor = passageNeighbors.getFirst();
                int betweenRow = (row + passageNeighbor.row()) / 2;
                int betweenCol = (col + passageNeighbor.col()) / 2;
                grid[betweenRow][betweenCol] =
                    new Cell(betweenRow, betweenCol, Cell.Type.PASSAGE, Cell.Surface.randomSurface(random));

                // Добавление соседних стен в список стен
                for (Coordinate coord : getNeighbors(row, col, grid, Cell.Type.WALL, Config.PRIM_STEP)) {
                    wallList.add(grid[coord.row()][coord.col()]);
                }
            }
        }

        return new Maze(height, width, grid);
    }

    /**
     * Генерирует случайное нечетное целое число меньше указанной границы.
     *
     * @param random экземпляр {@link Random}
     * @param bound  верхняя граница (не включительно)
     * @return случайное нечетное целое число меньше bound
     */
    private int randomOdd(Random random, int bound) {
        int r = random.nextInt(bound / 2) * 2 + 1;
        return r < bound ? r : r - 1;
    }

    /**
     * Получает соседние координаты клетки в сетке на основе указанного типа и шага.
     *
     * @param row индекс строки текущей клетки
     * @param col индекс столбца текущей клетки
     * @param grid сетка клеток
     * @param type {@link Cell.Type} для поиска соседей
     * @param step расстояние шага для соседей
     * @return список координат {@link Coordinate} соседних клеток указанного типа
     */
    private List<Coordinate> getNeighbors(int row, int col, Cell[][] grid, Cell.Type type, int step) {
        List<Coordinate> neighbors = new ArrayList<>();
        int height = grid.length;
        int width = grid[0].length;

        int[] dRows = {-step, step, 0, 0};
        int[] dCols = {0, 0, -step, step};

        for (int i = 0; i < dCols.length; i++) {
            int newRow = row + dRows[i];
            int newCol = col + dCols[i];
            if (0 <= newRow && newRow < height && 0 <= newCol && newCol < width) {
                if (grid[newRow][newCol].type() == type) {
                    neighbors.add(new Coordinate(newRow, newCol));
                }
            }
        }
        return neighbors;
    }
}
