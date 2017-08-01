package lm.macro.auto.data.model.item;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(LmBuyItemId.class)
public class LmBuyItem implements Serializable {
    @Id
    private String deviceSerial;

    @Id
    private String name;

    private int buyCount;

    public LmBuyItem() {
    }

    public LmBuyItem(String deviceSerial, String name, int buyCount) {
        this.name = name;
        this.buyCount = buyCount;
        this.deviceSerial = deviceSerial;
    }

    public LmBuyItem(String name, int buyCount) {
        this.name = name;
        this.buyCount = buyCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    @Override
    public String toString() {
        return String.format("구매물품 이름 :%s,수량 : %d", name, buyCount);
    }
}
