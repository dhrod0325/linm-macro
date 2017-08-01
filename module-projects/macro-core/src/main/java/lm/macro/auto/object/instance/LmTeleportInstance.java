package lm.macro.auto.object.instance;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;

public interface LmTeleportInstance {
    boolean toTeleport(LmAndroidDevice device, LmAndroidScreen screen, String name, boolean portalClick) throws Exception;
}
