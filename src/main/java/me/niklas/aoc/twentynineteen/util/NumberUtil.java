package me.niklas.aoc.twentynineteen.util;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class NumberUtil {

    public static int tryParseInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException expected) {
            return defaultValue;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static long getLong(String input, int defaultValue) {
        try {
            return Long.parseLong(input);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int[] getNumberDigits(int number, int requiredLength) {
        int length = String.valueOf(number).length();
        int[] result = new int[Math.max(length, requiredLength)];

        for (int i = 1; i <= length; i++) {
            int multi = (int) Math.pow(10, i);
            result[result.length - i] = (number % multi) / (multi / 10);
        }
        return result;
    }

    public static int[] getNumberDigits(int number) {
        return getNumberDigits(number, 0);
    }
}
