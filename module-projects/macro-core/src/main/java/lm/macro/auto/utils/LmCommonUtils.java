package lm.macro.auto.utils;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmCustomPixel;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LmCommonUtils {
    private static LmLog logger = new LmLog(LmCommonUtils.class);

    public static void sleep(String reason, long time) {
        try {

            if (!StringUtils.isEmpty(reason)) {
                logger.debug("[" + reason + "] " + time + "ms 대기");
            } else {
                logger.debug(time + "ms 대기");
            }

            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(long time) {
        sleep("", time);
    }

    public static void okClick(LmAndroidDevice device) throws Exception {
        new LmCustomPixel(410, 380, 121, 25).click(device);
    }

    public static void saveScreenShot(
            LmAndroidDevice device,
            BufferedImage image,
            String dir) throws IOException {
        File saveScreenShotDir = new File(LmCommon.SOURCE_PATH + "/emulators/" + LmStringUtils.replaceInvalidFileAndDirectoryName(device.getSerial()) + "/" + dir);

        if (!saveScreenShotDir.isDirectory()) {
            saveScreenShotDir.mkdirs();
        }

        String name = new SimpleDateFormat("yyyy-MM-dd HH시mm분").format(new Date()) + ".png";
        File screenShot = new File(saveScreenShotDir, name);
        ImageIO.write(image, "png", screenShot);
    }
}