package lm.macro.auto.data.model.item;

import java.io.Serializable;

public class LmBuyItemId implements Serializable {
    private String deviceSerial;
    private String name;

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
