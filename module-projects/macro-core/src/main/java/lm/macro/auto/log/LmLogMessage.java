package lm.macro.auto.log;

import lm.macro.auto.android.device.model.LmAndroidDevice;

/**
 * Created by dhrod0325 on 2017-07-09.
 */
public class LmLogMessage {
    private String type;
    private Object message;
    private LmAndroidDevice device;

    public LmLogMessage() {
    }

    public LmLogMessage(String type, Object message) {
        this.type = type;
        this.message = message;
    }

    public LmLogMessage(String type, Object message, LmAndroidDevice device) {
        this.type = type;
        this.message = message;
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public LmAndroidDevice getDevice() {
        return device;
    }

    public void setDevice(LmAndroidDevice device) {
        this.device = device;
    }
}
