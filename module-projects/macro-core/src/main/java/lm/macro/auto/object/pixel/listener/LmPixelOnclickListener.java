package lm.macro.auto.object.pixel.listener;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.object.pixel.LmPixel;

public interface LmPixelOnclickListener {
    void click(LmAndroidDevice device, LmPixel pixel) throws Exception;
}