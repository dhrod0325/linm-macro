package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.object.pixel.LmBooleanPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.pixel.LmPixelUtils;

import java.awt.*;

public class LmAuto extends LmBooleanPixel {
    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(589, 330);
    }

    @Override
    public int getWidth() {
        return 40;
    }

    @Override
    public int getHeight() {
        return 40;
    }

    @Override
    public boolean isTrue(LmAndroidScreen screen) {
        int resultColor = 0;

        Color[][] colors = LmPixelUtils.screenPixelRangeToColors(screen, this);

        if (colors == null) {
            return true;
        }

        for (Color[] colorList : colors) {
            for (Color color : colorList) {
                boolean check1 = color.getRed() >= 255;
                boolean check3 = color.getRed() >= 230 && color.getGreen() >= 230 && color.getBlue() >= 175;

                if (check3 || check1) {
                    resultColor++;
                }
            }
        }

        return resultColor > 5;
    }
}
