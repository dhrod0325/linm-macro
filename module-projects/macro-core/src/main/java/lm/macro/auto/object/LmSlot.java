package lm.macro.auto.object;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.object.pixel.LmPixel;

import java.util.HashMap;
import java.util.Map;

public class LmSlot {
    private static final int SLOT_DISTANCE = 50;
    private static final int SLOT_Y = 390;
    private static final int SLOT_WIDTH = 43;
    private static final int SLOT_HEIGHT = 45;

    public static Map<SlotType, LmPixel> slotData = new HashMap<SlotType, LmPixel>() {
        {
            int SLOT_1_START_X = 320;
            put(SlotType.SLOT1, new LmCustomPixel(SLOT_1_START_X, SLOT_Y, SLOT_WIDTH, SLOT_HEIGHT));

            SLOT_1_START_X += SLOT_DISTANCE;
            put(SlotType.SLOT2, new LmCustomPixel(SLOT_1_START_X, SLOT_Y, SLOT_WIDTH, SLOT_HEIGHT));

            SLOT_1_START_X += SLOT_DISTANCE;
            put(SlotType.SLOT3, new LmCustomPixel(SLOT_1_START_X, SLOT_Y, SLOT_WIDTH, SLOT_HEIGHT));

            SLOT_1_START_X += SLOT_DISTANCE;
            put(SlotType.SLOT4, new LmCustomPixel(SLOT_1_START_X, SLOT_Y, SLOT_WIDTH, SLOT_HEIGHT));

            int SLOT_2_START_X = 580;
            put(SlotType.SLOT5, new LmCustomPixel(SLOT_2_START_X, SLOT_Y, SLOT_WIDTH, SLOT_HEIGHT));

            SLOT_2_START_X += SLOT_DISTANCE;
            put(SlotType.SLOT6, new LmCustomPixel(SLOT_2_START_X, SLOT_Y, SLOT_WIDTH, SLOT_HEIGHT));

            SLOT_2_START_X += SLOT_DISTANCE;
            put(SlotType.SLOT7, new LmCustomPixel(SLOT_2_START_X, SLOT_Y, SLOT_WIDTH, SLOT_HEIGHT));

            SLOT_2_START_X += SLOT_DISTANCE;
            put(SlotType.SLOT8, new LmCustomPixel(SLOT_2_START_X, SLOT_Y, SLOT_WIDTH, SLOT_HEIGHT));
        }
    };

    public static void useSlot(int slotPage, LmAndroidDevice device, LmSlot.SlotType slotType) {
        try {
            slotData.get(slotType).click(device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum SlotType {
        SLOT1, SLOT2, SLOT3, SLOT4,
        SLOT5, SLOT6, SLOT7, SLOT8
    }
}
