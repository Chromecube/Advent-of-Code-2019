import me.niklas.aoc.twentynineteen.days.intcode.IntcodeComputer;
import me.niklas.aoc.twentynineteen.days.intcode.IntcodeInstruction;
import me.niklas.aoc.twentynineteen.util.NumberUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Niklas on 05.12.2019 in twentynineteen
 */
public class TestIntcodes {

    @Test
    public void testInterpreter() {
        IntcodeComputer executor = new IntcodeComputer();

        Assert.assertEquals(1, executor.execute("3,9,8,9,10,9,4,9,99,-1,8", 8).lastOutput());
        System.out.println("Stage 1: PASS");

        Assert.assertEquals(0, executor.execute("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 0).lastOutput());
        System.out.println("Stage 2: PASS");

        String in = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        Assert.assertEquals(999, executor.execute(in, 7).lastOutput());     //999  (<8)
        Assert.assertEquals(1000, executor.execute(in, 8).lastOutput());     //1000 (==8)
        Assert.assertEquals(1001, executor.execute(in, 500).lastOutput());   //1001 (>8)*/
        System.out.println("Stage 3: PASS");
    }

    @Test
    public void testOpcodes() {
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(1, 5)).nextOffset(),
                4); //Add
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(2, 5)).nextOffset(),
                4); //Multiply
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(3, 5)).nextOffset(),
                2); //Input
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(4, 5)).nextOffset(),
                2); //Output
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(5, 5)).nextOffset(),
                3); //if true
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(6, 5)).nextOffset(),
                3); //if false
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(7, 5)).nextOffset(),
                4); //less than
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(8, 5)).nextOffset(),
                4); //equals
        Assert.assertEquals(new IntcodeInstruction(NumberUtil.getNumberDigits(9, 5)).nextOffset(), 2);
    }

    @Test
    public void testRelative() {
        IntcodeComputer computer = new IntcodeComputer();

        computer.loadProgram(computer.parseString("109,19,99"));
        computer.setRelativeBase(2000);
        computer.continueUntilOutput();
        Assert.assertEquals(2019, computer.getRelativeBase());
    }

    @Test
    public void testLargeNumberSupport() {
        IntcodeComputer computer = new IntcodeComputer(true);
        computer.execute("104,1125899906842624,99");
        Assert.assertEquals(1125899906842624L, (long) computer.getOutput().get(0));
    }

    @Test
    public void testRelativeProgram() {
        IntcodeComputer computer = new IntcodeComputer();

        computer.setDebugMode(false);
        computer.execute("109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99");
        Assert.assertEquals("[109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99]", Arrays.toString(computer.getOutput().toArray()));
        System.out.println("Stage 1: PASS");

        computer.execute("1102,34915192,34915192,7,4,7,99,0");
        Assert.assertEquals(16, computer.getOutput().get(computer.getOutput().size() - 1).toString().length());
        System.out.println("Stage 1: PASS");
    }


}
