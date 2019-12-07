package me.niklas.aoc.twentynineteen.intcode;

/**
 * Created by Niklas on 07.12.2019 in twentynineteen
 */
public class IntcodeResult {

    public final int[] memory;
    public final int[] outputs;

    IntcodeResult(int[] memory, int[] outputs) {
        this.memory = memory;
        this.outputs = outputs;
    }

    public int lastOutput() {
        return outputs[outputs.length - 1];
    }
}
