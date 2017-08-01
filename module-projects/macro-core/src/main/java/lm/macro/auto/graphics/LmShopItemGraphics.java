package lm.macro.auto.graphics;

import lm.macro.auto.common.LmCommon;
import org.springframework.stereotype.Component;

@Component
public class LmShopItemGraphics extends LmAbstractGraphics {
    public LmShopItemGraphics() {
        add(LmCommon.은화살, "shop_arrow.jpg");

        add(LmCommon.빨간물약, "shop_potion_red.jpg");

        add(LmCommon.주홍물약, "shop_potion_orange.jpg");

        add(LmCommon.초록물약, "shop_potion_green.jpg");

        add(LmCommon.강화초록물약, "shop_potion_green2.jpg");

        add(LmCommon.용기의물약, "shop_potion_brave.jpg");

        add(LmCommon.와퍼, "shop_waffer.jpg");

        add(LmCommon.파란물약, "shop_potion_blue.jpg");

        add(LmCommon.해독제, "shop_poison_potion.jpg");

        add(LmCommon.힘센한우스테이크, "shop_cooking1.jpg");

        add(LmCommon.날쌘연어찜, "shop_cooking2.jpg");

        add(LmCommon.영리한칠면조, "shop_cooking3.jpg");

        add(LmCommon.변신주문서, "shop_paper_change.jpg");

        add(LmCommon.순간이동주문서, "shop_paper_teleport.jpg");

        add(LmCommon.귀환주문서, "shop_paper_home.jpg");

        add(LmCommon.혈맹귀환주문서, "shop_paper_blood_home.jpg");

        add(LmCommon.인형소환주문서, "shop_paper_doll.jpg");

        add(LmCommon.정령옥, "shop_elemental_stone.jpg");

        add(LmCommon.마력의돌, "shop_magic_stone.jpg");

        add(LmCommon.숫돌, "shop_fix_stone.jpg");

        add(LmCommon.에바의축복, "shop_potion_eva.jpg");

        add(LmCommon.용해제, "shop_solvent.jpg");
    }
}
