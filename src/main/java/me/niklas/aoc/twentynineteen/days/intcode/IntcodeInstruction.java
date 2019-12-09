package me.niklas.aoc.twentynineteen.days.intcode;

/**
 * Created by Niklas on 05.12.2019 in twentynineteen
 */
public class IntcodeInstruction {

    private final Opcode opcode;
    private final Mode first;
    private final Mode second;
    private final Mode third;

    public IntcodeInstruction(int[] input) {
        if (input.length != 5) {
            throw new IllegalArgumentException("Invalid input length (has to be 5)");
        }

        third = input[0] == 0 ? Mode.POSITION : input[0] == 1 ? Mode.IMMEDIATE : Mode.RELATIVE;
        second = input[1] == 0 ? Mode.POSITION : input[1] == 1 ? Mode.IMMEDIATE : Mode.RELATIVE;
        first = input[2] == 0 ? Mode.POSITION : input[2] == 1 ? Mode.IMMEDIATE : Mode.RELATIVE;
        opcode = Opcode.parse((input[3] * 10) + input[4]);
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public Mode getFirst() {
        return first;
    }

    public Mode getSecond() {
        return second;
    }

    public Mode getThird() {
        return third;
    }

    public int nextOffset() {
        return opcode.ordinal() == 8 ? 2 : opcode.ordinal() < 2 || opcode.ordinal() > 5 ? 4 : opcode.ordinal() > 3 ? 3 : 2; //ADD and MULTIPLY -> 4 indices, IN/OUT only two
    }

    public enum Mode {
        POSITION, IMMEDIATE, RELATIVE
    }

    public enum Opcode {
        ADD, MULTIPLY, INPUT, OUTPUT, JUMP_IF_TRUE, JUMP_IF_FALSE, LESS_THAN, EQUALS, RELATIVE_BASE, END, UNKNOWN;

        static Opcode parse(int in) {
            switch (in) {
                case 1:
                    return ADD;
                case 2:
                    return MULTIPLY;
                case 3:
                    return INPUT;
                case 4:
                    return OUTPUT;
                case 5:
                    return JUMP_IF_TRUE;
                case 6:
                    return JUMP_IF_FALSE;
                case 7:
                    return LESS_THAN;
                case 8:
                    return EQUALS;
                case 9:
                    return RELATIVE_BASE;
                case 99:
                    return END;
                default:
                    return UNKNOWN;
            }
        }
    }


}
