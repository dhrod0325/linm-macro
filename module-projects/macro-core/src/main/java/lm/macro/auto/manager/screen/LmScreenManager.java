package lm.macro.auto.manager.screen;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.log.LmLog;
import lm.macro.spring.app.LmAppContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LmScreenManager {
    private final Map<String, LmAndroidScreen> screenHashMap = new HashMap<>();
    private LmLog logger = new LmLog(getClass());

    public LmAndroidScreen getScreen(LmAndroidDevice device) {
        synchronized (screenHashMap) {
            try {
                if (device != null) {
                    screenHashMap.computeIfAbsent(device.getSerial(), k -> LmAppContext.getBean("initScreen"));
                    return screenHashMap.get(device.getSerial());
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return null;
    }
}