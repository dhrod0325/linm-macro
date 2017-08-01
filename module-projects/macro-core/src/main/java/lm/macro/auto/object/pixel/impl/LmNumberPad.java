package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.utils.LmCommonUtils;

public class LmNumberPad {
    private LmAndroidDevice device;

    public LmNumberPad(LmAndroidDevice device) {
        this();
        this.device = device;
    }

    private LmCustomPixel[] numberPads = new LmCustomPixel[12];

    public LmNumberPad() {
        int x = 305;
        int y = 99;
        int w = 46;
        int h = 49;

        int marginRight = 3;
        int marginBottom = 8;

        for (int i = 0; i < numberPads.length; i++) {
            numberPads[i] = new LmCustomPixel(x, y, 46, 49);

            x += w + marginRight;

            if (i % 4 == 3) {
                y += h + marginBottom;
                x = 305;
            }
        }
    }

    public void enterNumber(int number) throws Exception {
        int[] r = splitNumber(number);

        for (int input : r) {
            getCursorByNumber(input).click(device);
            LmCommonUtils.sleep(300);
        }
    }

    public void ok() throws Exception {
        new LmCustomPixel(404, 346, 106, 28).click(device);

        LmCommonUtils.sleep(200);

        new LmCustomPixel(410, 380, 121, 25).click(device);

        LmCommonUtils.sleep(200);
    }

    public int[] splitNumber(int targetNumber) {
        String target = targetNumber + "";

        int[] result = new int[target.length()];

        for (int i = 0; i < result.length; i++) {
            result[i] = Character.getNumericValue(target.charAt(i));
        }

        return result;
    }

    public LmCustomPixel getCursorByNumber(int number) {


        if (number == 9) {
            return numberPads[2];
        } else if (number == 8) {
            return numberPads[1];
        } else if (number == 7) {
            return numberPads[0];
        } else if (number == 6) {
            return numberPads[6];
        } else if (number == 5) {
            return numberPads[5];
        } else if (number == 4) {
            return numberPads[4];
        } else if (number == 3) {
            return numberPads[10];
        } else if (number == 2) {
            return numberPads[9];
        } else if (number == 1) {
            return numberPads[8];
        }

        return null;
    }
}
