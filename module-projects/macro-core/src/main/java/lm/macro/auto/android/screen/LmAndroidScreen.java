package lm.macro.auto.android.screen;

import lm.macro.auto.data.model.screen.LmScreenShot;
import lm.macro.auto.object.pixel.LmPixelData;
import org.opencv.core.Mat;

public interface LmAndroidScreen extends LmScreenShot {
    LmPixelData findPixelMatch(Mat icon, double matchPercent) throws Exception;

    LmPixelData findPixelMatch(Mat icon) throws Exception;

    int findPixelMatches(double matchPercent, Mat... icon) throws Exception;
}
