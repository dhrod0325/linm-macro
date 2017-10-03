package lm.macro.auto.android.screen;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class LmFileScreen extends LmAbstractAndroidScreen {
    private String fileName;

    private BufferedImage img;

    public LmFileScreen(String fileName) throws Exception {
        this.fileName = fileName;
        this.img = ImageIO.read(new File(fileName));
    }

    @Override
    public void refreshScreen(LmAndroidDevice device) throws Exception {
        setScreenShotIO(img);
        File file = new File(fileDir, fileName);
        ImageIO.write(img, "png", file);
        Mat mat = Imgcodecs.imread(file.getAbsolutePath());
        setOpencvScreenShot(mat);
    }

    @Override
    public void destroy() {

    }
}