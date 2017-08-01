package lm.macro.auto.utils;

import lm.macro.auto.log.LmLog;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.pixel.LmPixelUtils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class LmCvUtils {
    private static LmLog logger = new LmLog(LmCvUtils.class);

    public static LmPixelData findMatchScreen(Mat screen, Mat item) {
        return findMatchScreen(screen, item, 0.7);
    }

    public static LmPixelData findMatchScreen(Mat screen, Mat item, double matchPercent) {
        try {
            int matchMethod = Imgproc.TM_CCOEFF_NORMED;

            int result_cols = screen.cols() - item.cols() + 1;
            int result_rows = screen.rows() - item.rows() + 1;

            if (result_cols > 0 && result_rows > 0) {
                Mat result = new Mat(result_rows, result_cols, CvType.CV_8U);

                Imgproc.matchTemplate(screen, item, result, matchMethod);

                LmPixelData pixelData = new LmPixelData();

                Core.MinMaxLocResult mm = Core.minMaxLoc(result);

                pixelData.setX(mm.maxLoc.x);
                pixelData.setY(mm.maxLoc.y);
                pixelData.setPercent(mm.maxVal);

                if (pixelData.getPercent() >= matchPercent) {
                    logger.trace("픽셀 검색 성공 matchPercent : " + pixelData.getPercent());

                    return pixelData;
                } else {
                    logger.trace("픽셀 검색 실패 matchPercent : " + pixelData.getPercent());

                    return new LmPixelData(0, 0, pixelData.getPercent());
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return new LmPixelData(0, 0, 0);
    }

    public static int findMatchScreens(Mat screen, double matchPercent, Mat[] mats) {
        int resultCount = 0;

        for (Mat mat : mats) {
            LmPixelData result = findMatchScreen(screen, mat, matchPercent);

            if (result.getPercent() > matchPercent) {
                resultCount++;
            }
        }

        return resultCount;
    }

    public static Mat bufferedImageToMat(BufferedImage bufferedImage) throws Exception {
        String tempFileName = System.currentTimeMillis() + ".png";
        File file = new File(tempFileName);
        ImageIO.write(bufferedImage, "png", file);

        Mat result = Imgcodecs.imread(file.getAbsolutePath());

        if (file.canRead())
            file.delete();

        return result;
    }

    public static LmPixelData cropAndFindMatch(BufferedImage bufferedImage, int x, int y, int w, int h, Mat item, double matchPercent) throws Exception {
        BufferedImage findBuffer = LmPixelUtils.crop(bufferedImage, x, y, w, h);

        ImageIO.write(findBuffer, "png", new File(x + "_" + y + "_.png"));

        Mat screen = bufferedImageToMat(findBuffer);

        return findMatchScreen(screen, item, matchPercent);
    }
}
