package lm.macro.auto.graphics;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.object.pixel.LmPixelData;

public class LmVillageGraphics extends LmAbstractGraphics {
    public LmVillageGraphics() {
        add(LmCommon.글루딘마을, "village_01.jpg");
        add(LmCommon.켄트마을, "village_02.jpg");
        add(LmCommon.은기사마을, "village_03.jpg");
        add(LmCommon.우드벡마을, "village_04.jpg");
        add(LmCommon.기란마을, "village_05.jpg");
        add(LmCommon.하이네마을, "village_06.jpg");
        add(LmCommon.화전민마을, "village_07.jpg");
        add(LmCommon.웰던마을, "village_08.jpg");
        add(LmCommon.오렌마을, "village_09.jpg");
        add(LmCommon.아덴마을, "village_10.jpg");
        add(LmCommon.요정숲마을, "village_11.jpg");
        add(LmCommon.아지트, "village_12.jpg");
    }

    public boolean isInVillage(String villageName, LmAndroidScreen screen) throws Exception {
        LmGraphic graphic = getByName(villageName);
        LmPixelData match = screen.findPixelMatch(graphic.toMat(), 0.85);
        return match.isExists();
    }

    public LmGraphic isInVillage(LmAndroidScreen screen) throws Exception {
        for (LmGraphic graphic : graphics) {
            LmPixelData match = screen.findPixelMatch(graphic.toMat(), 0.85);
            if (match.isExists()) {
                return graphic;
            }
        }

        return null;
    }
}
