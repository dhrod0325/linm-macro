package lm.macro.auto.android.screen;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.data.model.process.LmAdbProcess;
import lm.macro.auto.manager.process.LmAdbProcessManager;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

public class LmLocalAndroidScreen extends LmAbstractAndroidScreen {
    @Resource
    private LmAdbProcessManager adbProcessManager;

    public void setAdbProcessManager(LmAdbProcessManager adbProcessManager) {
        this.adbProcessManager = adbProcessManager;
    }

    public void loadScreenFromBuffer(BufferedImage img) throws IOException {
        setScreenShotIO(img);
        File file = new File(fileDir, fileName);
        ImageIO.write(img, "png", file);
        Mat mat = Imgcodecs.imread(file.getAbsolutePath());
        setOpencvScreenShot(mat);
    }

    @Override
    public void refreshScreen(LmAndroidDevice device) throws Exception {
        try {
            dirCheck(device);

            LmAdbProcess process = adbProcessManager.getAdbProcessByHostPort(device.getPort());

            if (process != null) {
                logger.trace("스크린 로드 ... ", device);
                BufferedImage img = captureByTitle(process.getTitle());
                BufferedImage bufferedImage = img.getSubimage(0, 36, 800, 480);

                loadScreenFromBuffer(bufferedImage);
            }
        } catch (RasterFormatException e) {
            e.printStackTrace();
            logger.error("화면 최소화상태입니다..");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private BufferedImage captureByTitle(String processTitle) throws Exception {
        WinDef.HWND window = User32.INSTANCE.FindWindow(null, processTitle);
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(window, rect);
        Robot robot = new Robot();
        return robot.createScreenCapture(rect.toRectangle());
    }

    @Override
    public void destroy() {
    }
}
