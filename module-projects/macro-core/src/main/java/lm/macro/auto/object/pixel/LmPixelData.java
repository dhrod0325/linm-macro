package lm.macro.auto.object.pixel;

public class LmPixelData {
    private double x = 0;
    private double y = 0;
    private double percent = 0.7;

    public LmPixelData() {
    }

    public LmPixelData(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public LmPixelData(double x, double y, double percent) {
        this.x = x;
        this.y = y;
        this.percent = percent;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "AndroidScreen{" +
                "x=" + x +
                ", y=" + y +
                ", match=" + getPercent() +
                '}';
    }

    public boolean isExists() {
        return x > 0 || y > 0;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
