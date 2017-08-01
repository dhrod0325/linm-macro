package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.object.pixel.LmPixel;
import lm.macro.auto.object.pixel.LmPixelData;

public class LmJoystick extends LmPixel {
    /**
     * 조이스틱이 작았다가 커지는 딜레이
     */
    public static final long JOYSTICK_DELAY = 400;

    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(122, 380);
    }

    @Override
    public int getWidth() {
        return 46;
    }

    @Override
    public int getHeight() {
        return 46;
    }

    @Override
    public double[] clickPosition() {
        return new double[]{122, 380};
    }

    @Override
    public void swipe(LmAndroidDevice device, double x1, double y1, long delay) {
        super.swipe(device, x1, y1, delay + JOYSTICK_DELAY);
    }
}
