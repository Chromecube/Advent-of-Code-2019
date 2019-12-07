import me.niklas.aoc.twentynineteen.util.NumberUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Niklas on 07.12.2019 in twentynineteen
 */
public class NumberUtilTest {

    @Test
    public void testArrayGeneration() {
        //Size was calculated
        Assert.assertEquals(120, NumberUtil.getCombinations(5, new int[]{0, 1, 2, 3, 4}).size());
    }
}
