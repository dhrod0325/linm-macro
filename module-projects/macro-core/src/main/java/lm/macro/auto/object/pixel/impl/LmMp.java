package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.pixel.LmPixelUtils;

import java.awt.*;

public class LmMp extends LmCustomPixel {
    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(47, 27);
    }

    @Override
    public int getWidth() {
        return 127;
    }

    @Override
    public int getHeight() {
        return 4;
    }

    public double percent(LmAndroidScreen screen) {
        Color[][] colors = LmPixelUtils.screenPixelRangeToColors(screen, this);

        int x = 0;
        int o = 30;

        for (Color[] colorList : colors) {
            for (Color color : colorList) {
                if (color.getRed() < 130 && color.getGreen() <= 255 && color.getGreen() >= 255 - o && color.getBlue() <= 255 && color.getBlue() >= 255 - o) {
                    double result = (double) x / getWidth() * 100;
                    return result / 97 * 100;
                }
            }
            x++;
        }

        return 0;
    }
}