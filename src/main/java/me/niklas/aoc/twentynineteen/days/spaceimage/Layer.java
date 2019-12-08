package me.niklas.aoc.twentynineteen.days.spaceimage;

/**
 * Created by Niklas on 08.12.2019 in twentynineteen
 */
public class Layer {

    private final int width;
    private final int height;
    private final int[] data;

    public Layer(int width, int height, int[] data) {
        this.width = width;
        this.height = height;
        this.data = data;

        if (data.length != width * height) {
            System.err.println("Image data has an invalid size: Has " + data.length + " data points, expected " + width * height);
            System.exit(1);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getData() {
        return data;
    }

    public int dataAt(int x, int y) {
        return data[x + y * width];
    }

    public int amountOf(int value) {
        int result = 0;
        for (int el : data) {
            if (el == value) result++;
        }
        return result;
    }

    public String asString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (i > 0 && i % width == 0) builder.append("\n");
            builder.append(data[i]);
        }
        return builder.toString().trim();
    }
}
