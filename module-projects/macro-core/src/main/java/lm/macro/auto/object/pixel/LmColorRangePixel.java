package lm.macro.auto.object.pixel;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.utils.pixel.LmColorCheck;
import lm.macro.auto.utils.pixel.LmPixelUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class LmColorRangePixel extends LmPixel {
    public abstract LmColorCheck red();

    public abstract LmColorCheck green();

    public abstract LmColorCheck blue();

    public double percent(BufferedImage bufferedImage) {
        try {
            BufferedImage img = LmPixelUtils.crop(bufferedImage, (int) getPixelData().getX(), (int) getPixelData().getY(), (int) getWidth(), (int) getHeight());
            Color[][] colors = LmPixelUtils.screenPixelRangeToColors(img, 0, 0, getWidth(), getHeight());

            return LmPixelUtils.findColorRange(colors, red(), green(), blue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double percent(LmAndroidScreen screen) {
        BufferedImage bufferedImage = screen.getScreenShotIO();
        return percent(bufferedImage);
    }
}