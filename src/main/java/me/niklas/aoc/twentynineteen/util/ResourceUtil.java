package me.niklas.aoc.twentynineteen.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Niklas on 01.12.2019 in twentynineteen
 */
public class ResourceUtil {

    public static List<String> readResource(String resourceName, Class caller) {
        List<String> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(caller.getClassLoader().getResourceAsStream(resourceName))))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (NumberUtil.isLong(line)) result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Long> readResourceAsLong(String resourceName, Class caller) {
        List<Long> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(caller.getClassLoader().getResourceAsStream(resourceName))))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (NumberUtil.isLong(line)) result.add(NumberUtil.getLong(line, -1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
