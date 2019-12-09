package me.niklas.aoc.twentynineteen.days.intcode;

import me.niklas.aoc.twentynineteen.util.NumberUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static me.niklas.aoc.twentynineteen.days.intcode.IntcodeInstruction.Mode.IMMEDIATE;
import static me.niklas.aoc.twentynineteen.days.intcode.IntcodeInstruction.Mode.POSITION;
import static me.niklas.aoc.twentynineteen.days.intcode.IntcodeInstruction.Opcode.*;

/**
 * Created by Niklas on 05.12.2019 in twentynineteen
 */
public class IntcodeComputer {

    private final Queue<Integer> inputQueue = new LinkedBlockingQueue<>(1024);
    private final List<Integer> output = new ArrayList<>();
    private boolean halted = false;
    private int[] memory = new int[0];
    private int pointer = 0;
    private int relative = 0;
    private boolean debugMode;

    public IntcodeComputer() {
        this(false);
    }

    public IntcodeComputer(boolean debugMode) {
        this.debugMode = debugMode;
    }

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
        relative = 0;
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
        log(String.format("Current operation is %d at %d, opcode: %s", memory[pointer], pointer, in.getOpcode()));

        if (in.getOpcode() == ADD) { //Add numbers
            int first = getValue(1, in.getFirst());
            int second = getValue(2, in.getSecond());

            int sum = first + second;

            setValue(3, in.getThird(), sum);
        } else if (in.getOpcode() == MULTIPLY) { //Multiply numbers
            int first = getValue(1, in.getFirst());
            int second = getValue(2, in.getSecond());

            int product = first * second;

            setValue(3, in.getThird(), product);
        } else if (in.getOpcode() == INPUT) {
            int loc = getValue(1, in.getFirst(), true);
            if (inputQueue.size() > 0) {
                memory[loc] = inputQueue.poll();
            } else {
                System.err.println("Reached end of input queue");
                System.exit(1);
            }
        } else if (in.getOpcode() == OUTPUT) {
            int out = getValue(1, in.getFirst());
            output.add(out);

        } else if (in.getOpcode() == JUMP_IF_TRUE) {
            int value = getValue(1, in.getFirst());
            int jump = getValue(2, in.getSecond());

            if (value != 0) {
                pointer = jump;
                return;
            }
        } else if (in.getOpcode() == JUMP_IF_FALSE) {
            int value = getValue(1, in.getFirst());
            int jump = getValue(2, in.getSecond());

            if (value == 0) {
                pointer = jump;
                return;
            }

        } else if (in.getOpcode() == LESS_THAN) {
            int first = getValue(1, in.getFirst());
            int second = getValue(2, in.getSecond());

            int result = first < second ? 1 : 0;

            setValue(3, in.getThird(), result);
        } else if (in.getOpcode() == EQUALS) {
            int first = getValue(1, in.getFirst());
            int second = getValue(2, in.getSecond());

            int result = first == second ? 1 : 0;

            setValue(3, in.getThird(), result);

        } else if (in.getOpcode() == RELATIVE_BASE) {
            relative += getValue(1, in.getFirst());
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

    private int getValue(int offset, IntcodeInstruction.Mode mode, boolean adressOnly) {
        if (adressOnly) {
            return mode == POSITION ? memory[pointer + offset] :
                    mode == IMMEDIATE ? pointer + offset :
                            relative + memory[pointer + offset];
        }
        return mode == POSITION ? memory[memory[pointer + offset]] :
                mode == IMMEDIATE ? memory[pointer + offset] :
                        memory[relative + memory[pointer + offset]];
    }

    public int getValue(int offset, IntcodeInstruction.Mode mode) {
        return getValue(offset, mode, false);
    }

    public void setValue(int offset, IntcodeInstruction.Mode mode, int value) {
        if (mode == POSITION) memory[memory[pointer + offset]] = value;
        else if (mode == IMMEDIATE) memory[pointer + offset] = value;
        else memory[relative + memory[pointer + offset]] = value;
    }

    private IntcodeInstruction readInstruction(int input) {
        return new IntcodeInstruction(NumberUtil.getNumberDigits(input, 5));
    }

    public boolean isHalted() {
        return halted;
    }

    public void setDebugMode(boolean b) {
        debugMode = b;
    }

    private void log(Object msg) {
        if (debugMode) System.out.println(msg);
    }

    public int getRelativeBase() {
        return relative;
    }

    public void setRelativeBase(int base) {
        relative = base;
    }
}
