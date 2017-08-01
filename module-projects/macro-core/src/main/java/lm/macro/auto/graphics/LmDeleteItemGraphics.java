package lm.macro.auto.graphics;

import lm.macro.auto.common.LmCommon;
import org.springframework.stereotype.Component;

@Component
public class LmDeleteItemGraphics extends LmAbstractGraphics {
    public LmDeleteItemGraphics() {
        add(LmCommon.철괴, "item_1.jpg");
        add(LmCommon.초록물약, "item_2.jpg");
        add(LmCommon.가죽, "item_3.jpg");
        add(LmCommon.보석, "item_4.jpg");
        add(LmCommon.천, "item_5.jpg");
    }
}
