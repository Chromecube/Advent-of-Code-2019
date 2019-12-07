package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.intcode.IntcodeComputer;
import me.niklas.aoc.twentynineteen.intcode.IntcodeResult;
import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.NumberUtil;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Niklas on 07.12.2019 in twentynineteen
 */
public class DaySeven implements AocSolution {

    private final IntcodeComputer executor = new IntcodeComputer();

    @Override
    public int getDay() {
        return 7;
    }

    @Override
    public String getName() {
        return "Amplification Circuit";
    }

    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public void solve() {
        int[] code = executor.parseString(ResourceUtil.readResource("dayseven", getClass()).get(0));

        int max = 0;
        int[] maxArr = new int[0];

        List<int[]> combinations = NumberUtil.getCombinations(5, new int[]{0, 1, 2, 3, 4});

        for (int[] arr : combinations) {
            int val = executeForSequence(code, arr);
            if (val > max) {
                max = val;
                maxArr = arr;
            }
        }

        System.out.println("PART ONE: " + max + " for " + Arrays.toString(maxArr)); //338603

        List<int[]> feedbackCombinations = NumberUtil.getCombinations(5, new int[]{5, 6, 7, 8, 9});
        max = 0;
        maxArr = new int[0];

        for (int[] arr : feedbackCombinations) {
            int val = executeForAmplifiers(code, arr);
            if (val > max) {
                max = val;
                maxArr = arr;
            }
        }
        System.out.println("PART TWO: " + max + " for " + Arrays.toString(maxArr));
    }

    private int executeForSequence(int[] code, int[] in) {
        int last = 0;
        for (int value : in) {
            IntcodeResult out = executor.execute(code, value, last); //Execute for each input
            last = out.outputs[0];
        }
        return last;
    }

    private int executeForAmplifiers(int[] code, int[] in) {

        IntcodeComputer[] executors = new IntcodeComputer[in.length];

        for (int e = 0; e < executors.length; e++) {
            executors[e] = new IntcodeComputer();
            executors[e].loadProgram(code, in[e]);
        }

        int i = 0;
        int last = 0;
        while (!executors[in.length - 1].isHalted()) {
            if (i == in.length) i = 0;
            executors[i].addInput(last); //Add last result as input
            executors[i].continueUntilOutput(); //Run the program until a new value is generated

            last = executors[i].getOutput().get(executors[i].getOutput().size() - 1); //Extract output
            i++;
        }
        //Return the result from the last amplifier
        return executors[in.length - 1].getOutput().get(executors[in.length - 1].getOutput().size() - 1);
    }
}
