import me.niklas.aoc.twentynineteen.intcode.IntcodeComputer;
import me.niklas.aoc.twentynineteen.intcode.IntcodeInstruction;
import me.niklas.aoc.twentynineteen.util.NumberUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Niklas on 05.12.2019 in twentynineteen
 */
public class TestIntcodes {

    @Test
    public void testInterpreter() {
        IntcodeComputer executor = new IntcodeComputer();

        Assert.assertEquals(1, executor.execute("3,9,8,9,10,9,4,9,99,-1,8", 8).lastOutput());
        Assert.assertEquals(0, executor.execute("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 0).lastOutput());
        String in = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        Assert.assertEquals(999, executor.execute(in, 7).lastOutput());     //999  (<8)
        Assert.assertEquals(1000, executor.execute(in, 8).lastOutput());     //1000 (==8)
        Assert.assertEquals(1001, executor.execute(in, 500).lastOutput());   //1001 (>8)*/
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
    }
}
