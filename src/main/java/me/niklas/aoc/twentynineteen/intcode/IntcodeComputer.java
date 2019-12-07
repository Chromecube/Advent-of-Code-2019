package me.niklas.aoc.twentynineteen.intcode;

import me.niklas.aoc.twentynineteen.util.NumberUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static me.niklas.aoc.twentynineteen.intcode.IntcodeInstruction.Mode.POSITION;
import static me.niklas.aoc.twentynineteen.intcode.IntcodeInstruction.Opcode.*;

/**
 * Created by Niklas on 05.12.2019 in twentynineteen
 */
public class IntcodeComputer {

    private final Queue<Integer> inputQueue = new LinkedBlockingQueue<>(1024);
    private final List<Integer> output = new ArrayList<>();
    private boolean halted = false;
    private int[] memory = new int[0];
    private int pointer = 0;

    public int[] parseString(String input) {
        String[] rawIn = input.split(",");
        int[] in = new int[rawIn.length];

        for (int i = 0; i < rawIn.length; i++) {
            in[i] = NumberUtil.tryParseInt(rawIn[i], -1);
        }
        return in;
    }

    public IntcodeResult execute(String input, int... inputs) {
        return execute(parseString(input), inputs);
    }

    public void loadProgram(int[] code, int... inputs) {
        inputQueue.clear();
        addInput(inputs);
        output.clear();
        pointer = 0;
        memory = Arrays.copyOf(code, code.length);
        halted = false;
    }

    public void addInput(int... input) {
        for (int i : input) {
            inputQueue.offer(i);
        }
    }

    public IntcodeResult execute(int[] code, int... inputs) {
        loadProgram(code, inputs);

        while (!halted) {
            runNext();
        }

        //Collect output
        int[] out = new int[output.size()];

        for (int i = 0; i < out.length; i++) {
            out[i] = output.get(i);
        }

        return new IntcodeResult(memory, out);
    }

    public void continueUntilOutput() {
        int size = output.size();

        while (!halted && size == output.size()) {
            runNext();
        }
    }

    public List<Integer> getOutput() {
        return output;
    }

    private void runNext() {
        if (pointer >= memory.length || pointer < 0) {
            System.err.println("Pointer out of memory region, value is " + pointer + " memory size is " + memory.length);
            System.exit(1);
        }
        if (halted) {
            System.err.println("The program has already stopped. Please load another one.");
            return;
        }
        IntcodeInstruction in = readInstruction(memory[pointer]);

        if (in.getOpcode() == ADD) { //Add numbers
            int first = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
            int second = in.getSecond() == POSITION ? memory[memory[pointer + 2]] : memory[pointer + 2];

            int sum = first + second;

            if (in.getThird() == POSITION) memory[memory[pointer + 3]] = sum;
            else memory[pointer + 3] = sum;
        } else if (in.getOpcode() == MULTIPLY) { //Multiply numbers
            int first = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
            int second = in.getSecond() == POSITION ? memory[memory[pointer + 2]] : memory[pointer + 2];

            int product = first * second;

            if (in.getThird() == POSITION) memory[memory[pointer + 3]] = product;
            else memory[pointer + 3] = product;

        } else if (in.getOpcode() == INPUT) {
            int loc = in.getFirst() == POSITION ? memory[pointer + 1] : pointer + 1;
            if (inputQueue.size() > 0) {
                memory[loc] = inputQueue.poll();
            } else {
                System.err.println("Reached end of input queue");
                System.exit(1);
            }
        } else if (in.getOpcode() == OUTPUT) {
            int out = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
            output.add(out);

        } else if (in.getOpcode() == JUMP_IF_TRUE) {
            int value = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
            int jump = in.getSecond() == POSITION ? memory[memory[pointer + 2]] : memory[pointer + 2];

            if (value != 0) {
                pointer = jump;
                return;
            }

        } else if (in.getOpcode() == JUMP_IF_FALSE) {
            int value = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
            int jump = in.getSecond() == POSITION ? memory[memory[pointer + 2]] : memory[pointer + 2];

            if (value == 0) {
                pointer = jump;
                return;
            }

        } else if (in.getOpcode() == LESS_THAN) {
            int first = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
            int second = in.getSecond() == POSITION ? memory[memory[pointer + 2]] : memory[pointer + 2];

            int result = first < second ? 1 : 0;

            if (in.getThird() == POSITION) memory[memory[pointer + 3]] = result;
            else memory[pointer + 3] = result;

        } else if (in.getOpcode() == EQUALS) {
            int first = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
            int second = in.getSecond() == POSITION ? memory[memory[pointer + 2]] : memory[pointer + 2];

            int result = first == second ? 1 : 0;

            if (in.getThird() == POSITION) memory[memory[pointer + 3]] = result;
            else memory[pointer + 3] = result;

        } else if (in.getOpcode() == END) {
            halted = true;
        } else if (in.getOpcode() == UNKNOWN) {
            System.err.println("Unknown operation " + memory[pointer] + " at position " + pointer);
            System.exit(1);
        } else {
            System.err.println("Opcode " + in.getOpcode() + " is not implemented.");
        }
        pointer += in.nextOffset();
    }

    private IntcodeInstruction readInstruction(int input) {
        return new IntcodeInstruction(NumberUtil.getNumberDigits(input, 5));
    }

    public boolean isHalted() {
        return halted;
    }
}
