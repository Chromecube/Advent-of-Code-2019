package me.niklas.aoc.twentynineteen.intcode;

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

        third = input[0] == 0 ? Mode.POSITION : Mode.IMMEDIATE;
        second = input[1] == 0 ? Mode.POSITION : Mode.IMMEDIATE;
        first = input[2] == 0 ? Mode.POSITION : Mode.IMMEDIATE;
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
        return opcode.ordinal() < 2 || opcode.ordinal() > 5 ? 4 : opcode.ordinal() > 3 ? 3 : 2; //ADD and MULTIPLY -> 4 indices, IN/OUT only two
    }

    public enum Mode {
        POSITION, IMMEDIATE
    }

    public enum Opcode {
        ADD, MULTIPLY, INPUT, OUTPUT, JUMP_IF_TRUE, JUMP_IF_FALSE, LESS_THAN, EQUALS, END, UNKNOWN;

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
                case 99:
                    return END;
                default:
                    return UNKNOWN;
            }
        }
    }


}
