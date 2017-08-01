package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.android.screen.LmLocalAndroidScreen;
import lm.macro.auto.utils.LmCommonUtils;

public class LmMenu {
    private LmMenuButton menuButton = new LmMenuButton();

    private LmBag bag = new LmBag();

    public boolean isOpen(LmAndroidScreen screen) throws Exception {
        return menuButton.isOpen(screen);
    }

    public void openMenu(LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        menuButton.setOpen(true, device, screen);
    }

    public void closeMenu(LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        menuButton.setOpen(false, device, screen);

    }

    public void openBag(LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        if (!isOpen(screen)) {
            openMenu(device, screen);
            LmCommonUtils.sleep(300);
        }

        bag.setOpen(true, device, screen);
    }

    public void closeBag(LmAndroidDevice device, LmLocalAndroidScreen screen) throws Exception {
        bag.setOpen(false, device, screen);
    }
}
