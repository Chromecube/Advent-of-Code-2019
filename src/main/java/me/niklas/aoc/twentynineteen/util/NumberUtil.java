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

    public static long getLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (Exception e) {
            return -1L;
        }
    }

    public static boolean isLong(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
