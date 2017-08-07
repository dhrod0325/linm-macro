package lm.macro.auto.manager.device;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.data.model.netstat.LmNetstatProcessHolder;
import lm.macro.auto.data.model.process.LmAdbProcess;
import lm.macro.auto.manager.process.LmAdbProcessManager;
import lm.macro.auto.object.instance.LmAiInstance;
import lm.macro.auto.object.instance.LmPcInstance;
import lm.macro.pcap.util.PcapUtils;
import lm.macro.spring.app.LmAppContext;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class LmConnectedDeviceHolder {
    public static final int SNIFF_PORT = 12000;

    private LmAdbProcessManager adbProcessManager;

    private LmNetstatProcessHolder netstatProcessHolder;

    private LmAndroidDevice device;

    private LmAiInstance aiInstance;

    private InetAddress ipAddress;

    private boolean connect = true;
    private boolean running = false;
    private boolean reconnect = false;

    private long runTime = -1;

    public LmConnectedDeviceHolder() {
        try {
            this.ipAddress = PcapUtils.getLocalHostLANAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void loadNetstat() {
        if (getAdbProcess() != null) {
            this.netstatProcessHolder = getAdbProcess().findNetstatProcess(SNIFF_PORT);
        }
    }

    public LmNetstatProcessHolder getNetstatProcessHolder() {
        return netstatProcessHolder;
    }

    public LmConnectedDeviceHolder(LmAndroidDevice device) {
        this();

        LmAppContext.autowireBean(this);

        this.device = device;

        this.aiInstance = new LmPcInstance();
        this.aiInstance.setDevice(device);

        LmAdbProcess p = getAdbProcess();

        if (p != null) {
            this.aiInstance.setName(p.getTitle());
            getAdbProcess().findNetstatProcess(SNIFF_PORT);
        }
    }

    @Resource
    public void setAdbProcessManager(LmAdbProcessManager adbProcessManager) {
        this.adbProcessManager = adbProcessManager;
    }

    public LmAndroidDevice getDevice() {
        synchronized (this) {
            return device;
        }
    }

    public LmAiInstance getPcInstance() {
        synchronized (this) {
            return aiInstance;
        }
    }

    public LmAdbProcess getAdbProcess() {
        if (adbProcessManager != null)
            return adbProcessManager.getAdbProcessByHostPort(device.getPort());

        return null;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean equals(Object obj) {
        LmConnectedDeviceHolder deviceHolder = (LmConnectedDeviceHolder) obj;

        if (deviceHolder != null && deviceHolder.getDevice() != null && getDevice() != null) {
            return Objects.equals(deviceHolder.getDevice().getSerial(), getDevice().getSerial());
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.format("기기:%s", device.getSerial());
    }

    public boolean isReconnect() {
        return reconnect;
    }

    public void setReconnect(boolean reconnect) {
        this.reconnect = reconnect;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public LmAdbProcessManager adbProcessManager() {
        return adbProcessManager;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
