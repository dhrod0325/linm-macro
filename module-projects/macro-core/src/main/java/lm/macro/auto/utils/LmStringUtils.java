package lm.macro.auto.utils;

import lm.macro.auto.constants.LmMotionClickSlot;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class LmStringUtils {
    public static String replaceInvalidFileAndDirectoryName(String name) {
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    public static List<String> enumValuesStringList(Class _enum) {
        List<String> result = new ArrayList<>();
        Object[] enums = _enum.getEnumConstants();

        for (Object o : enums) {
            result.add(o.toString());
        }

        return result;
    }

    public static int findPortFromSerialName(String serial) {
        try {
            serial = serial.replace("-", ":");
            URI u = new URI("temp://" + serial);

            if (serial.contains("emulator")) {
                return u.getPort() + 1;
            } else {
                return u.getPort();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
