package lm.macro.auto.android.device.service;

import lm.macro.auto.android.device.model.LmAndroidDevice;

import java.util.List;

public interface LmAndroidDeviceService {
    List<LmAndroidDevice> getDeviceList();

    LmAndroidDevice getDeviceByPort(int port);
}
