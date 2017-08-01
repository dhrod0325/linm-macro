package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.LmCommonUtils;

public class LmDieRestart {
    private int dieCount;

    public void restart(
            LmAndroidScreen screen,
            LmAndroidDevice device) throws Exception {

        if (isDie(screen)) {
            dieCount++;

            LmCommonUtils.saveScreenShot(device, screen.getScreenShotIO(), "사망");

            LmPixels.재시작버튼().click(device);
        }
    }

    public boolean isDie(LmAndroidScreen screen) throws Exception {
        LmPixelData dieData = screen.findPixelMatch(LmGraphics.IS_DIE, 0.9);
        return dieData.isExists();
    }

    public int getDieCount() {
        return dieCount;
    }
}
