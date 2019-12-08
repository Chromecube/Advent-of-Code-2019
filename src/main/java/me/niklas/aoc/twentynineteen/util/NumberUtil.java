package me.niklas.aoc.twentynineteen.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**
     * Retrieves all number digits from an integer,
     *
     * @param number         The number
     * @param requiredLength The minimum length of the result. If it is smaller than the amount of digits in number,
     *                       the length of number will be used. If number has less digits, the first indices will be 0.
     * @return The individual digits (one/index).
     */
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

    /**
     * Generates all possible combinations for an array.
     * <p>
     * Example:
     * current = [-1,-1,-1,-1,-1]
     * index = 0 (when called, this is a recursive method)
     * possibleInputs = [0,1,2,3,4]
     * reuseInputs = false (we want every number to be only present once in the array.)
     * output = {object which was already create}
     * <p>
     * => This will produce 120 results, e.g. containing [4, 1, 3, 2, 0]. The number increase with the index,
     * which means that it starts with [0, 1, ...] -> [0, 2,...] -> [1, 0, ...] -> [2, 0, ...] and so on.
     *
     * @param current        The current array. To begin, provide an array with a value which is not in possibleInputs.
     * @param index          The current index which is being checked. As this is a recursive method, it increases with each call.
     * @param possibleInputs An array of possible inputs.
     * @param reuseInputs    Whether each number in the array should be unique. If not, arrays like {1,2,1,2} will be valid,
     *                       otherwise only arrays like {1,2,3,4} where every number is only present one time will be produced
     * @param output         The list where the results should be stored into. Create it before calling the method.
     */
    private static void generateCombinations(int[] current, int index, int[] possibleInputs, boolean reuseInputs, List<int[]> output) {
        for (int possibleInput : possibleInputs) { //For all possible numbers
            if (reuseInputs || !arrayContains(current, possibleInput)) { //If not present in array yet...
                int[] next = Arrays.copyOf(current, current.length); //Create a copy of the current array
                next[index] = possibleInput; //Insert the unused input
                if (index == current.length - 1) { //This is the last iteration; save the result and return
                    output.add(next);
                    return;
                    //Call recursively
                } else generateCombinations(next, index + 1, possibleInputs, reuseInputs, output);
            }
        }
    }

    public static List<int[]> getCombinations(int arrayLength, int[] possibleInputs) {
        List<int[]> result = new ArrayList<>();

        if (possibleInputs.length < arrayLength) {
            System.err.println("Input length is smaller than the array.");
            return result;
        }
        //We don't want to reuse inputs, every input should be unique in an array.
        generateCombinations(fillEmptyArray(arrayLength, -1), 0, possibleInputs, false, result);
        return result;
    }

    /**
     * Fills an array with an initial value.
     *
     * @param length       The desired length of the array. If it is smaller than 0, an empty array will be returned.
     * @param initialValue The initial value. All elements of the array will have that value.
     * @return An int array of the specified length.
     */
    public static int[] fillEmptyArray(int length, int initialValue) {
        if (length < 0) length = 0;
        int[] arr = new int[length];
        Arrays.fill(arr, initialValue);
        return arr;
    }

    private static boolean arrayContains(int[] arr, int val) {
        for (int v : arr) {
            if (v == val) return true;
        }
        return false;
    }

    /**
     * Takes a string as an input and transforms it into an int array. If a character is invalid, 0 will be used as
     * a default value.
     * <p>
     * Example: "12345" -> [1,2,3,4,5]
     *
     * @param input The input string. All characters should be numbers!
     * @return The parsed int array. result.length == input.length()!
     */
    public static int[] toIntArray(String input) {
        int[] result = new int[input.length()];
        for (int i = 0; i < result.length; i++) {
            char c = input.charAt(i);
            if (c >= 48 && c <= 57) {
                result[i] = c - 48;
            }
        }
        return result;
    }
}
