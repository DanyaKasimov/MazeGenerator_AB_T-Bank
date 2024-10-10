package backend.academy.dto;

import backend.academy.constants.enums.GeneratorType;
import backend.academy.constants.enums.SolverType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameters {
    private int height;

    private int width;

    private GeneratorType generatorType;

    private SolverType solverType;
}
