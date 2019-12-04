package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 04.12.2019 in twentynineteen
 */
public class DayFour implements AocSolution {
    private final int inputMin = 172851;
    private final int inputMax = 675869;

    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public String getName() {
        return "Secure Container";
    }

    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public void solve() {
        System.out.println("Iterating over numbers");
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        for (int i = inputMin; i <= inputMax; i++) {
            int[] digits = NumberUtil.getNumberDigits(i);
            if (!numbersNeverDecrease(digits)) continue;
            if (meetsPartOneCriteria(digits)) first.add(i); //1660
            if (meetsPartTwoCriteria(digits)) second.add(i); //1135
        }
        System.out.println("PART ONE: " + first.size());
        System.out.println("PART TWO: " + second.size());
    }

    private boolean meetsPartOneCriteria(int[] digits) {
        int last = -1;
        for (int digit : digits) {
            if (last == digit) return true; //The digit before was the same -> Match
            last = digit;
        }
        return false;
    }

    private boolean meetsPartTwoCriteria(int[] digits) {
        int i = 0;
        while (i < digits.length - 1) {
            if (digits[i] == digits[i + 1]) { //At least two of the same type
                if (i + 2 == digits.length || digits[i + 2] != digits[i]) return true; //This is a couple
                int current = digits[i];
                while (i < digits.length - 1 && digits[i] == current) i++; //Increase until another number is found
            } else i++; //No couple -> Increase
        }
        return false;
    }

    private boolean numbersNeverDecrease(int[] digits) {
        int last = -1;
        for (int digit : digits) {
            if (last > digit) return false;
            if (last < digit) last = digit;
        }
        return true;
    }
}
