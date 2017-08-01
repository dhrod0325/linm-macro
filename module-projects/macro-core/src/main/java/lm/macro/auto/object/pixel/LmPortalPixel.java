package lm.macro.auto.object.pixel;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.utils.LmCommonUtils;

public class LmPortalPixel extends LmMatPixel {
    public LmPortalPixel(LmAndroidScreen screen) {
        super(screen, LmGraphics.DUNGEON);
    }

    @Override
    public void click(LmAndroidDevice device) throws Exception {
        super.click(device);

        LmCommonUtils.sleep(LmCommon.REFRESH_SLEEP);

        getScreen().refreshScreen(device);

        LmCommonUtils.sleep(LmCommon.REFRESH_SLEEP);

        new LmOkPixel(getScreen()).click(device);
    }
}
