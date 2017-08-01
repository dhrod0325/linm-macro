package lm.macro.auto.android.device.service.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.device.service.LmAndroidDeviceService;

import java.util.List;

public abstract class LmAbstractAndroidDeviceService implements LmAndroidDeviceService {
    @Override
    public LmAndroidDevice getDeviceByPort(int port) {
        List<LmAndroidDevice> devices = getDeviceList();

        for (LmAndroidDevice device : devices) {
            if (device.getPort() == port) {
                return device;
            }
        }

        return null;
    }
}
