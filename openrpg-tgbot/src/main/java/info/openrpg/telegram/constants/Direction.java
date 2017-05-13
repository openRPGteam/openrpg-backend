package info.openrpg.telegram.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction {
    TOP(0, 1),
    TOP_RIGHT(1, 1),
    RIGHT(1, 0),
    BOTTOM_RIGHT(1, -1),
    BOTTOM(0, -1),
    BOTTOM_LEFT(-1, -1),
    LEFT(-1, 0),
    TOP_LEFT(-1, 1),;

    private final int x;
    private final int y;
}
