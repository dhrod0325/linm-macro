package lm.macro.auto.graphics;

import lm.macro.auto.common.LmCommon;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class LmGraphics {
    public static final Mat NPC_SHOP_1 = loadImage("npc_shop_1.jpg");
    public static final Mat NPC_SHOP_2 = loadImage("npc_shop_2.jpg");

    public static final Mat NPC_WAREHOUSE = loadImage("npc_warehouse.jpg");

    public static final Mat POTION_EMPTY = loadImage("potion_empty.jpg");

    public static final Mat DUNGEON = loadImage("dungeon.jpg");
    public static final Mat BUTTON_OK = loadImage("button_ok.jpg");

    public static final Mat BUTTON_MENU = loadImage("button_menu.jpg");
    public static final Mat BUTTON_MENU_OPEN = loadImage("button_menu_on.jpg");
    public static final Mat BUTTON_MENU_CLOSE = loadImage("button_menu_close.jpg");

    public static final Mat BAG_OPENED = loadImage("bag_opened.jpg");
    public static final Mat BAG_HEAVY = loadImage("bag_heavy.jpg");
    public static final Mat ATTACK = loadImage("attack.jpg");

    public static final Mat PAD_NUM_0 = loadImage("num0.jpg");
    public static final Mat PAD_NUM_1 = loadImage("num1.jpg");
    public static final Mat PAD_NUM_2 = loadImage("num2.jpg");
    public static final Mat PAD_NUM_3 = loadImage("num3.jpg");
    public static final Mat PAD_NUM_4 = loadImage("num4.jpg");
    public static final Mat PAD_NUM_5 = loadImage("num5.jpg");
    public static final Mat PAD_NUM_6 = loadImage("num6.jpg");
    public static final Mat PAD_NUM_7 = loadImage("num7.jpg");
    public static final Mat PAD_NUM_8 = loadImage("num8.jpg");
    public static final Mat PAD_NUM_9 = loadImage("num9.jpg");

    public static final Mat ITEM_NUM_10000 = loadImage("num10000.jpg");
    public static final Mat ITEM_NUM_20000 = loadImage("num20000.jpg");
    public static final Mat ITEM_NUM_30000 = loadImage("num30000.jpg");
    public static final Mat ITEM_NUM_40000 = loadImage("num40000.jpg");
    public static final Mat IS_DIE = loadImage("die.jpg");
    public static final Mat IS_POISON = loadImage("is_poison.jpg");

    public static final Mat EMPTY_ARR = loadImage("empty_arr.jpg");

    public static Mat loadImage(String image) {
        return Imgcodecs.imread(LmCommon.SOURCE_PATH + "/images/" + image);
    }
}
