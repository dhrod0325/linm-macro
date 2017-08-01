package lm.macro.auto.log;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.spring.app.LmAppContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.Map;

public class LmLog {
    private static final String LOG_DEBUG = "DEBUG";
    private static final String LOG_ERROR = "ERROR";
    private static final String LOG_INFO = "INFO";
    private static final String LOG_TRACE = "TRACE";

    private Log logger;

    private SimpMessagingTemplate simpMessagingTemplate;

    public LmLog(Class clazz) {
        this.logger = LogFactory.getLog(clazz);
        this.simpMessagingTemplate = LmAppContext.getBean(SimpMessagingTemplate.class);
    }

    private String deviceMessage(LmAndroidDevice device, Object message) {
        return String.format("[%s] ", device.getSerial()) + message;
    }

    public void debug(Object message, LmAndroidDevice device) {
        logger.debug(deviceMessage(device, message));

        triggerLog(new LmLogMessage(LOG_DEBUG, message, device));
    }

    public void debug(Object message) {
        logger.debug(message);

        triggerLog(new LmLogMessage(LOG_DEBUG, message));
    }

    public void info(Object message) {
        logger.info(message);
        triggerLog(new LmLogMessage(LOG_INFO, message));
    }

    public void info(Object message, LmAndroidDevice device) {
        logger.info(deviceMessage(device, message));
        triggerLog(new LmLogMessage(LOG_INFO, message, device));
    }

    public void error(Object message) {
        logger.error(message);
        triggerLog(new LmLogMessage(LOG_ERROR, message));
    }

    public void error(Object message, LmAndroidDevice device) {
        logger.error(deviceMessage(device, message));
        triggerLog(new LmLogMessage(LOG_ERROR, message, device));
    }

    private void triggerLog(LmLogMessage logMessage) {
        if (logger.isDebugEnabled() && logMessage.getMessage() instanceof Exception) {
            ((Exception) logMessage.getMessage()).printStackTrace();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("message", logMessage);
        data.put("type", logMessage.getType());
        data.put("time", System.currentTimeMillis());

        if (simpMessagingTemplate != null)
            simpMessagingTemplate.convertAndSend("/socket/triggerLog", data);
    }

    public void trace(String message) {
        logger.trace(message);
    }

    public void trace(String message, LmAndroidDevice device) {
        logger.trace(message);
        triggerLog(new LmLogMessage(LOG_TRACE, message, device));
    }
}