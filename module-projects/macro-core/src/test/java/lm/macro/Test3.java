package lm.macro;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lm.macro.auto.common.LmCommon;

import java.io.File;

public class Test3 {
    public static void main(String[] args) throws Exception {
        String bip = LmCommon.SOURCE_PATH + "/beep-04.mp3";
        Media sound = new Media(bip);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}