package me.niklas.aoc.twentynineteen.intcode;

import me.niklas.aoc.twentynineteen.util.NumberUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import static me.niklas.aoc.twentynineteen.intcode.IntcodeInstruction.Mode.POSITION;
import static me.niklas.aoc.twentynineteen.intcode.IntcodeInstruction.Opcode.*;

/**
 * Created by Niklas on 05.12.2019 in twentynineteen
 */
public class IntcodeComputer {

    public int[] execute(String input, int... inputs) {
        String[] rawIn = input.split(",");
        int[] in = new int[rawIn.length];

        for (int i = 0; i < rawIn.length; i++) {
            in[i] = NumberUtil.tryParseInt(rawIn[i], -1);
        }

        return execute(in, inputs);
    }

    public int[] execute(int[] code, int... inputs) {
        int[] memory = Arrays.copyOf(code, code.length);

        try {
            int inputPointer = 0;
            int pointer = 0;
            boolean done = false;
            while (pointer < memory.length && !done) {

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
                    if (inputPointer < inputs.length) {
                        memory[loc] = inputs[inputPointer];
                        inputPointer++;
                    } else {
                        System.out.println("INPUT:");
                        String input = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        memory[loc] = NumberUtil.tryParseInt(input, -1);
                    }

                } else if (in.getOpcode() == OUTPUT) {
                    int out = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
                    System.out.println("OUTPUT: " + out);

                } else if (in.getOpcode() == JUMP_IF_TRUE) {
                    int value = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
                    int jump = in.getSecond() == POSITION ? memory[memory[pointer + 2]] : memory[pointer + 2];

                    if (value != 0) {
                        pointer = jump;
                        continue;
                    }

                } else if (in.getOpcode() == JUMP_IF_FALSE) {
                    int value = in.getFirst() == POSITION ? memory[memory[pointer + 1]] : memory[pointer + 1];
                    int jump = in.getSecond() == POSITION ? memory[memory[pointer + 2]] : memory[pointer + 2];

                    if (value == 0) {
                        pointer = jump;
                        continue;
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
                    done = true;

                } else {
                    System.err.println("Unknown operation " + memory[pointer]);
                    break;
                }
                pointer += in.nextOffset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return memory;
    }

    private IntcodeInstruction readInstruction(int input) {
        return new IntcodeInstruction(NumberUtil.getNumberDigits(input, 5));
    }
}
