package lm.macro.auto.packet;

/**
 * Created by dhrod0325 on 2017-07-09.
 */
public class LmPacketUtils {
    public static int read2ByteData(byte[] bytes) {
        double a = (bytes[0] & 0xff);
        double b = (bytes[1] << 8 & 0xffff);

        if (bytes[1] == 1) {
            return (int) a;
        } else if (bytes[1] == 2) {
            return (int) ((b / 4) + a);
        } else if (bytes[1] == 3) {
            return (int) ((b / 3) + a);
        } else if (bytes[1] == 4) {
            return (int) ((b / 2.7) + a);
        } else if (bytes[1] == 5) {
            return (int) ((b / 2.5) + a);
        } else if (bytes[1] == 6) {
            return (int) ((b / 2.4) + a);
        } else if (bytes[1] == 7) {
            return (int) ((b / 2.3) + a);
        } else if (bytes[1] == 8) {
            return (int) ((b / 2.2) + a);
        } else if (bytes[1] == 9) {
            return (int) ((b / 2.2) + a);
        } else if (bytes[1] == 10) {
            return (int) ((b / 2.2) + a);
        } else if (bytes[1] < 10) {
            return (int) ((b / 1.2) + a);
        } else {
            return (int) ((b / 3) + a);
        }
    }
}
