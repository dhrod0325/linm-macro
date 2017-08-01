package lm.macro.auto.utils.pixel;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmPixel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LmPixelUtils {
    private static LmLog logger = new LmLog(LmPixelUtils.class);

    public static Color[][] loadPixelsFromImage(File file) throws IOException {
        return loadPixelsFromBuffer(ImageIO.read(file));
    }

    public static Color[][] loadPixelsFromBuffer(BufferedImage image) throws IOException {
        if (image == null) {
            return new Color[][]{};
        }

        Color[][] colors = new Color[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                colors[x][y] = new Color(image.getRGB(x, y));
            }
        }

        return colors;
    }

    public static Color[][] screenPixelRangeToColors(BufferedImage image, double posX, double posY, int width, int height) {
        try {
            Color[][] colors = LmPixelUtils.loadPixelsFromBuffer(image);

            if (colors.length == 0) {
                return new Color[][]{};
            }

            int i = 0;

            Color[][] result = new Color[width][height];

            for (int x = (int) posX; x < posX + width; x++) {
                Color[] loop = colors[x];

                int j = 0;

                for (int y = (int) posY; y < posY + height; y++) {
                    Color color = loop[y];
                    result[i][j] = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
                    j++;
                }

                i++;
            }

            return result;
        } catch (Exception e) {
            logger.error(e);
        }

        return new Color[0][0];
    }

    private static Color[][] screenPixelRangeToColors(LmAndroidScreen screen, double posX, double posY, int width, int height) {
        if (screen == null) {
            return new Color[][]{};
        }

        BufferedImage image = screen.getScreenShotIO();
        return screenPixelRangeToColors(image, posX, posY, width, height);
    }

    public static Color[][] screenPixelRangeToColors(LmAndroidScreen screen, LmPixel pixel) {
        try {
            return screenPixelRangeToColors(screen,
                    pixel.getPixelData().getX(),
                    pixel.getPixelData().getY(),
                    pixel.getWidth(),
                    pixel.getHeight());
        } catch (Exception e) {
            logger.error(e);
        }

        return new Color[0][0];
    }

    public static BufferedImage convertColorsToBufferedImage(Color[][] colors) {
        int width = colors.length;
        int height = colors.length > 0 ? colors[0].length : 0;

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int x = 0;

        for (Color[] colorList : colors) {
            int y = 0;

            for (Color color : colorList) {
                int pixel = color.getRGB();
                output.setRGB(x, y, pixel);
                y++;
            }
            x++;
        }

        return output;
    }

    public static double findColorRange(Color[][] colors, LmColorCheck red, LmColorCheck green, LmColorCheck blue, int totalOption) {
        double total = totalOption;
        double result = 0;

        for (Color[] colorList : colors) {
            for (Color color : colorList) {
                boolean rCheck = red.getColorCheckType() == LmColorCheckType.ABOVE ? color.getRed() >= red.getValue() : color.getRed() <= red.getValue();
                boolean gCheck = green.getColorCheckType() == LmColorCheckType.ABOVE ? color.getGreen() >= green.getValue() : color.getGreen() <= green.getValue();
                boolean bCheck = blue.getColorCheckType() == LmColorCheckType.ABOVE ? color.getBlue() >= blue.getValue() : color.getBlue() <= blue.getValue();

                if (rCheck && gCheck && bCheck) {
                    result++;
                }

                total++;
            }
        }

        return result / total * 100;
    }

    public static double findColorRange(Color[][] colors, LmColorCheck red, LmColorCheck green, LmColorCheck blue) {
        return findColorRange(colors, red, green, blue, 0);
    }

    public static BufferedImage crop(BufferedImage src, int x, int y, int w, int h) throws Exception {
        return src.getSubimage(x, y, w, h);
    }
}