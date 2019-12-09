package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.days.intcode.IntcodeComputer;
import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

/**
 * Created by Niklas on 05.12.2019 in twentynineteen
 */
public class DayFive implements AocSolution {
    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public String getName() {
        return "Sunny with a Chance of Asteroids";
    }

    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public void solve() {
        IntcodeComputer executor = new IntcodeComputer();

        long[] code = executor.parseString(ResourceUtil.readResource("dayfive", getClass()).get(0));

        System.out.println("PART ONE: " + executor.execute(code, 1).lastOutput()); //15314507
        System.out.println("PART TWO: " + executor.execute(code, 5).lastOutput()); //652726

    }
}
