package lm.macro.auto.android.screen;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.utils.LmAdbUtils;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.InitializingBean;

import javax.imageio.ImageIO;
import java.io.File;

public class LmSyncAndroidScreen extends LmAbstractAndroidScreen implements InitializingBean {
    @Override
    public void refreshScreen(LmAndroidDevice device) throws Exception {
        logger.debug("스크린 로드 ... ", device);
        dirCheck(device);
        String remoteFile = String.format("/mnt/sdcard/%s", fileName);
        File file = LmAdbUtils.screenCap(device.getSerial(), remoteFile, new File(fileDir, fileName).getAbsolutePath());
        initWithFile(file);
    }

    public void initWithFile(File file) {
        try {
            setScreenShotIO(ImageIO.read(file));
            setOpencvScreenShot(Imgcodecs.imread(file.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        File f = getFile();
        if (f.exists()) {
            f.delete();
        }
    }

    public File getFile() {
        return new File(fileDir + "/" + fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}