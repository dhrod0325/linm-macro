package lm.macro.auto.object.pixel;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.graphics.LmVillageGraphics;
import lm.macro.auto.object.LmSlot;
import lm.macro.auto.object.pixel.impl.LmJoystick;
import lm.macro.auto.object.pixel.impl.LmPixels;
import lm.macro.auto.utils.LmCommonUtils;
import lm.macro.auto.utils.LmGameScreenUtils;

public class LmNpcShopPixel extends LmCustomPixel {
    private LmNpcShopCallback callback;
    private LmJoystick joystick = new LmJoystick();

    private LmVillageGraphics villageGraphics = new LmVillageGraphics();

    public void setCallback(LmNpcShopCallback callback) {
        this.callback = callback;
    }

    @Override
    public double[] clickPosition() {
        double x = getPixelData().getX() + (getWidth() / 2);
        double y = getPixelData().getY() + (getHeight() / 2);

        return new double[]{x, y - 47};
    }

    private LmPixelData findShopLocation(LmAndroidScreen screen, LmAndroidDevice device, int findCount) {
        try {
            for (int i = 0; i < findCount; i++) {
                screen.refreshScreen(device);

                LmCommonUtils.sleep(300);

                if (LmGameScreenUtils.isMenuOpen(screen)) {
                    LmPixels.메뉴버튼().click(device);
                }

                if (!LmGameScreenUtils.isNormalGameScreen(screen)) {
                    return new LmPixelData();
                }

                logger.debug(String.format("상점찾기 시도중... 시도횟수 %d회", i), device);

                LmPixelData shop1Position = screen.findPixelMatch(LmGraphics.NPC_SHOP_1, 0.65);
                LmPixelData shop2Position = screen.findPixelMatch(LmGraphics.NPC_SHOP_2, 0.65);

                if (shop1Position != null && shop1Position.isExists()) {
                    setPixelData(shop1Position);
                    setWidth(LmGraphics.NPC_SHOP_1.width());
                    setHeight(LmGraphics.NPC_SHOP_1.height());

                    return shop1Position;
                } else if (shop2Position != null && shop2Position.isExists()) {
                    setPixelData(shop2Position);
                    setWidth(LmGraphics.NPC_SHOP_2.width());
                    setHeight(LmGraphics.NPC_SHOP_2.height());

                    return shop2Position;
                } else {
                    logger.debug("상점찾기 실패 캐릭 좌표 이동 ... ", device);

                    LmSlot.slotData.get(LmSlot.SlotType.SLOT8).click(device);
                    LmCommonUtils.sleep(100);
                    screen.refreshScreen(device);
                    LmCommonUtils.sleep(100);

                    if (villageGraphics.isInVillage(LmCommon.우드벡마을, screen)) {
                        joystick.swipeRight(device, 100, 2500);
                    } else if (villageGraphics.isInVillage(LmCommon.켄트마을, screen)) {
                        joystick.swipeTop(device, 100, 12000);
                    } else if (villageGraphics.isInVillage(LmCommon.은기사마을, screen)) {
                        joystick.swipeTopRight(device, 100, 3500);
                        joystick.swipeTop(device, 100, 2000);
                    } else if (villageGraphics.isInVillage(LmCommon.하이네마을, screen)) {

                    } else if (villageGraphics.isInVillage(LmCommon.화전민마을, screen)) {

                    } else if (villageGraphics.isInVillage(LmCommon.웰던마을, screen)) {
                        joystick.swipeTop(device, 100, 9000);
                        joystick.swipeRight(device, 100, 1000);
                    } else if (villageGraphics.isInVillage(LmCommon.오렌마을, screen)) {
                        joystick.swipeRight(device, 100, 2000);
                    } else if (villageGraphics.isInVillage(LmCommon.아덴마을, screen)) {
                        joystick.swipeTop(device, 100, 1000);
                    }

                    LmCommonUtils.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean openShop(int count, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        if (LmGameScreenUtils.isMenuOpen(screen)) {
            LmPixels.메뉴버튼().click(device);
        }

        LmPixelData findShop = findShopLocation(screen, device, count);

        if (findShop != null && findShop.isExists()) {
            LmCommonUtils.sleep(300);

            logger.debug("상점 발견... 상점 클릭", device);

            setPixelData(findShop);

            click(device);

            logger.debug("클릭 한 후 상점이 제대로 오픈되었는지 확인 처리", device);

            LmCommonUtils.sleep(200);

            LmPixels.상점에서_장비_탭_버튼().click(device);

            screen.refreshScreen(device);

            LmCommonUtils.sleep(200);

            if (LmGameScreenUtils.isShopScreen(screen)) {
                if (callback != null) {
                    callback.onOpen();
                    return true;
                }
            } else {
                LmPixels.메뉴버튼().click(device);
                joystick.swipeTopLeft(device, 300, 1000);
                openShop(count, device, screen);
            }
        } else {
            logger.debug("상점찾기에 실패하였습니다....");
            return false;
        }

        return true;
    }

    public boolean click(LmAndroidDevice device, LmAndroidScreen screen, int findCount, LmNpcShopCallback callback) {
        try {
            setCallback(callback);
            return openShop(findCount, device, screen);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public interface LmNpcShopCallback {
        void onOpen() throws Exception;
    }
}