package lm.macro.auto.object.instance;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.object.LmSlot;
import lm.macro.auto.object.pixel.LmMatPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.object.pixel.impl.LmJoystick;
import lm.macro.auto.utils.LmCommonUtils;
import lm.macro.auto.utils.LmGameScreenUtils;

public class LmWarehouseInstance {
    public LmPixelData findWareHouse(LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        LmJoystick joystick = new LmJoystick();

        LmPixelData find = null;

        for (int i = 0; i < LmCommon.FIND_WAREHOUSE_MAX_COUNT; i++) {
            screen.refreshScreen(device);
            find = screen.findPixelMatch(LmGraphics.NPC_WAREHOUSE, 0.7);

            if (find.isExists()) {
                break;
            } else {
                joystick.swipeTop(device, 100, 3500);
                joystick.swipeRight(device, 100, 1000);

                screen.refreshScreen(device);
                find = screen.findPixelMatch(LmGraphics.NPC_WAREHOUSE, 0.7);

                if (find.isExists()) {
                    openWareHouse(device, screen);
                    break;
                } else {
                    LmSlot.useSlot(0, device, LmSlot.SlotType.SLOT8);
                    LmCommonUtils.sleep("상점찾기 실패 귀환주문서 사용 대기", 700);
                }
            }
        }

        if (find.isExists()) {
            return find;
        }

        return find;
    }

    public void openWareHouse(LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        LmPixelData wareHouse = findWareHouse(device, screen);

        if (wareHouse.isExists()) {
            LmMatPixel wareHouseImage = new LmMatPixel(screen, LmGraphics.NPC_WAREHOUSE);
            wareHouseImage.iconClick(device);

            LmGameScreenUtils.skipTalk(device);

            LmCommonUtils.sleep("창고 열리는 시간 대기...", 1500);
        }
    }
}