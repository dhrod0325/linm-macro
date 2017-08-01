package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.LmCommonUtils;
import lm.macro.auto.utils.LmCvUtils;
import lm.macro.auto.utils.pixel.LmPixelUtils;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class LmBag {
    private LmLog logger = new LmLog(getClass());

    private LmCustomPixel[] itemCells = new LmCustomPixel[16];

    int cutScreenX = 570;
    int cutScreenY = 100;
    int margin = 5;

    public LmBag() {
        int startX = cutScreenX;
        int startY = cutScreenY;

        int w = 47;
        int h = 47;

        for (int i = 0; i < itemCells.length; i++) {
            itemCells[i] = new LmCustomPixel(startX, startY, w, h);
            startX += w + margin;

            if (i % 4 == 3) {
                startY += h + margin;
                startX = cutScreenX;
            }
        }
    }

    public boolean isHeavy(LmAndroidScreen screen) throws Exception {
        return screen.findPixelMatch(LmGraphics.BAG_HEAVY, 0.9).isExists();
    }

    public boolean isOpen(LmAndroidScreen screen) throws Exception {
        LmPixelData t = screen.findPixelMatch(LmGraphics.BAG_OPENED, 0.9);
        return t.isExists();
    }

    public void close(LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        setOpen(false, device, screen);
    }

    public void setOpen(boolean isOpen, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        screen.refreshScreen(device);
        boolean currentOpen = isOpen(screen);

        if (isOpen) {
            if (!currentOpen) {
                new LmCustomPixel(626, 21, 18, 24).click(device);
            }
        } else {
            if (currentOpen) {
                new LmCustomPixel(626, 21, 18, 24).click(device);
            }
        }

        if (isOpen) {
            logger.debug("가방 열림", device);
        } else {
            logger.debug("가방 닫힘", device);
        }
    }

    public void scrollTop(LmAndroidDevice device) throws Exception {
        for (int i = 0; i < 3; i++) {
            device.swipe(685, 130, 680, 300, 50);
        }
    }

    public void scrollDown(LmAndroidDevice device) throws Exception {
        device.swipe(685, 300, 680, 130, 3000);
    }

    public void selectTab(LmItemType type, LmAndroidDevice device) throws Exception {
        new LmCustomPixel(570, 70, 30, 16).click(device);
        LmCommonUtils.sleep(200);

        if (type == LmItemType.ALL) {
            new LmCustomPixel(570, 70, 30, 16).click(device);
        } else if (type == LmItemType.ETC) {
            new LmCustomPixel(720, 70, 30, 16).click(device);
        } else if (type == LmItemType.WEAPON) {
            new LmCustomPixel(650, 70, 30, 16).click(device);
        }
    }

    public void openAndScrollTop(LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        LmMenu menu = new LmMenu();
        menu.openBag(device, screen);
        LmCommonUtils.sleep("가방 열림상태", 100);

        //열린 가방 새로고침
        screen.refreshScreen(device);
        LmCommonUtils.sleep("가방 열림상태", 200);

        scrollTop(device);

        LmCommonUtils.sleep(200);
    }

    public LmCustomPixel findItem(int cellIndex, LmItemType itemType, Mat mat, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        LmCustomPixel itemCell = itemCells[cellIndex];
        itemCell.click(device);

        LmCommonUtils.sleep(200);
        screen.refreshScreen(device);

        LmPixelData t = LmCvUtils.cropAndFindMatch(screen.getScreenShotIO(), cutScreenX, cutScreenY - 80, 222, 350, mat, 0.7);

        if (t.isExists()) {
            return itemCell;
        }

        return null;
    }

    /**
     * 아이템찾기
     */
    public LmCustomPixel findItem(LmItemType itemType, Mat mat, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        openAndScrollTop(device, screen);
        selectTab(itemType, device);

        for (int i = 0; i < 4; i++) {
            LmCommonUtils.sleep(200);

            for (int j = 0; j < itemCells.length; j++) {
                LmCustomPixel itemCell = findItem(j, itemType, mat, device, screen);

                if (itemCell != null)
                    return itemCell;
            }

            LmCommonUtils.sleep(200);

            scrollDown(device);
        }

        return null;
    }

    /**
     * 아이템 용해하기
     */
    public void melt(Mat mat, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        LmCustomPixel findItem = findItem(LmItemType.WEAPON, mat, device, screen);

        if (findItem != null) {
            LmCustomPixel detailButton = new LmCustomPixel(711, 360, 64, 25);
            detailButton.click(device);
            LmCommonUtils.sleep(200);

            LmCustomPixel meltButton = new LmCustomPixel(711, 142, 64, 25);
            meltButton.click(device);

            LmCommonUtils.sleep(200);

            LmCommonUtils.okClick(device);
        }
    }

    public static Mat[] numbers = new Mat[]{
            LmGraphics.PAD_NUM_0, LmGraphics.PAD_NUM_1, LmGraphics.PAD_NUM_2,
            LmGraphics.PAD_NUM_3, LmGraphics.PAD_NUM_4, LmGraphics.PAD_NUM_5,
            LmGraphics.PAD_NUM_6, LmGraphics.PAD_NUM_7, LmGraphics.PAD_NUM_8, LmGraphics.PAD_NUM_9
    };

    public int getItemCount(LmCustomPixel findItem, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        screen.refreshScreen(device);

        BufferedImage t = LmPixelUtils.crop(screen.getScreenShotIO(), (int) findItem.getPixelData().getX() + 8, (int) findItem.getPixelData().getY() + 5, findItem.getWidth(), findItem.getHeight());

        int num40000 = 0;
        int num30000 = 0;
        int num20000 = 0;
        int num10000 = 0;

        int num1000 = 0;
        int num100 = 0;
        int num10 = 0;
        int num1 = 0;

        for (int i = 0; i < LmBag.numbers.length; i++) {
            Mat m = LmBag.numbers[i];

            LmPixelData a1 = LmCvUtils.cropAndFindMatch(t, 27, 32, 20, 13, LmGraphics.ITEM_NUM_10000, 0.9);
            LmPixelData a2 = LmCvUtils.cropAndFindMatch(t, 27, 32, 20, 13, LmGraphics.ITEM_NUM_20000, 0.9);
            LmPixelData a3 = LmCvUtils.cropAndFindMatch(t, 27, 32, 20, 13, LmGraphics.ITEM_NUM_30000, 0.9);
            LmPixelData a4 = LmCvUtils.cropAndFindMatch(t, 27, 32, 20, 13, LmGraphics.ITEM_NUM_40000, 0.9);

            if (a1.isExists()) {
                num10000 = 10000;
                break;
            }
            if (a2.isExists()) {
                num20000 = 20000;
                break;
            }
            if (a3.isExists()) {
                num30000 = 30000;
                break;
            }
            if (a4.isExists()) {
                num40000 = 40000;
                break;
            }

            LmPixelData a = LmCvUtils.cropAndFindMatch(t, 23, 32, 5, 12, m, 0.8);
            LmPixelData b = LmCvUtils.cropAndFindMatch(t, 28, 32, 5, 12, m, 0.8);
            LmPixelData c = LmCvUtils.cropAndFindMatch(t, 33, 32, 5, 12, m, 0.8);
            LmPixelData d = LmCvUtils.cropAndFindMatch(t, 38, 32, 5, 12, m, 0.8);

            if (a.isExists()) {
                num1000 = i * 1000;
            }

            if (b.isExists()) {
                num100 = i * 100;
            }

            if (c.isExists()) {
                num10 = i * 10;
            }

            if (d.isExists()) {
                num1 = i;
            }
        }

        return num40000 + num30000 + num20000 + num10000 + num1000 + num100 + num10 + num1;
    }

    /**
     * 아이템 삭제하기
     */
    public void delete(Mat mat, int count, LmAndroidDevice device, LmAndroidScreen screen) throws Exception {
        //아이템을 가저오고 삭제 행진행
        LmCustomPixel findItem = findItem(LmItemType.ETC, mat, device, screen);

        if (findItem != null) {
            LmCustomPixel deleteButton = new LmCustomPixel(640, 360, 55, 25);

            deleteButton.click(device);
            LmCommonUtils.sleep(500);
            screen.refreshScreen(device);

            LmNumberPad p = new LmNumberPad(device);
            p.enterNumber(count);
            p.ok();
        }

        close(device, screen);

        LmCommonUtils.sleep(300);

        LmPixels.메뉴버튼().click(device);
    }

    public enum LmItemType {
        ALL("전체"),
        WEAPON("장비"),
        ETC("기타");

        String name;

        LmItemType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
