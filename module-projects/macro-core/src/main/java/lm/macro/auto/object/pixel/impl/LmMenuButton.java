package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmMatPixel;

public class LmMenuButton {
    private LmLog logger = new LmLog(getClass());

    public boolean isOpen(LmAndroidScreen screen) throws Exception {
        return screen.findPixelMatch(LmGraphics.BUTTON_MENU_OPEN, 0.88).isExists();
    }

    public void setOpen(boolean isOpen, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        screen.refreshScreen(device);

        boolean currentOpen = isOpen(screen);

        if (isOpen) {
            if (!currentOpen) {
                new LmMatPixel(screen, LmGraphics.BUTTON_MENU).click(device);
            }
        } else {
            if (currentOpen) {
                new LmMatPixel(screen, LmGraphics.BUTTON_MENU_CLOSE).click(device);
            }
        }

        if (isOpen) {
            logger.debug("메뉴 열림", device);
        } else {
            logger.debug("메뉴 닫힘", device);
        }
    }
}