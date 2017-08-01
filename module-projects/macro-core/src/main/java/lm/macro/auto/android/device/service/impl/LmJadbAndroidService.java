package lm.macro.auto.android.device.service.impl;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.device.model.impl.LmJadbAndroidDevice;
import org.springframework.beans.factory.annotation.Autowired;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated
 */
public class LmJadbAndroidService extends LmAbstractAndroidDeviceService {
    private JadbConnection connection;

    @Autowired(required = false)
    public void setConnection(JadbConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<LmAndroidDevice> getDeviceList() {
        List<LmAndroidDevice> deviceList = new ArrayList<>();

        try {
            List<JadbDevice> list = connection.getDevices();

            for (JadbDevice device : list) {
                try {
                    deviceList.add(new LmJadbAndroidDevice(device));
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
