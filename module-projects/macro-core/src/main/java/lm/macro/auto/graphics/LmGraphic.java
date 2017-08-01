package lm.macro.auto.graphics;

import lm.macro.auto.common.LmCommon;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class LmGraphic {
    private String name;
    private String imageName;

    public LmGraphic() {
    }

    public LmGraphic(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Mat toMat() {
        return Imgcodecs.imread(LmCommon.IMAGE_PATH + imageName);
    }
}
