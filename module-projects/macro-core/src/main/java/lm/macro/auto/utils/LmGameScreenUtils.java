package lm.macro.auto.utils;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmPixelData;

public class LmGameScreenUtils {
    private static LmLog logger = new LmLog(LmGameScreenUtils.class);

    public static void skipTalk(LmAndroidDevice device) throws Exception {
        LmCommonUtils.sleep("대화창 스킵...", 300);
        device.tap(250, 250);
        LmCommonUtils.sleep("대화창 스킵...", 300);
        device.tap(250, 250);
    }

    public static boolean isTeleportScreen(LmAndroidScreen screen) throws Exception {
        LmPixelData match = screen.findPixelMatch(
                LmGraphics.loadImage("game_screen_tele.jpg"), 0.9);
        boolean result = match.isExists();

        if (result) {
            logger.debug("현재상태 텔레포트");
        } else {
            logger.debug("현재상태 텔레포트 아님");
        }

        return result;
    }

    public static boolean isShopScreen(LmAndroidScreen screen) throws Exception {
        String[] matches = {
                "game_screen_shop.jpg",
                "game_screen_shop1.jpg",
                "game_screen_shop2.jpg",
                "game_screen_shop3.jpg"
        };

        boolean result = false;

        for (String m : matches) {
            LmPixelData match = screen.findPixelMatch(LmGraphics.loadImage(m), 0.75);

            if (match.isExists()) {
                result = true;
                break;
            }
        }

        if (result) {
            logger.debug("현재상태 잡탬 상점");
        } else {
            logger.debug("현재상태 잡탬 상점 아님");
        }

        return result;
    }

    public static boolean isNormalGameScreen(LmAndroidScreen screen) throws Exception {
        int data = screen.findPixelMatches(0.7,
                LmGraphics.loadImage("normal1.jpg"),
                LmGraphics.loadImage("normal2.jpg"),
                LmGraphics.loadImage("normal3.jpg"));

        return data == 3;
    }

    public static boolean isMenuOpen(LmAndroidScreen screen) throws Exception {
        LmPixelData t = screen.findPixelMatch(LmGraphics.BUTTON_MENU_OPEN, 0.9);
        return t.isExists();
    }

    public static boolean isRememberBook(LmAndroidScreen screen) throws Exception {
        LmPixelData t = screen.findPixelMatch(LmGraphics.REMEMBER_BOOK_SCREEN, 0.9);

        return t.isExists();
    }

    public static boolean isNcShopScreen(LmAndroidScreen screen) throws Exception {
        LmPixelData t = screen.findPixelMatch(LmGraphics.NC_SHOP_SCREEN, 0.9);

        return t.isExists();
    }

    public static boolean isEqOpen(LmAndroidScreen screen) throws Exception {
        LmPixelData t = screen.findPixelMatch(LmGraphics.OPEN_EQ_SCREEN, 0.9);

        return t.isExists();
    }

    public static boolean isQuestScreen(LmAndroidScreen screen) throws Exception {
        LmPixelData t = screen.findPixelMatch(LmGraphics.QUEST_SCREEN, 0.9);

        return t.isExists();
    }
}