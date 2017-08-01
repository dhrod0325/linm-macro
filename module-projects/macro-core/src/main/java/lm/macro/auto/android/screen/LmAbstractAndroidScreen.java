package lm.macro.auto.android.screen;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.LmCvUtils;
import lm.macro.auto.utils.LmStringUtils;
import org.opencv.core.Mat;
import org.springframework.beans.factory.InitializingBean;

import java.awt.image.BufferedImage;
import java.io.File;

public abstract class LmAbstractAndroidScreen implements LmAndroidScreen, InitializingBean {
    protected LmLog logger = new LmLog(getClass());

    private BufferedImage screenShotIO;
    private Mat opencvScreenShot;

    protected String fileDir;

    protected String fileName;

    protected void dirCheck(LmAndroidDevice device) {
        this.fileDir = LmCommon.SOURCE_PATH + "/emulators/" + LmStringUtils.replaceInvalidFileAndDirectoryName(device.getSerial());

        File dir = new File(fileDir);

        if (!dir.isDirectory())
            dir.mkdirs();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (fileName == null) {
            throw new Exception("fileName null 일수 없습니다.");
        }
    }

    @Override
    public BufferedImage getScreenShotIO() {
        return screenShotIO;
    }

    public void setScreenShotIO(BufferedImage screenShotIO) {
        this.screenShotIO = screenShotIO;
    }

    @Override
    public Mat getCvScreenShot() {
        return opencvScreenShot;
    }

    public void setOpencvScreenShot(Mat opencvScreenShot) {
        this.opencvScreenShot = opencvScreenShot;
    }

    @Override
    public LmPixelData findPixelMatch(Mat icon, double matchPercent) throws Exception {
        return LmCvUtils.findMatchScreen(getCvScreenShot(), icon, matchPercent);
    }

    @Override
    public LmPixelData findPixelMatch(Mat icon) throws Exception {
        return LmCvUtils.findMatchScreen(getCvScreenShot(), icon);
    }

    @Override
    public int findPixelMatches(double matchPercent, Mat... icon) throws Exception {
        return LmCvUtils.findMatchScreens(getCvScreenShot(), matchPercent, icon);
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return new File(getFileDir(), fileName);
    }
}
