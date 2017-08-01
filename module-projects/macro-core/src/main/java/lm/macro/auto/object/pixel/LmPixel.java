package lm.macro.auto.object.pixel;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.utils.LmAdbUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class LmPixel {
    protected LmLog logger = new LmLog(getClass());

    public abstract LmPixelData getPixelData();

    public abstract int getWidth();

    public abstract int getHeight();

    public void iconClick(LmAndroidDevice device) throws Exception {
        if (getPixelData() == null || !getPixelData().isExists()) {
            logger.error("픽셀 검색 실패하여 클릭 못함" + ReflectionToStringBuilder.toString(this));
            return;
        }

        double[] clickPosition = clickPosition();
        double x = clickPosition[0];
        double y = clickPosition[1];

        device.tap(x, y - 47);
    }

    public void click(LmAndroidDevice device) throws Exception {
        if (getPixelData() == null || !getPixelData().isExists()) {
            logger.error("픽셀 검색 실패하여 클릭 못함" + ReflectionToStringBuilder.toString(this));
            return;
        }

        double[] clickPosition = clickPosition();
        double x = clickPosition[0];
        double y = clickPosition[1];

        device.tap(x, y);
    }

    public void swipe(LmAndroidDevice device, double x1, double y1, long delay) {
        try {
            double[] clickPosition = clickPosition();
            LmAdbUtils.swipe(device.getSerial(), clickPosition[0], clickPosition[1], x1, y1, delay);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double[] clickPosition() {
        double x = getPixelData().getX() + (getWidth() / 2);
        double y = getPixelData().getY() + (getHeight() / 2);

        return new double[]{x, y};
    }

    public void longPress(LmAndroidDevice device, long delay) {
        swipe(device, getPixelData().getX(), getPixelData().getY(), delay);
    }

    public void swipeTop(LmAndroidDevice device, double distance, long delay) {
        swipe(device, getPixelData().getX(), getPixelData().getY() - distance, delay);
    }

    public void swipeTopRight(LmAndroidDevice device, double distance, long delay) {
        swipe(device, getPixelData().getX() + distance, getPixelData().getY() - distance, delay);
    }

    public void swipeRight(LmAndroidDevice device, double distance, long delay) {
        swipe(device, getPixelData().getX() + distance, getPixelData().getY(), delay);
    }

    public void swipeBottomRight(LmAndroidDevice device, double distance, long delay) {
        swipe(device, getPixelData().getX() + distance, getPixelData().getY() + distance, delay);
    }

    public void swipeBottom(LmAndroidDevice device, double distance, long delay) {
        swipe(device, getPixelData().getX(), getPixelData().getY() + distance, delay);
    }

    public void swipeBottomLeft(LmAndroidDevice device, double distance, long delay) {
        swipe(device, getPixelData().getX() - distance, getPixelData().getY() + distance, delay);
    }

    public void swipeLeft(LmAndroidDevice device, double distance, long delay) {
        swipe(device, getPixelData().getX() - distance, getPixelData().getY(), delay);
    }

    public void swipeTopLeft(LmAndroidDevice device, double distance, long delay) {
        swipe(device, getPixelData().getX() - distance, getPixelData().getY() - distance, delay);
    }
}
