package me.niklas.aoc.twentynineteen.days.intcode;

/**
 * Created by Niklas on 07.12.2019 in twentynineteen
 */
public class IntcodeResult {

    public final long[] memory;
    public final long[] outputs;

    IntcodeResult(long[] memory, long[] outputs) {
        this.memory = memory;
        this.outputs = outputs;
    }

    public long lastOutput() {
        return outputs[outputs.length - 1];
    }
}
