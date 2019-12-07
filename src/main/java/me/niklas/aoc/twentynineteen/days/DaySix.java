package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.days.orbits.OrbitalMap;
import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

import java.util.List;

/**
 * Created by Niklas on 06.12.2019 in twentynineteen
 */
public class DaySix implements AocSolution {
    @Override
    public int getDay() {
        return 6;
    }

    @Override
    public String getName() {
        return "Universal Orbit Map";
    }

    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public void solve() {
        List<String> in = ResourceUtil.readResource("daysix", getClass());
        OrbitalMap map = new OrbitalMap(in);

        System.out.println("PART ONE: " + map.getOrbitCount()); //273985
        System.out.println("PART TWO: " + map.distanceBetween("SAN", "YOU")); //460
    }
}
