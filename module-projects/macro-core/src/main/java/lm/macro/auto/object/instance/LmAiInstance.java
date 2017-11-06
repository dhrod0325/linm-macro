package lm.macro.auto.object.instance;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;

public interface LmAiInstance {
    void ai(LmAndroidScreen screen, long time) throws Exception;

    void setState(LmPcState state);

    LmPcState getState();

    void setDevice(LmAndroidDevice device);

    LmAndroidDevice getDevice();

    LmAndroidScreen getScreen();

    String getName();

    void setName(String title);
}
