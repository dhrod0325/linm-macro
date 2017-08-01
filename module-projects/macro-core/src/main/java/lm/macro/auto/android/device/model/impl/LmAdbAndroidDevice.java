package lm.macro.auto.android.device.model.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.data.model.item.LmBuyItem;
import lm.macro.auto.utils.LmAdbUtils;
import lm.macro.auto.utils.LmStringUtils;

import javax.persistence.OneToMany;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class LmAdbAndroidDevice implements LmAndroidDevice {
    private String serial;

    @OneToMany
    private List<LmBuyItem> buyItems;

    public List<LmBuyItem> getBuyItems() {
        return buyItems;
    }

    public void setBuyItems(List<LmBuyItem> buyItems) {
        this.buyItems = buyItems;
    }

    public LmAdbAndroidDevice() {
    }

    public LmAdbAndroidDevice(String serial) {
        this.serial = serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public String getSerial() {
        return serial;
    }

    @Override
    public int getPort() {
        return LmStringUtils.findPortFromSerialName(getSerial());
    }

    @Override
    public void pull(String remoteFile, File localFile) throws Exception {
    }

    @Override
    public InputStream shell(String... command) throws Exception {
        LmAdbUtils.shell(serial, command);
        return null;
    }

    @Override
    public void tap(double x, double y) throws Exception {
        LmAdbUtils.tap(serial, x, y);
    }

    @Override
    public void swipe(double x1, double y1, double x2, double y2, long delay) throws Exception {
        LmAdbUtils.swipe(serial, x1, y1, x2, y2, delay);
    }

    @Override
    public void swipe(double x1, double y1, double x2, double y2) throws Exception {
        LmAdbUtils.swipe(serial, x1, y1, x2, y2);
    }
}
