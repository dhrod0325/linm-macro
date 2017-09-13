package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.object.pixel.LmColorRangePixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.pixel.LmColorCheck;
import lm.macro.auto.utils.pixel.LmColorCheckType;

import java.awt.image.BufferedImage;

public class LmHp extends LmColorRangePixel {
    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(52, 28);
    }

    @Override
    public int getWidth() {
        return 123;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public LmColorCheck red() {
        return new LmColorCheck(100, LmColorCheckType.ABOVE);
    }

    @Override
    public LmColorCheck green() {
        return new LmColorCheck(40, LmColorCheckType.BELOW);
    }

    @Override
    public LmColorCheck blue() {
        return new LmColorCheck(40, LmColorCheckType.BELOW);
    }

    @Override
    public double percent(BufferedImage bufferedImage) {
        double v = super.percent(bufferedImage);
        if (v < 1) {
            v = new LmPoisonHp().percent(bufferedImage);
        }
        return v;
    }

    @Override
    public double percent(LmAndroidScreen screen) {
        double v = super.percent(screen);

        if (v < 1) {
            v = new LmPoisonHp().percent(screen.getScreenShotIO());
        }

        if (v < 3) {
            v = 0;
        }

        return v;
    }
}