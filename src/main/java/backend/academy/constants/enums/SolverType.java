package backend.academy.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SolverType implements Describable {
    BFS("Алгоритм BFS"),
    Dijkstra("Алгоритм Дейкстры");

    private final String description;
}
