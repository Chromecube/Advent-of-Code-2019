package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.util.AocSolution;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class ExitDay implements AocSolution {
    @Override
    public boolean isSolved() {
        return false;
    }

    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public void solve() {
        System.exit(0);
    }

    @Override
    public int getDay() {
        return 0;
    }
}
