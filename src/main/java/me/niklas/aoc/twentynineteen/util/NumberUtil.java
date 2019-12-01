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
}
