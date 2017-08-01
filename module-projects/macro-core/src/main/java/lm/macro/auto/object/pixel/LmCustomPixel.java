package lm.macro.auto.object.pixel;

public class LmCustomPixel extends LmPixel {
    private int width;
    private int height;
    private LmPixelData pixelData;

    public LmCustomPixel() {
    }

    public LmCustomPixel(int x, int y, int width, int height) {
        setPixelData(new LmPixelData(x, y));
        setWidth(width);
        setHeight(height);
    }

    @Override
    public LmPixelData getPixelData() {
        return pixelData;
    }

    public void setPixelData(LmPixelData pixelData) {
        this.pixelData = pixelData;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
