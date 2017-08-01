package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.utils.LmCommonUtils;

public class LmLetter {
    public void check(LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        LmCustomPixel btn = new LmCustomPixel(762, 19, 27, 27);

        btn.click(device);
        LmCommonUtils.sleep(200);

        new LmCustomPixel(621, 213, 18, 16).click(device);
        LmCommonUtils.sleep(200);

        new LmCustomPixel(644, 432, 120, 28).click(device);
        LmCommonUtils.sleep(200);

        btn.click(device);

        LmCommonUtils.sleep(200);

        btn.click(device);
    }
}
