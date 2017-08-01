package lm.macro.auto.object.pixel.impl;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.object.pixel.LmColorRangePixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.pixel.LmColorCheck;
import lm.macro.auto.utils.pixel.LmColorCheckType;

/**
 * Created by dhrod0325 on 2017-07-15.
 */
public class LmAttackCheck extends LmColorRangePixel {
    @Override
    public LmColorCheck red() {
        return new LmColorCheck(186, LmColorCheckType.ABOVE);
    }

    @Override
    public LmColorCheck green() {
        return new LmColorCheck(80, LmColorCheckType.ABOVE);
    }

    @Override
    public LmColorCheck blue() {
        return new LmColorCheck(190, LmColorCheckType.ABOVE);
    }

    @Override
    public LmPixelData getPixelData() {
        return new LmPixelData(322, 130);
    }

    @Override
    public int getWidth() {
        return 160;
    }

    @Override
    public int getHeight() {
        return 182;
    }

    //공격당하면 xx번 슬롯 사용 (순간,귀환 택1하면됨)
    public boolean isAttack(LmAndroidScreen screen) {
        return percent(screen) > 0.3;
    }
}
