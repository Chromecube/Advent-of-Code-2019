package me.niklas.aoc.twentynineteen.util;

/**
 * Created by Niklas on 08.12.2019 in twentynineteen
 * <p>
 * Fore more information on byte-shifting:
 * https://stackoverflow.com/questions/3312853/how-does-bitshifting-work-in-java
 * https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op3.html
 */
public class ColorUtil {

    private static int[] parse(int argb) {
        return new int[]{(argb & 0xff000000) >> 24, (argb & 0xff0000) >> 16, (argb & 0xff00) >> 8, (argb & 0xff)};
    }

    public static int argb(int a, int r, int g, int b) {
        return a << 24 | r << 16 | g << 8 | b;
    }
}
