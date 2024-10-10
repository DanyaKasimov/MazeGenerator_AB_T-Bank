package backend.academy.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneratorType implements Describable {
    PRIM("Алгоритм Прима"),
    KRUSKAL("Алгоритм Краскала");

    private final String description;
}
