package backend.academy.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InterfaceTemplates {

    public static final String START = Styles.RED_BACKGROUND + Styles.BLACK_TEXT + " S " + Styles.RESET;

    public static final String END = Styles.RED_BACKGROUND + Styles.BLACK_TEXT + " E " + Styles.RESET;

    public static final String NULL = "   ";

    public static final String PATH = Styles.PURPLE_BACKGROUND + " . " + Styles.RESET;

    public static final String WALL = Styles.BLACK_BACKGROUND + NULL + Styles.RESET;

    public static final String SWAMP = Styles.GREEN_BACKGROUND + Styles.BLACK_TEXT + " ~ " + Styles.RESET;

    public static final String SAND = Styles.YELLOW_BACKGROUND + NULL + Styles.RESET;

    public static final String COIN = Styles.WHITE_BACKGROUND + Styles.YELLOW_TEXT + " $ " + Styles.RESET;

    public static final String GOOD_FLOOR = Styles.CYAN_BACKGROUND + NULL + Styles.RESET;

    public static final String PASSAGE = Styles.WHITE_BACKGROUND + NULL + Styles.RESET;

    public static final String FLOOR_INFO = SWAMP + ": Болото" + Styles.RESET + "\n"
                                            + SAND + ": Песок" + Styles.RESET + "\n"
                                            + COIN + ": Монетка" + Styles.RESET + "\n"
                                            + GOOD_FLOOR + ": Хорошее покрытие" + Styles.RESET + "\n"
                                            + PASSAGE + ": Проход" + Styles.RESET + "\n"
                                            + WALL + ": Стена" + Styles.RESET + "\n";


}
