package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.object.pixel.LmBooleanPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.pixel.LmPixelUtils;

import java.awt.*;

public class LmSelf extends LmBooleanPixel {
    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(663, 276);
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
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
                if (color.getGreen() >= 200 && color.getRed() > 50 && color.getBlue() > 50) {
                    resultColor++;
                }
            }
        }

        return resultColor > 0;
    }
}
