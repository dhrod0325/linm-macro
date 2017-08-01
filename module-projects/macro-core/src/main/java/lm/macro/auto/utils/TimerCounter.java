package lm.macro.auto.utils;

public class TimerCounter {
    private int counter = 0;

    public boolean waitSecond(int second) {
        boolean result;

        if (++counter % second != 0) {
            result = false;
        } else {
            result = true;
            counter = 0;
        }

        return result;
    }
}
