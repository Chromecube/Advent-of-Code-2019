package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.days.intcode.IntcodeComputer;
import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

/**
 * Created by Niklas on 09.12.2019 in twentynineteen
 */
public class DayNine implements AocSolution {
    @Override
    public int getDay() {
        return 9;
    }

    @Override
    public String getName() {
        return "Sensor Boost";
    }

    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public void solve() {
        IntcodeComputer computer = new IntcodeComputer();
        long[] code = computer.parseString(ResourceUtil.readResource("daynine", getClass()).get(0));

        computer.loadProgram(code, 1);
        computer.run();
        System.out.println("PART ONE: " + computer.getLastOutput()); //3497884671

        computer.loadProgram(code, 2);
        computer.run();
        System.out.println("PART TWO: " + computer.getLastOutput()); //46470
    }
}
