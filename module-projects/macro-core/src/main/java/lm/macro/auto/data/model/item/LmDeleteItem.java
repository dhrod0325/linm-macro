package lm.macro.auto.data.model.item;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LmDeleteItem {
    @Id
    private String name;

    private int deleteCount;

    private String deviceSerial;

    public LmDeleteItem() {
    }

    public LmDeleteItem(String deviceSerial, String name, int deleteCount) {
        this.name = name;
        this.deleteCount = deleteCount;
        this.deviceSerial = deviceSerial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(int deleteCount) {
        this.deleteCount = deleteCount;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }
}
