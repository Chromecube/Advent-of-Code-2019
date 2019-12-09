package me.niklas.aoc.twentynineteen.days.intcode;

import me.niklas.aoc.twentynineteen.util.NumberUtil;

import java.util.ArrayList;
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
    private final Queue<Long> inputQueue = new LinkedBlockingQueue<>(1024);
    private final List<Long> output = new ArrayList<>();
    private boolean halted = false;
    private long[] memory = new long[0];
    private int pointer = 0;
    private int relative = 0;
    private boolean debugMode;

    public IntcodeComputer() {
        this(false);
    }

    public IntcodeComputer(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public long[] parseString(String input) {
        String[] rawIn = input.split(",");
        long[] in = new long[rawIn.length];

        for (int i = 0; i < rawIn.length; i++) {
            in[i] = NumberUtil.getLong(rawIn[i].trim(), -1);
        }
        return in;
    }

    public IntcodeResult execute(long[] code, long... inputs) {
        loadProgram(code, inputs);

        run();

        //Collect output
        long[] out = new long[output.size()];

        for (int i = 0; i < out.length; i++) {
            out[i] = output.get(i);
        }

        return new IntcodeResult(memory, out);
    }

    public IntcodeResult execute(String input, long... inputs) {
        return execute(parseString(input), inputs);
    }

    public void run() {
        while (!halted) {
            runNext();
        }
    }

    public void loadProgram(long[] code, long... inputs) {
        inputQueue.clear();
        addInput(inputs);
        output.clear();
        pointer = 0;
        memory = new long[65535];
        System.arraycopy(code, 0, memory, 0, code.length);
        halted = false;
        relative = 0;
    }

    public void addInput(long... input) {
        for (long i : input) {
            inputQueue.offer(i);
        }
    }

    public void continueUntilOutput() {
        int size = output.size();

        while (!halted && size == output.size()) {
            runNext();
        }
    }

    public List<Long> getOutput() {
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
            long first = getValue(1, in.getFirst());
            long second = getValue(2, in.getSecond());

            long sum = first + second;

            setValue(3, in.getThird(), sum);
        } else if (in.getOpcode() == MULTIPLY) { //Multiply numbers
            long first = getValue(1, in.getFirst());
            long second = getValue(2, in.getSecond());

            long product = first * second;

            setValue(3, in.getThird(), product);
        } else if (in.getOpcode() == INPUT) {
            int loc = (int) getValue(1, in.getFirst(), true);
            if (inputQueue.size() > 0) {
                memory[loc] = inputQueue.poll();
            } else {
                System.err.println("Reached end of input queue");
                System.exit(1);
            }
        } else if (in.getOpcode() == OUTPUT) {
            long out = getValue(1, in.getFirst());
            output.add(out);

        } else if (in.getOpcode() == JUMP_IF_TRUE) {
            long value = getValue(1, in.getFirst());
            int jump = (int) getValue(2, in.getSecond());

            if (value != 0) {
                pointer = jump;
                return;
            }
        } else if (in.getOpcode() == JUMP_IF_FALSE) {
            long value = getValue(1, in.getFirst());
            int jump = (int) getValue(2, in.getSecond());

            if (value == 0) {
                pointer = jump;
                return;
            }

        } else if (in.getOpcode() == LESS_THAN) {
            long first = getValue(1, in.getFirst());
            long second = getValue(2, in.getSecond());

            int result = first < second ? 1 : 0;

            setValue(3, in.getThird(), result);
        } else if (in.getOpcode() == EQUALS) {
            long first = getValue(1, in.getFirst());
            long second = getValue(2, in.getSecond());

            int result = first == second ? 1 : 0;

            setValue(3, in.getThird(), result);

        } else if (in.getOpcode() == RELATIVE_BASE) {
            int add = (int) getValue(1, in.getFirst());
            log(add);
            setRelativeBase(relative + add);
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

    private long getValue(int offset, IntcodeInstruction.Mode mode, boolean addressOnly) {
        log("Mode: " + mode);
        if (addressOnly) {
            return mode == POSITION ? memory[pointer + offset] :
                    mode == IMMEDIATE ? pointer + offset :
                            relative + memory[pointer + offset];
        }
        return mode == POSITION ? memory[(int) memory[pointer + offset]] :
                mode == IMMEDIATE ? memory[pointer + offset] :
                        memory[(int) (relative + memory[pointer + offset])];
    }

    public long getValue(int offset, IntcodeInstruction.Mode mode) {
        return getValue(offset, mode, false);
    }

    public void setValue(int offset, IntcodeInstruction.Mode mode, long value) {
        if (mode == POSITION) memory[(int) memory[pointer + offset]] = value;
        else if (mode == IMMEDIATE) memory[pointer + offset] = value;
        else memory[(int) (relative + memory[pointer + offset])] = value;
    }

    private IntcodeInstruction readInstruction(long input) {
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
        log("Setting relative base to " + base);
        relative = base;
    }

    public long getLastOutput() {
        return output.get(output.size() - 1);
    }
}
