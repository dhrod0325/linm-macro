package lm.macro.auto.data.model.screen;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public interface LmScreenShot {
    BufferedImage getScreenShotIO();

    Mat getCvScreenShot();

    void refreshScreen(LmAndroidDevice device) throws Exception;

    void destroy();
}