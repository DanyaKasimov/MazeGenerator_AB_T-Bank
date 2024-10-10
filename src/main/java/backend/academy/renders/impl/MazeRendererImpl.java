package backend.academy.renders.impl;

import backend.academy.constants.InterfaceTemplates;
import backend.academy.dto.Cell;
import backend.academy.dto.Coordinate;
import backend.academy.dto.Maze;
import backend.academy.renders.Renderer;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса {@link Renderer} для рендеринга лабиринта в строковое представление.
 * Этот класс отвечает за преобразование объекта {@link Maze} в строку,
 * используя специальные стили для отображения различных элементов лабиринта:
 * <ul>
 *     <li>Стены</li>
 *     <li>Проходы</li>
 *     <li>Пути</li>
 *     <li>Болота (Ухудшающее покрытие)</li>
 *     <li>Монетки (Улучшающее покрытие)</li>
 *     <li>Песок (Ухудшающее покрытие)</li>
 *     <li>Хорошие покрытия (Улучшающее покрытие)</li>
 *     <li>Обычные покрытия</li>
 * </ul>
 * Лабиринт отображается с нумерацией строк и столбцов для удобства навигации.
 * Если задан путь, то он отображается поверх лабиринта с выделением стартовой и конечной точек.
 * <br/>
 * Поверхности клеток проходов отображаются с использованием различных цветов и символов,
 * представляя разные типы поверхностей: обычные, болота, монеты и хорошие покрытия.
 */
public class MazeRendererImpl implements Renderer {

    /**
     * Рендерит лабиринт без отображения пути.
     *
     * @param maze объект {@link Maze} для рендеринга
     * @return строковое представление лабиринта
     */
    @Override
    public String render(Maze maze) {
        return render(maze, null);
    }

    /**
     * Рендерит лабиринт с возможностью отображения пути.
     * Путь отображается, если передан список координат {@link Coordinate}.
     * Стартовая и конечная точки пути выделяются отдельными символами "S" и "E".
     *
     * @param maze объект {@link Maze} для рендеринга
     * @param path список координат {@link Coordinate}, представляющих путь в лабиринте
     * @return строковое представление лабиринта с отображением пути
     */
    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();
        Cell[][] grid = maze.grid();

        // Преобразуем путь в множество для быстрого доступа
        Set<Coordinate> pathSet = path != null ? new HashSet<>(path) : Collections.emptySet();

        // Отображаем номера столбцов
        sb.append("    ");

        String format = "%2d ";
        for (int col = 0; col < maze.width(); col++) {
            sb.append(String.format(format, col));
        }
        sb.append("\n");

        // Проходим по каждой строке и столбцу сетки
        for (int row = 0; row < maze.height(); row++) {
            // Отображаем номер строки
            sb.append(String.format(format, row));

            for (int col = 0; col < maze.width(); col++) {
                Cell cell = grid[row][col];
                Coordinate coord = new Coordinate(row, col);

                // Проверяем, является ли текущая клетка частью пути
                if (pathSet.contains(coord)) {
                    if (coord.equals(path.getFirst())) {
                        // Стартовая точка пути
                        sb.append(InterfaceTemplates.START);
                    } else if (coord.equals(path.getLast())) {
                        // Конечная точка пути
                        sb.append(InterfaceTemplates.END);
                    } else {
                        // Промежуточные точки пути
                        sb.append(InterfaceTemplates.PATH);
                    }
                } else if (cell.type() == Cell.Type.WALL) {
                    // Отображаем стену
                    sb.append(InterfaceTemplates.WALL);
                } else {
                    // Отображаем поверхность клетки
                    sb.append(renderSurface(cell.surface()));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Приватный метод для рендеринга поверхности клетки на основе ее типа.
     * Каждая поверхность отображается с использованием определенного цвета и символа.
     *
     * @param surface тип поверхности клетки {@link Cell.Surface}
     * @return строковое представление клетки с соответствующим стилем
     */
    private String renderSurface(Cell.Surface surface) {
        return switch (surface) {
            case SWAMP -> InterfaceTemplates.SWAMP;
            case SAND -> InterfaceTemplates.SAND;
            case COIN -> InterfaceTemplates.COIN;
            case GOOD_FLOOR -> InterfaceTemplates.GOOD_FLOOR;
            default -> InterfaceTemplates.PASSAGE;
        };
    }
}
