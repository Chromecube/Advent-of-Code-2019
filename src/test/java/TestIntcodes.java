import me.niklas.aoc.twentynineteen.intcode.IntcodeInstruction;
import me.niklas.aoc.twentynineteen.util.NumberUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Niklas on 05.12.2019 in twentynineteen
 */
public class TestIntcodes {

    @Test
    public void testIntcodes() {
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
