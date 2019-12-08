package me.niklas.aoc.twentynineteen.days;

import me.niklas.aoc.twentynineteen.days.spaceimage.SpaceImage;
import me.niklas.aoc.twentynineteen.util.AocSolution;
import me.niklas.aoc.twentynineteen.util.NumberUtil;
import me.niklas.aoc.twentynineteen.util.ResourceUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Niklas on 08.12.2019 in twentynineteen
 */
public class DayEight implements AocSolution {
    @Override
    public int getDay() {
        return 8;
    }

    @Override
    public String getName() {
        return "Space Image Format";
    }

    @Override
    public boolean isSolved() {
        return true;
    }

    @Override
    public void solve() {
        int[] data = NumberUtil.toIntArray(ResourceUtil.readResource("dayeight", getClass()).get(0));

        SpaceImage image = new SpaceImage(25, 6, data);

        int least = 0;
        int amount = Integer.MAX_VALUE;

        for (int i = 0; i < image.getLayers().length; i++) {
            int a = image.getLayer(i).amountOf(0);
            if (a < amount) {
                least = i;
                amount = a;
            }
        }

        System.out.println("PART ONE: " + image.getLayer(least).amountOf(1) * image.getLayer(least).amountOf(2));

        StringBuilder b = new StringBuilder();
        for (int i : image.getImage()) {
            b.append(i);
        }

        File out = new File(System.getProperty("user.dir") + File.separator + "out.png");
        try {
            ImageIO.write(image.toImage(), "png", out);
            System.out.println("PART TWO: OPENING IN DEFAULT IMAGE APP");
            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
