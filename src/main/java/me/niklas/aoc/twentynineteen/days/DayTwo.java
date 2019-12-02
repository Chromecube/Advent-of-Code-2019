package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.NumberUtil;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

import java.util.Arrays;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class DayTwo implements AocSolution {


    @Override
    public boolean isSolved() {
        return false;
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
        String[] rawIn = input.split(",");
        int[] in = new int[rawIn.length];

        for (int i = 0; i < rawIn.length; i++) {
            in[i] = NumberUtil.tryParseInt(rawIn[i], -1);
        }

        //Make changes to recover state
        in[1] = 12;
        in[2] = 2;

        int[] backup = Arrays.copyOf(in, in.length);

        System.out.println("Read in " + in.length + " values, now executing the Intcode..");
        System.out.println("Current: " + in[0]);
        executeIntcode(in);
        System.out.println("Solved: " + in[0]); //5866714

        System.out.println("Brute force result: " + applyBruteForce(backup)); //5208
    }

    private int applyBruteForce(int[] codes) {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                int[] copy = Arrays.copyOf(codes, codes.length);
                copy[1] = noun;
                copy[2] = verb;
                executeIntcode(copy);
                if (copy[0] == 19690720) { //Solved!
                    System.out.println(String.format("Noun is %d, verb is %d.", noun, verb));
                    return (100 * noun) + verb;
                }
            }
        }
        System.out.println("No solution found!");
        return -1;
    }

    private void executeIntcode(int[] codes) {
        int pointer = 0;
        boolean done = false;
        while (pointer < codes.length && !done) {
            if (codes[pointer] == 1) { //Add numbers
                int sum = codes[codes[pointer + 1]] + codes[codes[pointer + 2]];
                codes[codes[pointer + 3]] = sum;
            } else if (codes[pointer] == 2) { //Multiply numbers
                int product = codes[codes[pointer + 1]] * codes[codes[pointer + 2]];
                codes[codes[pointer + 3]] = product;
            } else if (codes[pointer] == 99) done = true;
            else {
                System.err.println("Unknown operation " + codes[pointer]);
                break;
            }
            pointer += 4;
        }
    }
}
