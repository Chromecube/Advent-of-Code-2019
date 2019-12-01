package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

import java.util.List;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class DayOne implements AocSolution {
    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public String getName() {
        return "The Tyranny of the Rocket Equation";
    }

    @Override
    public int getDay() {
        return 1;
    }

    @Override
    public void solve() {
        //1: Read resource
        List<Long> moduleWeights = ResourceUtil.readResourceAsLong("dayone", getClass());

        //2: Check whether the input was parsed correctly.
        if (moduleWeights.size() != 100) {
            System.err.println("The number of read in values should be 100.");
            return;
        }
        ;

        //3: Calculate results
        System.out.println("Required fuel: " + calcFuel(moduleWeights, false)); //3345909
        System.out.println("+ extra fuel: " + calcFuel(moduleWeights, true)); //5015983
    }

    private long calcFuel(List<Long> modules, boolean includeFuelWeight) {
        long result = 0L;

        for (long number : modules) {
            long moduleFuel = calcFuel(number);
            result += moduleFuel;

            while (includeFuelWeight && (moduleFuel = calcFuel(moduleFuel)) > 0) { //Calculate until fuel weight is under minimum
                result += moduleFuel;
            }
        }

        return result;
    }

    private long calcFuel(long weight) { //Java rounds down by default.
        return weight / 3L - 2L;
    }
}
