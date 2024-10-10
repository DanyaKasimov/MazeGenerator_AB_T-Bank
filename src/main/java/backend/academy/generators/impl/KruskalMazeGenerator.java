package backend.academy.generators.impl;

import backend.academy.dto.Cell;
import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import backend.academy.generators.Generator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Алгоритм Краскала для генерации минимального остовного дерева.
 * Лабиринт представляет собой сетку, где стены размещены на чётных строках и столбцах,
 * а проходы на нечётных. Лабиринт состоит из множества непересекающихся множеств,
 * которые соединяются рёбрами, пока не будет образовано единственное связное множество.
 * <p>
 * Лабиринт создаётся по следующей логике:
 * <ul>
 *     <li>Инициализируется сетка с клетками-стенами и клетками-проходами.</li>
 *     <li>Клетки проходы добавляются в одни множества, а рёбра в другие.</li>
 *     <li>Рёбра случайным образом перемешиваются и соединяют множества, пока не останется одно множество.</li>
 *     <li>Переходы между соседними клетками образуются, если эти клетки принадлежат разным множествам.</li>
 * </ul>
 * Поверхности клеток проходов задаются случайно, с различными типами (обычные, болота, монеты и хорошие покрытия.),
 * которые могут влиять на прохождение пути.
 */
public class KruskalMazeGenerator implements Generator {

    /**
     * Генерирует лабиринт с указанными высотой и шириной.
     *
     * @param heightP высота лабиринта (если чётное число, будет увеличено на 1).
     * @param widthP  ширина лабиринта (если чётное число, будет увеличено на 1).
     * @return объект {@link Maze}, представляющий сгенерированный лабиринт.
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
        List<Edge> edges = new ArrayList<>();
        Map<Coordinate, Set<Coordinate>> sets = new HashMap<>();
        Random random = new Random();

        // Инициализация сетки с клетками-стенами и проходами
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row % 2 == 0 || col % 2 == 0) {
                    grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE, Cell.Surface.randomSurface(random));
                    Coordinate coord = new Coordinate(row, col);
                    sets.put(coord, new HashSet<>(Collections.singletonList(coord)));

                    // Добавление рёбер между соседними клетками
                    if (row + 2 < height) {
                        edges.add(new Edge(coord, new Coordinate(row + 2, col)));
                    }
                    if (col + 2 < width) {
                        edges.add(new Edge(coord, new Coordinate(row, col + 2)));
                    }
                }
            }
        }

        // Перемешиваем рёбра и соединяем клетки
        Collections.shuffle(edges);
        for (Edge edge : edges) {
            Coordinate cell1 = edge.cell1();
            Coordinate cell2 = edge.cell2();

            Set<Coordinate> set1 = sets.get(cell1);
            Set<Coordinate> set2 = sets.get(cell2);

            // Если клетки принадлежат разным множествам, соединяем их
            if (!set1.equals(set2)) {
                int betweenRow = (cell1.row() + cell2.row()) / 2;
                int betweenCol = (cell1.col() + cell2.col()) / 2;
                grid[betweenRow][betweenCol] =
                    new Cell(betweenRow, betweenCol, Cell.Type.PASSAGE, Cell.Surface.randomSurface(random));

                set1.addAll(set2);
                for (Coordinate coord : set2) {
                    sets.put(coord, set1);
                }
            }
        }

        return new Maze(height, width, grid);
    }

    /**
     * Вспомогательный класс для представления рёбер между клетками.
     */
    private record Edge(Coordinate cell1, Coordinate cell2) {}
}
