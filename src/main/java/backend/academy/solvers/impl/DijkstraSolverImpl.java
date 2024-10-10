package backend.academy.solvers.impl;

import backend.academy.dto.Cell;
import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import backend.academy.solvers.Solver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Реализация интерфейса {@link Solver} с использованием алгоритма Дейкстры.
 * <p>
 * Этот класс предоставляет метод для нахождения пути с наименьшей общей стоимостью от начальной координаты до конечной
 * в лабиринте, учитывая стоимость перемещения по различным типам поверхностей ячеек.
 * </p>
 */
public class DijkstraSolverImpl implements Solver {

    /**
     * Находит путь с наименьшей стоимостью от {@code start} до {@code end}.
     * <p>
     * Использует очередь с приоритетом для выбора следующей клетки с наименьшей накопленной стоимостью.
     * Стоимость перемещения через каждую ячейку определяется типом её поверхности.
     * </p>
     *
     * @param maze лабиринт {@link Maze} для решения
     * @param start начальная координата {@link Coordinate}
     * @param end конечная координата {@link Coordinate}
     * @return список {@link Coordinate}, представляющий путь с наименьшей стоимостью от {@code start} до {@code end},
     * или пустой список, если путь не существует
     */
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.height();
        int width = maze.width();
        Cell[][] grid = maze.grid();

        // Таблица расстояний до каждой клетки
        Map<Coordinate, Integer> distances = new HashMap<>();
        // Хранение родительских узлов для восстановления пути
        Map<Coordinate, Coordinate> predecessors = new HashMap<>();
        // Множество посещённых вершин
        Set<Coordinate> visited = new HashSet<>();
        // Очередь с приоритетом (наименьший путь имеет приоритет)
        PriorityQueue<CoordinateDistancePair> priorityQueue =
            new PriorityQueue<>(Comparator.comparingInt(pair -> pair.distance));

        // Инициализация: все расстояния огромные, кроме стартовой точки
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Coordinate coord = new Coordinate(row, col);
                distances.put(coord, Integer.MAX_VALUE); // огромное расстояние
            }
        }
        distances.put(start, 0); // Начальная точка с расстоянием 0
        priorityQueue.add(new CoordinateDistancePair(start, 0));

        // Пока очередь не пуста
        while (!priorityQueue.isEmpty()) {
            CoordinateDistancePair currentPair = priorityQueue.poll();
            Coordinate current = currentPair.coordinate;

            // Если текущая точка уже посещена, пропускаем
            if (visited.contains(current)) {
                continue;
}
            // Если дошли до конечной точки, восстанавливаем путь
            if (current.equals(end)) {
                return reconstructPath(predecessors, start, end);
            }

            // Помечаем текущую вершину как посещённую
            visited.add(current);

            // Обрабатываем соседей
            for (Coordinate neighbor : getNeighbors(current, grid)) {
                if (!visited.contains(neighbor)) {
                    int newDist = distances.get(current)
                        + Cell.Surface.surfaceCost(grid[neighbor.row()][neighbor.col()].surface());
                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        predecessors.put(neighbor, current);
                        priorityQueue.add(new CoordinateDistancePair(neighbor, newDist));
                    }
                }
            }
        }

        // Если путь не найден, возвращаем пустой список
        return Collections.emptyList();
    }

    /**
     * Восстанавливает путь от {@code start} до {@code end}, используя информацию о предшественниках.
     *
     * @param predecessors карта предшественников для каждой координаты
     * @param start начальная координата {@link Coordinate}
     * @param end конечная координата {@link Coordinate}
     * @return список {@link Coordinate}, представляющий путь от {@code start} до {@code end}
     */
    private List<Coordinate> reconstructPath(
        Map<Coordinate, Coordinate> predecessors,
        Coordinate start,
        Coordinate end
    ) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate step = end;
        while (!step.equals(start)) {
            path.add(step);
            step = predecessors.get(step);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    /**
     * Возвращает список соседних координат для данной координаты, которые являются допустимыми для перемещения.
     *
     * @param current текущая координата {@link Coordinate}
     * @param grid двумерный массив ячеек {@link Cell} лабиринта
     * @return список допустимых соседних координат {@link Coordinate}
     */
    private List<Coordinate> getNeighbors(Coordinate current, Cell[][] grid) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        for (int i = 0; i < dCol.length; i++) {
            int newRow = current.row() + dRow[i];
            int newCol = current.col() + dCol[i];
            if (isValid(newRow, newCol, grid)) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }
        return neighbors;
    }

    /**
     * Проверяет, является ли координата с заданными строкой и столбцом допустимой для перемещения.
     * Координата считается допустимой, если она находится внутри границ лабиринта и является проходом.
     *
     * @param row номер строки координаты
     * @param col номер столбца координаты
     * @param grid двумерный массив ячеек {@link Cell} лабиринта
     * @return {@code true}, если координата допустима для перемещения; {@code false} в противном случае
     */
    private boolean isValid(int row, int col, Cell[][] grid) {
        return row >= 0 && row < grid.length
            && col >= 0 && col < grid[0].length
            && grid[row][col].type() == Cell.Type.PASSAGE;
    }

    /**
     * Класс для хранения пары координаты и соответствующего расстояния до неё.
     */
    private record CoordinateDistancePair(Coordinate coordinate, int distance) {
    }
}
