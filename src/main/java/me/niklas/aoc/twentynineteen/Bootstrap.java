package me.niklas.aoc.twentynineteen;

import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.NumberUtil;
import me.niklas.aoc.twentynineteen.util.Timer;

import java.util.Scanner;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class Bootstrap {

    private Bootstrap() {
        AocSolution[] days = new DayManager().getDays();

        if (days.length == 0) {
            System.out.println("Oops! No challenges have been solved yet. Exiting...");
            return;
        }

        System.out.println(String.format("Welcome to my The Advent of Code project! %d challenges have been solved so far:", days.length));

        for (int i = 0; i < days.length; i++) {
            System.out.println(String.format("[%d%s]: %s (Day %d)", i, days[i].isSolved() ? ", SOLVED" : "", days[i].getName(), days[i].getDay()));
        }

        System.out.println("\n\nPlease enter the number of the project you want to run:");

        Scanner s = new Scanner(System.in);
        int day;
        if ((day = NumberUtil.tryParseInt(s.nextLine(), -1)) >= 0 && day < days.length) {
            System.out.println(String.format("Executing \"%s\"...", days[day].getName()));
            System.out.println("---------------- OUTPUT BEGIN -------------------------");
            Timer.instance().start();
            days[day].solve();
            System.out.println("---------------- OUTPUT END (" + Timer.instance().stopNoReset() + "ms to execute) -------------------------");
        } else {
            System.out.println("Your input is out of range or invalid.");
        }

    }

    public static void main(String[] args) {
        new Bootstrap();
    }
}
