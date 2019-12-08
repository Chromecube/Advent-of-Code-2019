import me.niklas.aoc.twentynineteen.days.spaceimage.SpaceImage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Niklas on 08.12.2019 in twentynineteen
 */
public class SpaceImageTest {

    @Test
    public void testLayers() {
        SpaceImage image = new SpaceImage(3, 2, "123456789012");
        Assert.assertEquals(2, image.getLayers().length);
        Assert.assertEquals(6, image.getLayer(0).getData().length);
        Assert.assertEquals(5, image.getLayer(0).dataAt(1, 1));
        Assert.assertEquals(0, image.getLayer(0).amountOf(0));

        System.out.println(image.asString());
    }

    @Test
    public void testRenderedImage() {
        SpaceImage image = new SpaceImage(2, 2, "0222112222120000");
        Assert.assertArrayEquals(new int[]{0, 1, 1, 0}, image.getImage());
    }
}
