package lm.macro.auto.object.pixel;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;

public abstract class LmBooleanPixel extends LmCustomPixel {
    public void setBoolean(LmAndroidDevice device, LmAndroidScreen screen, boolean isTrue) {
        try {
            boolean currentBoolean = isTrue(screen);

            if (isTrue) {
                if (!currentBoolean) {
                    click(device);
                }
            } else {
                if (currentBoolean) {
                    click(device);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract boolean isTrue(LmAndroidScreen screen);
}