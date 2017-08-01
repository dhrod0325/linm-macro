package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.object.pixel.LmPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.pixel.LmPixelUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LmPoisonHp extends LmPixel {
    public double percent(BufferedImage screen) {
        try {
            BufferedImage img = LmPixelUtils.crop(screen, (int) getPixelData().getX(), (int) getPixelData().getY(), getWidth(), getHeight());
            Color[][] colors = LmPixelUtils.screenPixelRangeToColors(img, 0, 0, getWidth(), getHeight());

            double total = 0;
            double result = 0;

            for (Color[] colorList : colors) {
                for (Color color : colorList) {
                    boolean check = (color.getRed() >= 0 && color.getRed() <= 55)
                            && (color.getGreen() >= 50 && color.getGreen() <= 110)
                            && (color.getBlue() >= 0 && color.getBlue() <= 50);

                    if (check) {
                        result++;
                    }

                    total++;
                }
            }

            return result / total * 100;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(48, 14);
    }

    @Override
    public int getWidth() {
        return 123;
    }

    @Override
    public int getHeight() {
        return 8;
    }

}