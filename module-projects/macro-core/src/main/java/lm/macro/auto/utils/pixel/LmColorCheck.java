package lm.macro.auto.utils.pixel;

/**
 * Created by dhrod0325 on 2017-07-06.
 */
public class LmColorCheck {
    private int value;
    private LmColorCheckType colorCheckType;

    public LmColorCheck(int value, LmColorCheckType colorCheckType) {
        this.value = value;
        this.colorCheckType = colorCheckType;
    }

    public int getValue() {
        return value;
    }

    public LmColorCheckType getColorCheckType() {
        return colorCheckType;
    }
}
