package me.niklas.aoc.twentynineteen.days.three;

import java.util.Objects;

/**
 * Created by Niklas on 03.12.2019 in twentynineteen
 */
public class Point {

    public static final Point ZERO = new Point(0, 0);

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int dist(Point other) {
        return Math.abs(other.x - x) + Math.abs(other.y - y);
    }

    public int dist() {
        return dist(Point.ZERO);
    }

    public Point translate(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Point copy() {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return String.format("{x: %d, y: %d}", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
