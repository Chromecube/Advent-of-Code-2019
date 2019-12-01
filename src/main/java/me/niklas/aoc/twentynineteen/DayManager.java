package me.niklas.aoc.twentynineteen;

import me.niklas.aoc.twentynineteen.util.AocSolution;
import org.reflections.Reflections;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class DayManager {

    public AocSolution[] getDays() {
        Reflections ref = new Reflections("me.niklas.aoc.twentynineteen.days");

        Set<Class<? extends AocSolution>> all = ref.getSubTypesOf(AocSolution.class);

        AocSolution[] result = new AocSolution[all.size()];

        Iterator<Class<? extends AocSolution>> it = all.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                AocSolution solution = it.next().getConstructor().newInstance();
                result[solution.getDay()] = solution;
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }

        return result;
    }
}
