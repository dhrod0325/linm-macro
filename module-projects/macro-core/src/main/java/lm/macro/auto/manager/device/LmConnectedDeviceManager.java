package lm.macro.auto.manager.device;

import com.google.common.collect.Lists;
import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.event.LmDeviceConnectEvent;
import lm.macro.auto.log.LmLog;
import lm.macro.security.LmUser;
import lm.macro.security.LmUserDetailsHelper;
import lm.macro.spring.app.LmAppContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LmConnectedDeviceManager {
    private LmLog logger = new LmLog(getClass());

    private final List<LmConnectedDeviceHolder> connectedDeviceList = new ArrayList<>();

    public void connect(LmAndroidDevice... devices) {
        for (LmAndroidDevice device : devices) {
            LmConnectedDeviceHolder holder = new LmConnectedDeviceHolder(device);
            connect(holder);
        }
    }

    public void connect(LmConnectedDeviceHolder... devices) {
        for (LmConnectedDeviceHolder deviceHolder : devices) {
            connect(deviceHolder);
        }
    }

    public void connect(LmConnectedDeviceHolder device) {
        synchronized (connectedDeviceList) {
            if (!connectedDeviceList.contains(device)) {
                logger.debug("디바이스 연결 : " + device.toString(), device.getDevice());

                device.setConnect(true);
                connectedDeviceList.add(device);

                LmAppContext.publishEvent(new LmDeviceConnectEvent(this, LmDeviceConnectEvent.EVENT_CONNECT, device));
            }
        }

    }

    public void disConnect(LmConnectedDeviceHolder device) {
        synchronized (connectedDeviceList) {
            if (connectedDeviceList.contains(device)) {
                logger.debug("디바이스 연결 정지 : " + device.toString(), device.getDevice());

                device.setConnect(false);

                connectedDeviceList.remove(device);

                LmAppContext.publishEvent(new LmDeviceConnectEvent(this, LmDeviceConnectEvent.EVENT_DISCONNECT, device));
            }
        }
    }

    public List<LmConnectedDeviceHolder> getConnectedDeviceList() {
        synchronized (connectedDeviceList) {
            return Lists.newArrayList(connectedDeviceList);
        }
    }

    public LmConnectedDeviceHolder getConnectedDeviceByPort(int port) {
        synchronized (connectedDeviceList) {
            for (LmConnectedDeviceHolder holder : connectedDeviceList) {
                if (holder.getDevice().getPort() == port) {
                    return holder;
                }
            }
        }

        return null;
    }

    public LmConnectedDeviceHolder getConnectedDeviceBySerial(String serial) {
        synchronized (connectedDeviceList) {
            for (LmConnectedDeviceHolder holder : connectedDeviceList) {
                if (serial.equals(holder.getDevice().getSerial())) {
                    return holder;
                }
            }
        }

        return null;
    }
}