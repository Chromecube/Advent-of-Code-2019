import me.niklas.aoc.twentynineteen.days.orbits.OrbitalMap;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Niklas on 06.12.2019 in twentynineteen
 */
public class TestOrbitalMap {


    @Test
    public void testBasicFunctionality() {
        List<String> in = ResourceUtil.readResource("orbits", getClass());
        OrbitalMap map = new OrbitalMap(in);

        Assert.assertEquals(3, map.getOrbitCountOf("D"));
        Assert.assertEquals(7, map.getOrbitCountOf("L"));
        Assert.assertEquals(0, map.getOrbitCountOf("COM"));
    }

    @Test
    public void testDistances() {
        List<String> in = ResourceUtil.readResource("orbital-distance", getClass());
        OrbitalMap map = new OrbitalMap(in);

        Assert.assertEquals(4, map.distanceBetween("YOU", "SAN"));
    }
}
