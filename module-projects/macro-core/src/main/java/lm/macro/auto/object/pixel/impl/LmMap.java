package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.object.pixel.LmPixel;
import lm.macro.auto.object.pixel.LmPixelData;

public class LmMap extends LmPixel {
    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(673, 85);
    }

    @Override
    public int getWidth() {
        return 104;
    }

    @Override
    public int getHeight() {
        return 100;
    }

    @Override
    public void click(LmAndroidDevice device) throws Exception {
        super.click(device);
    }
}
