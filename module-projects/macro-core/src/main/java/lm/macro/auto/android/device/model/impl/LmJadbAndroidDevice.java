package lm.macro.auto.android.device.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.log.LmLog;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.RemoteFile;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

/**
 * @deprecated
 */
public class LmJadbAndroidDevice implements LmAndroidDevice {
    private LmLog logger = new LmLog(getClass());

    private JadbDevice device;

    public LmJadbAndroidDevice(JadbDevice device) {
        this.device = device;
    }

    @JsonIgnore
    public JadbDevice getDevice() {
        return device;
    }

    @Override
    public String getSerial() {
        String serial = device.getSerial();

        try {
            if (serial.contains("emulator")) {
                serial = serial.replace("-", ":");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serial;
    }

    @Override
    public int getPort() {
        try {
            String serial = getSerial();

            URI u = new URI("temp://" + serial);

            if (serial.contains("emulator")) {
                return u.getPort() + 1;
            } else {
                return u.getPort();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void pull(String remoteFile, File localFile) throws Exception {
        device.pull(new RemoteFile(remoteFile), localFile);
    }

    @Override
    public InputStream shell(String... command) throws Exception {
        String c = Joiner.on(" ").join(command);

        logger.trace(c);

        return device.executeShell(c);
    }

    @Override
    public void tap(double x, double y) throws Exception {
        shell(String.format("input tap %f %f", x, y));
        logger.trace(String.format("click : %f %f", x, y));
    }

    @Override
    public void swipe(double x1, double y1, double x2, double y2, long delay) throws Exception {
        shell(String.format("input swipe %f %f %f %f %d", x1, y1, x2, y2, delay));
    }

    @Override
    public void swipe(double x1, double y1, double x2, double y2) throws Exception {
        shell(String.format("input swipe %f %f %f %f ", x1, y1, x2, y2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LmAndroidDevice) {
            ((LmAndroidDevice) obj).getSerial().equals(getSerial());
        }

        return super.equals(obj);
    }
}
