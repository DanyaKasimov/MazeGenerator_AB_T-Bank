package backend.academy.solvers.impl;

import backend.academy.dto.Cell;
import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import backend.academy.solvers.Solver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Реализация интерфейса {@link Solver} с использованием алгоритма поиска в ширину (BFS).
 * <p>
 * Этот класс предоставляет метод для нахождения кратчайшего пути от начальной координаты до конечной
 * в лабиринте, учитывая стоимость перемещения по различным типам поверхностей ячеек.
 * </p>
 */
public class BFSSolverImpl implements Solver {

    /**
     * Решает лабиринт с помощью алгоритма BFS для нахождения кратчайшего пути от {@code start} до {@code end}.
     * <p>
     * Этот метод ищет путь с наименьшим количеством шагов, игнорируя стоимость перемещения через поверхности ячеек.
     * Он использует очередь для исследования соседних ячеек в порядке ширины и поддерживает массивы для отслеживания
     * посещенных ячеек и их предшественников.
     * </p>
     *
     * @param maze  лабиринт {@link Maze} для решения
     * @param start начальная координата {@link Coordinate}
     * @param end   конечная координата {@link Coordinate}
     * @return список {@link Coordinate}, представляющий кратчайший путь от {@code start} до {@code end},
     *         или пустой список, если путь не существует
     */
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.height();
        int width = maze.width();
        Cell[][] grid = maze.grid();

        boolean[][] visited = new boolean[height][width];
        Coordinate[][] predecessors = new Coordinate[height][width];

        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        visited[start.row()][start.col()] = true;

        // Возможные перемещения: вверх, вниз, влево, вправо
        int[] dRows = {-1, 1, 0, 0};
        int[] dCols = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            // Проверяем, достигли ли конечной координаты
            if (current.equals(end)) {
                List<Coordinate> path = new ArrayList<>();
                Coordinate step = end;
                // Восстанавливаем путь, следуя по предшественникам
                while (step != null) {
                    path.add(step);
                    step = predecessors[step.row()][step.col()];
                }
                Collections.reverse(path);
                return path;
            }

            // Исследуем соседние ячейки
            for (int i = 0; i < dCols.length; i++) {
                int newRow = current.row() + dRows[i];
                int newCol = current.col() + dCols[i];

                // Проверяем границы лабиринта и тип ячейки
                if (0 <= newRow && newRow < height && 0 <= newCol && newCol < width) {
                    if (!visited[newRow][newCol] && grid[newRow][newCol].type() == Cell.Type.PASSAGE) {
                        visited[newRow][newCol] = true;
                        predecessors[newRow][newCol] = current;
                        queue.add(new Coordinate(newRow, newCol));
                    }
                }
            }
        }

        // Возвращаем пустой список, если путь не найден
        return Collections.emptyList();
    }
}
