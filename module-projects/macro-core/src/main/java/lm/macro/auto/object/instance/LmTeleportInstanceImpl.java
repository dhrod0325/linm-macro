package lm.macro.auto.object.instance;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.graphics.LmTeleportGraphics;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmMatPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.object.pixel.LmPortalPixel;
import lm.macro.auto.object.pixel.impl.LmMap;
import lm.macro.auto.object.pixel.impl.LmMenuButton;
import lm.macro.auto.object.pixel.impl.LmPixels;
import lm.macro.auto.utils.LmCommonUtils;
import lm.macro.auto.utils.LmGameScreenUtils;

public class LmTeleportInstanceImpl implements LmTeleportInstance {
    private LmLog logger = new LmLog(getClass());

    private LmMap mapPixel = new LmMap();

    private LmMenuButton menuButton = new LmMenuButton();

    public LmTeleportInstanceImpl() {
    }

    public void openTeleport(LmAndroidDevice device, LmAndroidScreen screen) {
        try {
            screen.refreshScreen(device);
            LmCommonUtils.sleep(200);

            menuButton.setOpen(false, device, screen);
            LmCommonUtils.sleep(200);

            mapPixel.click(device);
            LmCommonUtils.sleep(500);

            for (int i = 0; i < 8; i++) {
                device.swipe(76, 200, 76, 600);
                logger.trace("텔레포트 스크롤 제일 위로 올리는중 횟수: " + i, device);
                LmCommonUtils.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean toTeleport(LmAndroidDevice device, LmAndroidScreen screen, String name, boolean portalClick) throws Exception {
        if (name.startsWith("퀘스트1")) {
            LmPixels.퀘스트1().click(device);
            LmCommonUtils.sleep(300);
            LmPixels.확인버튼().click(device);
            return true;
        } else if (name.startsWith("퀘스트2")) {
            LmPixels.퀘스트2().click(device);
            LmCommonUtils.sleep(300);
            LmPixels.확인버튼().click(device);
            return true;
        } else if (name.startsWith("퀘스트3")) {
            LmPixels.퀘스트3().click(device);
            LmCommonUtils.sleep(300);
            LmPixels.확인버튼().click(device);
            return true;
        } else {
            LmCommonUtils.sleep(1000);
            openTeleport(device, screen);
            return _toTeleport(device, screen, name, portalClick);
        }
    }

    private boolean _toTeleport(LmAndroidDevice device, LmAndroidScreen screen, String name, boolean portalClick) throws Exception {
        double percent = 0.83;

        for (int i = 0; i < LmCommon.TELEPORT_FIND_MAX_COUNT; i++) {
            screen.refreshScreen(device);
            LmCommonUtils.sleep(500);

            if (!LmGameScreenUtils.isTeleportScreen(screen)) {
                logger.debug("텔레포트 화면 아니라서 중지함");
                return false;
            }

            LmMatPixel data = new LmMatPixel(screen, new LmTeleportGraphics().getByName(name).toMat());
            LmPixelData pos = screen.findPixelMatch(data.getMat(), percent);

            if (pos.isExists()) {
                data.click(device);

                logger.debug(String.format("%s 클릭 성공", name), device);

                screen.refreshScreen(device);
                LmCommonUtils.sleep(LmCommon.REFRESH_SLEEP);

                LmPixels.텔레포트이동버튼().click(device);

                if (portalClick) {
                    LmCommonUtils.sleep(1000);

                    screen.refreshScreen(device);

                    LmCommonUtils.sleep(1500);

                    LmPortalPixel portalPixel = new LmPortalPixel(screen);
                    if (portalPixel.getPixelData().isExists()) {
                        portalPixel.click(device);
                    }
                }

                return true;
            } else {
                device.swipe(76, 400, 76, 332);

                LmCommonUtils.sleep(3000);
            }
        }

        return false;
    }
}