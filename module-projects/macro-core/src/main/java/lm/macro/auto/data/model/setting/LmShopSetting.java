package lm.macro.auto.data.model.setting;


import lm.macro.auto.data.model.item.LmBuyItem;

import java.util.ArrayList;
import java.util.List;


public class LmShopSetting {
    private String deviceSerial;

    private List<LmBuyItem> buyItems = new ArrayList<>();

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public List<LmBuyItem> getBuyItems() {
        return buyItems;
    }

    public void setBuyItems(List<LmBuyItem> buyItems) {
        this.buyItems = buyItems;
    }
}
