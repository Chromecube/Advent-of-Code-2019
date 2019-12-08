package me.niklas.aoc.twentynineteen.days.spaceimage;

import me.niklas.aoc.twentynineteen.util.ColorUtil;
import me.niklas.aoc.twentynineteen.util.NumberUtil;

import java.awt.image.BufferedImage;

/**
 * Created by Niklas on 08.12.2019 in twentynineteen
 */
public class SpaceImage {

    private final int width;
    private final int height;
    private final Layer[] layers;
    private final int[] image;

    public SpaceImage(int width, int height, String data) {
        this(width, height, NumberUtil.toIntArray(data));
    }

    public SpaceImage(int width, int height, int[] data) {
        this.width = width;
        this.height = height;

        if (data.length % width * height != 0) {
            System.err.println("The image data has an invalid length.");
            System.exit(1);
        }

        int layerLength = width * height;
        layers = new Layer[data.length / layerLength]; //Create several layers. Each layer receives the same amount of input.
        for (int i = 0; i < layers.length; i++) {
            int[] layerData = new int[layerLength];
            System.arraycopy(data, i * layerLength, layerData, 0, layerLength); //Copy input from main array
            layers[i] = new Layer(width, height, layerData);
        }

        //Build image
        image = NumberUtil.fillEmptyArray(layerLength, 2);
        for (Layer layer : layers) {
            for (int i = 0; i < layerLength; i++) {
                if (image[i] == 2 && layer.getData()[i] != 2) image[i] = layer.getData()[i];
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public Layer getLayer(int index) {
        if (index < 0 || index >= layers.length) return null;
        return layers[index];
    }

    public String asString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < layers.length; i++) {
            builder.append("\n\nLayer ").append(i + 1).append(":\n").append(layers[i].asString());
        }
        return builder.toString().trim();
    }

    public int[] getImage() {
        return image;
    }

    public int getImageAt(int x, int y) {
        return image[x + y * width];
    }

    public BufferedImage toImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int data = getImageAt(x, y);
                int rgb = ColorUtil.argb(data == 2 ? 0 : 255, data == 1 ? 255 : 0, data == 1 ? 255 : 0, data == 1 ? 255 : 0);
                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }
}
