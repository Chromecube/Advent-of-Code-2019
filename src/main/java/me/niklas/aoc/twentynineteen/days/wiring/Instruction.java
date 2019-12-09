package me.niklas.aoc.twentynineteen.days.wiring;

import me.niklas.aoc.twentynineteen.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 03.12.2019 in twentynineteen
 */
public class Instruction {

    private final Direction direction;
    private final int length;

    private Instruction(Direction direction, int length) {
        this.direction = direction;
        this.length = length;
    }

    public static Instruction parse(String input) {
        if (input == null || input.length() == 0) return null;
        char dir = input.charAt(0);
        Direction res;
        switch (dir) {
            case 'U':
                res = Direction.UP;
                break;
            case 'R':
                res = Direction.RIGHT;
                break;
            case 'D':
                res = Direction.DOWN;
                break;
            case 'L':
                res = Direction.LEFT;
                break;
            default:
                return null;
        }

        int length = NumberUtil.getInt(input.substring(1), -1);
        if (length < 0) return null;
        return new Instruction(res, length);
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }

    public int horizontal() {
        return direction == Direction.RIGHT ? length : direction == Direction.LEFT ? -length : 0;
    }

    public int vertical() {
        return direction == Direction.DOWN ? length : direction == Direction.UP ? -length : 0;
    }

    public List<Point> getPoints(Point start) {
        List<Point> result = new ArrayList<>();
        int h = Integer.compare(horizontal(), 0); //1, -1 or 0
        int v = Integer.compare(vertical(), 0);
        for (int i = 1; i <= length; i++) {
            result.add(start.copy().translate(h * i, v * i));
        }
        return result;
    }

    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
}
