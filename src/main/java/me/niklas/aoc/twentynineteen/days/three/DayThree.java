package me.niklas.aoc.twentynineteen.days.three;

import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

import java.util.List;

/**
 * Created by Niklas on 03.12.2019 in twentynineteen
 */
public class DayThree implements AocSolution {
    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public String getName() {
        return "Crossed Wires";
    }

    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public void solve() {
        List<String> in = ResourceUtil.readResource("daythree", getClass());

        Path p1 = new Path();
        p1.readInstruction(in.get(0));
        //Test input
        //p1.readInstruction("R75,D30,R83,U83,L12,D49,R71,U7,L72");

        Path p2 = new Path();
        p2.readInstruction(in.get(1));
        //Test input
        //p2.readInstruction("U62,R66,U55,R34,D71,R55,D58,R83");

        p1.solveBothParts(p2);
    }
}
