package lm.macro.auto.android.device.model;

import java.io.File;
import java.io.InputStream;

public interface LmAndroidDevice {
    String getSerial();

    int getPort();

    void pull(String remoteFile, File localFile) throws Exception;

    InputStream shell(String... command) throws Exception;

    void tap(double x, double y) throws Exception;

    void swipe(double x1, double y1, double x2, double y2, long delay) throws Exception;

    void swipe(double x1, double y1, double x2, double y2) throws Exception;

}