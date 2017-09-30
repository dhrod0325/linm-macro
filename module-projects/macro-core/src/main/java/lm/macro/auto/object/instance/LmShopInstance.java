package lm.macro.auto.object.instance;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.data.model.item.LmBuyItem;
import lm.macro.auto.graphics.LmGraphic;
import lm.macro.auto.graphics.LmShopItemGraphics;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.object.pixel.LmMatPixel;
import lm.macro.auto.object.pixel.LmNpcShopPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.object.pixel.impl.LmPixels;
import lm.macro.auto.utils.LmCommonUtils;
import lm.macro.auto.utils.LmGameScreenUtils;

import java.util.List;

public class LmShopInstance {
    private LmLog logger = new LmLog(getClass());

    private LmCustomPixel cart1Button = LmPixels.상점구매1();

    private LmCustomPixel cart10Button = LmPixels.상점구매10();

    private LmCustomPixel cart100Button = LmPixels.상점구매100();

    private LmCustomPixel cart1000Button = LmPixels.상점구매1000();

    private LmCustomPixel buyButton = LmPixels.상점구매버튼();

    private LmCustomPixel buyOkButton = LmPixels.상점구매확인버튼();

    private LmNpcShopPixel npcShopPixel = new LmNpcShopPixel();

    public boolean startBuyItem(List<LmBuyItem> items, LmAndroidDevice device, LmAndroidScreen screen) {
        return npcShopPixel.click(device, screen, 5, () -> {
            try {
                screen.refreshScreen(device);
                LmCommonUtils.sleep(200);
                buyItem(items, device, screen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private LmMatPixel findItem(LmAndroidDevice device, LmAndroidScreen screen, LmGraphic buyItem) {
        try {
            for (int i = 0; i < 5; i++) {
                screen.refreshScreen(device);
                LmCommonUtils.sleep(500);

                if (!LmGameScreenUtils.isShopScreen(screen, device)) {
                    logger.debug("현재상태 상점 아님 정지함");
                    break;
                }

                LmMatPixel matPixel = new LmMatPixel(screen, buyItem.toMat());
                LmPixelData pixelData = screen.findPixelMatch(matPixel.getMat(), 0.92);

                if (!pixelData.isExists()) {
                    device.swipe(135, 294, 135, 206);
                    LmCommonUtils.sleep(2000);
                    logger.debug("실패 재시도 ..");
                } else {
                    return matPixel;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void scrollUp(LmAndroidDevice device) throws Exception {
        for (int i = 0; i < 3; i++) {
            device.swipe(76, 200, 76, 600);
            LmCommonUtils.sleep(50);
            logger.debug("스크롤 올림...");
        }
    }

    public void buyItem(List<LmBuyItem> items, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        if (!LmGameScreenUtils.isShopScreen(screen, device)) {
            logger.error("상점 모드 아님 상점구매 중지...", device);
            return;
        }

        logger.debug("구매할 물품목록의 수 : " + items.size());

        if (items.isEmpty()) {
            return;
        }

        logger.debug("물품 구매 시작 ... ", device);

        int delay = 300;

        for (LmBuyItem item : items) {
            if (!LmGameScreenUtils.isShopScreen(screen, device)) {
                logger.error("상점 모드 아님 상점구매 중지...", device);
                return;
            }

            if (item.getBuyCount() <= 0) {
                continue;
            }

            screen.refreshScreen(device);

            try {
                LmCommonUtils.sleep(delay);

                double count = item.getBuyCount() - 1;

                LmGraphic buyItem = new LmShopItemGraphics().getByName(item.getName());
                LmMatPixel findPixel = findItem(device, screen, buyItem);

                for (int i = 0; i < 3; i++) {
                    logger.debug(String.format("%s 아이템검색 시도 %d회.", buyItem.getName(), i + 1));

                    scrollUp(device);

                    findPixel = findItem(device, screen, buyItem);

                    if (findPixel != null) {
                        break;
                    }
                }

                if (findPixel == null) {
                    logger.debug(String.format("%s 아이템을 찾지 못하여 스킵합니다.", buyItem.getName()));
                    continue;
                }

                findPixel.click(device);

                LmCommonUtils.sleep(delay);

                int count1000 = (int) Math.floor(count / 1000);
                int count100 = (int) Math.floor(count % 1000 / 100);
                int count10 = (int) Math.floor(count % 100 / 10);
                int count1 = (int) Math.floor(count % 100 % 10);

                for (int i = 0; i < count1000; i++) {
                    cart1000Button.click(device);
                    LmCommonUtils.sleep(delay);
                }

                for (int i = 0; i < count100; i++) {
                    cart100Button.click(device);
                    LmCommonUtils.sleep(delay);
                }

                for (int i = 0; i < count10; i++) {
                    cart10Button.click(device);
                    LmCommonUtils.sleep(delay);
                }

                for (int i = 0; i < count1; i++) {
                    cart1Button.click(device);
                    LmCommonUtils.sleep(delay);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!LmGameScreenUtils.isShopScreen(screen, device)) {
            logger.error("상점 모드 아님 상점구매 중지...", device);
            return;
        }

        buyButton.click(device);

        LmCommonUtils.sleep(delay);

        buyOkButton.click(device);

        LmCommonUtils.sleep(delay);

        LmPixels.메뉴버튼().click(device);
    }
}
