package lm.macro.auto.android.device.service.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.device.model.impl.LmAdbAndroidDevice;
import lm.macro.auto.utils.LmAdbUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LmAdbAndroidService extends LmAbstractAndroidDeviceService {
    @Override
    public List<LmAndroidDevice> getDeviceList() {
        List<LmAndroidDevice> deviceList = new ArrayList<>();

        try {
            List<LmAdbUtils.DeviceInfo> list = LmAdbUtils.getDevices();

            for (LmAdbUtils.DeviceInfo device : list) {
                try {
                    deviceList.add(new LmAdbAndroidDevice(device.getSerial()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deviceList;
    }
}
