package lm.macro.auto.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.log.LmLog;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LmAdbUtils {
    private final static String ADB_FILE = LmCommon.ADB_PATH;

    private final static LmLog logger = new LmLog(LmAdbUtils.class);

    public static synchronized String execCommand(String... commands) throws Exception {
        String c = Joiner.on(" ").join(commands);
        String command = ADB_FILE + " " + c;

        logger.trace("command : " + command);

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        String result = IOUtils.toString(process.getInputStream(), "UTF-8");

        if (result.contains("Error")) {
            logger.error("result : " + result);
        }

        process.destroy();

        return result;
    }

    public static synchronized List<DeviceInfo> getDevices() throws Exception {
        List<DeviceInfo> list = new ArrayList<>();
        String result = execCommand("devices");

        String[] results = result.split("\r\n");

        for (int i = 1; i < results.length; i++) {
            String line = results[i];
            line = line.replaceAll("^ +| +$| (?= )", "");

            String[] k = line.split("\\s");

            String serial = k[0];
            String state = k[1];
            list.add(new DeviceInfo(serial, state));
        }

        return list;
    }

    public static synchronized String shell(String host, String... commands) throws Exception {
        String command = Joiner.on(" ").join(commands);

        return execCommand("-s", host, "shell", command);
    }

    public static synchronized String pull(String host, String fileName) throws Exception {
        return execCommand("-s", host, "pull", fileName);
    }

    public static synchronized String sendEvent(String host, String... commands) throws Exception {
        String command = Joiner.on(" ").join(commands);

        return shell(host, "sendEvent " + command);
    }

    public static synchronized String tap(String host, double x, double y) throws Exception {
        return shell(host, "input", "tap", String.valueOf(x), String.valueOf(y));
    }

    public static synchronized String swipe(String host, double x1, double y1, double x2, double y2, long delay) throws Exception {
        String result = shell(host, "input", "swipe", String.valueOf(x1), String.valueOf(y1), String.valueOf(x2), String.valueOf(y2), String.valueOf(delay));

        long d = delay + 10;
        
        logger.debug("스와이프 딜레이 " + d);

        Thread.sleep(d / 5);

        return result;
    }

    public static synchronized String swipe(String host, double x1, double y1, double x2, double y2) throws Exception {
        return shell(host, "input", "swipe", String.valueOf(x1), String.valueOf(y1), String.valueOf(x2), String.valueOf(y2));
    }

    public static synchronized String swipeTop(String host, double x1, double y1, double SWIPE_LENGTH, long delay) throws Exception {
        return swipe(host, x1, y1, x1, y1 - SWIPE_LENGTH, delay);
    }

    public static synchronized String swipeRight(String host, double x1, double y1, double SWIPE_LENGTH, long delay) throws Exception {
        return swipe(host, x1, y1, x1 + SWIPE_LENGTH, y1, delay);
    }

    public static synchronized String swipeBottom(String host, double x1, double y1, double SWIPE_LENGTH, long delay) throws Exception {
        return swipe(host, x1, y1, x1, y1 + SWIPE_LENGTH, delay);
    }

    public static synchronized String swipeLeft(String host, double x1, double y1, double SWIPE_LENGTH, long delay) throws Exception {
        return swipe(host, x1, y1, x1 - SWIPE_LENGTH, y1, delay);
    }

    public static synchronized File screenCap(String host, String remoteFile, String localFile) throws Exception {
        Stopwatch w = Stopwatch.createStarted();
        LmAdbUtils.shell(host, "screencap", remoteFile);
        LmAdbUtils.pull(host, String.format("%s %s", remoteFile, localFile));
        File file = new File(localFile);
        logger.trace(w.toString());

        return file;
    }

    public static class DeviceInfo {
        private String serial;
        private String state;

        public DeviceInfo(String serial, String state) {
            this.serial = serial;
            this.state = state;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "DeviceInfo{" +
                    "serial='" + serial + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }
    }
}