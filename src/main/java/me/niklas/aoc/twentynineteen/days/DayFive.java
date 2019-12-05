package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.intcode.IntcodeComputer;
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

        System.out.println("Type 1 for part one, 5 for part two.");
        //ONE: 15314507, TWO: 652726
        executor.execute(ResourceUtil.readResource("dayfive", getClass()).get(0));



        /* -------------- TESTS ----------------
        executor.executeIntcode("3,9,8,9,10,9,4,9,99,-1,8", 8); //1 (==8)
        executor.executeIntcode("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 0); //0 (==0)
        String in = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        executor.executeIntcode(in, 7);     //999  (<8)
        executor.executeIntcode(in, 8);     //1000 (==8)
        executor.executeIntcode(in, 500);   //1001 (>8)*/
    }
}
