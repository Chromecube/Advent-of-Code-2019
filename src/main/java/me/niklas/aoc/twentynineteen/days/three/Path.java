package me.niklas.aoc.twentynineteen.days.three;

import me.niklas.aoc.twentynineteen.util.Timer;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Niklas on 03.12.2019 in twentynineteen
 */
public class Path {

    private final List<Instruction> instructions = new ArrayList<>();

    public void readInstruction(String instruction) {
        if (instruction.contains(",")) {
            readInstructions(instruction.split(","));
            return;
        }

        Instruction next = Instruction.parse(instruction);
        if (next == null) System.out.println("WARN: " + instruction + " returns null as parsed input");
        else instructions.add(next);
    }

    private void readInstructions(String... instructions) {
        for (String instruction : instructions) {
            readInstruction(instruction);
        }
    }

    public void solveBothParts(Path other) {
        List<Point> otherPoints = other.getAllPoints();
        List<Point> myPoints = getAllPoints();

        Timer t = new Timer();
        System.out.println(String.format("Now merging the paths (%d objects), this can take a long time!",
                otherPoints.size() + myPoints.size())); //40-60s on my machine
        List<Point> intersections = myPoints.stream().filter(item -> !item.equals(Point.ZERO)).filter(otherPoints::contains).collect(Collectors.toList());
        System.out.println("Needed " + t.stop() + "ms find intersections");

        intersections.sort(Comparator.comparingInt(Point::dist));

        System.out.println("SOLUTION PART ONE: " + intersections.get(0).dist()); //557

        Map<Integer, Point> distances = new HashMap<>();
        intersections.forEach(i -> {
            int my = myPoints.indexOf(i);
            int ot = otherPoints.indexOf(i);
            distances.put(my + ot, i);
        });

        AtomicInteger lowestValue = new AtomicInteger(Integer.MAX_VALUE);
        List<Integer> items = new ArrayList<>(distances.keySet());
        items.sort(Integer::compareTo);
        System.out.println("SOLUTION PART TWO: " + items.get(0)); //56410
    }

    public List<Point> getAllPoints() {
        List<Point> result = new ArrayList<>();

        result.add(new Point(0, 0));

        for (Instruction instruction : instructions) {
            result.addAll(instruction.getPoints(result.get(result.size() - 1)));
        }

        return result;
    }
}
