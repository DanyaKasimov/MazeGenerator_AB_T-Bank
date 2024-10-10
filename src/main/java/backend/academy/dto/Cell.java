package backend.academy.dto;

import backend.academy.constants.Config;
import java.util.Random;

@SuppressWarnings("ReturnCount")
public record Cell(int row, int col, Type type, Surface surface) {

    public enum Type {
        WALL,
        PASSAGE
    }

    public enum Surface {
        NORMAL,
        SWAMP,
        SAND,
        COIN,
        GOOD_FLOOR;

        public static Surface randomSurface(Random random) {
            double chance = random.nextDouble();
            if (chance < Config.SWAMP_PROBABILITY) {
                return Surface.SWAMP;
            } else if (chance < Config.SWAMP_PROBABILITY + Config.SAND_PROBABILITY) {
                return Surface.SAND;
            } else if (chance < Config.SWAMP_PROBABILITY + Config.SAND_PROBABILITY + Config.COIN_PROBABILITY) {
                return Surface.COIN;
            } else if (chance < Config.SWAMP_PROBABILITY + Config.SAND_PROBABILITY
                + Config.COIN_PROBABILITY + Config.GOOD_TRACK_PROBABILITY) {
                return Surface.GOOD_FLOOR;
            }
            return Surface.NORMAL;
        }

        public static int surfaceCost(Surface surface) {
            return switch (surface) {
                case SWAMP -> Config.SWAMP_COST;
                case SAND -> Config.SAND_COST;
                case COIN -> Config.COIN_COST;
                case GOOD_FLOOR -> Config.GOOD_TRACK_COST;
                default -> Config.NORMAL_COST;
            };
        }
    }

    public Cell(int row, int col, Type type) {
        this(row, col, type, Surface.NORMAL);
    }

}
