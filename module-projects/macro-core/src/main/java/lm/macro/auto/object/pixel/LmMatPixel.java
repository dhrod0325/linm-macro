package lm.macro.auto.object.pixel;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import org.opencv.core.Mat;

public class LmMatPixel extends LmPixel {
    private String name;

    private Mat mat;
    private double matchPercent = 0.7;

    private LmAndroidScreen screen;
    private LmPixelData pixelMatch;

    private int width;
    private int height;

    public LmMatPixel(Mat mat) {
        this.mat = mat;
    }

    public LmMatPixel(Mat mat, double matchPercent) {
        this.mat = mat;
        this.matchPercent = matchPercent;
    }

    public LmMatPixel(LmAndroidScreen screen, Mat mat) {
        this.mat = mat;
        this.screen = screen;

        init(screen);
    }

    public LmMatPixel(LmAndroidScreen screen, Mat mat, double matchPercent) {
        this.mat = mat;
        this.matchPercent = matchPercent;

        init(screen);
    }

    public LmMatPixel(String name, Mat mat) {
        this.name = name;
        this.mat = mat;
    }

    public LmMatPixel(String name, LmAndroidScreen screen, Mat mat) {
        this.name = name;
        this.mat = mat;
        this.screen = screen;
    }

    public LmMatPixel(String name, LmAndroidScreen screen, Mat mat, double matchPercent) {
        this(name, screen, mat);

        this.screen = screen;
    }

    public LmAndroidScreen getScreen() {
        return screen;
    }

    public void setScreen(LmAndroidScreen screen) {
        init(screen);
    }

    @Override
    public LmPixelData getPixelData() {
        return pixelMatch;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void init(LmAndroidScreen screen) {
        try {
            this.screen = screen;
            this.pixelMatch = screen.findPixelMatch(mat, matchPercent);

            this.width = mat.width();
            this.height = mat.height();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void click(LmAndroidDevice device, boolean refreshScreen) {
        try {
            if (refreshScreen) {
                screen.refreshScreen(device);
                init(screen);
            }

            click(device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}