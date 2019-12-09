package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.days.intcode.IntcodeComputer;
import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

import java.util.Arrays;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class DayTwo implements AocSolution {


    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public String getName() {
        return "1202 Program Alarm";
    }

    @Override
    public int getDay() {
        return 2;
    }

    @Override
    public void solve() {
        String input = ResourceUtil.readResource("daytwo", getClass()).get(0);

        IntcodeComputer executor = new IntcodeComputer();
        long[] code = executor.parseString(input);


        //Make changes to recover state
        code[1] = 12;
        code[2] = 2;

        System.out.println("Solved: " + executor.execute(code).memory[0]); //5866714
        System.out.println("Brute force result: " + applyBruteForce(code)); //5208
    }

    private int applyBruteForce(long[] codes) {
        IntcodeComputer executor = new IntcodeComputer();
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                long[] copy = Arrays.copyOf(codes, codes.length);
                copy[1] = noun;
                copy[2] = verb;
                if (executor.execute(copy).memory[0] == 19690720) { //Solved!
                    System.out.println(String.format("Noun is %d, verb is %d.", noun, verb));
                    return (100 * noun) + verb;
                }
            }
        }
        System.out.println("No solution found!");
        return -1;
    }
}
