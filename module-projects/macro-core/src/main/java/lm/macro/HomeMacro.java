package lm.macro;

import lm.macro.auto.common.LmCommon;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.LmCvUtils;
import org.apache.commons.lang3.RandomUtils;
import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class HomeMacro {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.setProperty("java.awt.headless", "false");
    }

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                        BufferedImage capture = new Robot().createScreenCapture(screenRect);

                        String fileName = LmCommon.SOURCE_PATH + "/screen.png";
                        File file = new File(fileName);
                        ImageIO.write(capture, "png", file);

                        LmPixelData st = LmCvUtils.findMatchScreen(
                                Imgcodecs.imread(fileName),
                                LmGraphics.ATTACK, 0.9);

                        LmPixelData enemyMark = LmCvUtils.findMatchScreen(
                                Imgcodecs.imread(fileName),
                                LmGraphics.ENEMY, 0.9);

                        if (st.isExists()) {
                            int mask = InputEvent.BUTTON1_MASK;
                            Robot robot = new Robot();

                            int x = (int) RandomUtils.nextDouble(st.getX() + 13, st.getX() + 39);
                            int y = (int) RandomUtils.nextDouble(st.getY() + 39, st.getY() + 60);

                            robot.mouseMove(x, y);
                            robot.mousePress(mask);
                            robot.mouseRelease(mask);
                        }

                        System.out.println("귀환 메크로 동작중...");

                        Thread.sleep(RandomUtils.nextInt(100, 250));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
