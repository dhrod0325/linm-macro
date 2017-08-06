package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.pixel.LmPixelUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LmExp extends LmCustomPixel {
    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(7, 475);
    }

    @Override
    public int getWidth() {
        return 787;
    }

    @Override
    public int getHeight() {
        return 2;
    }


    public double percent(LmAndroidScreen screen) {
        try {
            BufferedImage img = LmPixelUtils.crop(screen.getScreenShotIO(), (int) getPixelData().getX(), (int) getPixelData().getY(), (int) getWidth(), (int) getHeight());
            Color[][] colors = LmPixelUtils.screenPixelRangeToColors(img, 0, 0, getWidth(), getHeight());

            double total = 0;
            double result = 0;

            for (Color[] colorList : colors) {
                for (Color color : colorList) {
                    boolean rCheck = color.getRed() < 140;
                    boolean gCheck = true;
                    boolean bCheck = color.getBlue() > 35;

                    if (rCheck && bCheck) {
                        result++;
                    }

                    total++;
                }
            }

            double not = result / total * 100;

            return 100d - not;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}