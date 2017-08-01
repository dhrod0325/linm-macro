package lm.macro.auto.event;

import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import org.springframework.context.ApplicationEvent;

public class LmDeviceConnectEvent extends ApplicationEvent {
    public static final String EVENT_CONNECT = "EVENT_CONNECT";
    public static final String EVENT_DISCONNECT = "EVENT_DISCONNECT";

    private String eventType;
    private LmConnectedDeviceHolder device;

    public LmDeviceConnectEvent(Object source, String eventType, LmConnectedDeviceHolder device) {
        super(source);
        this.eventType = eventType;
        this.device = device;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LmConnectedDeviceHolder getDevice() {
        return device;
    }

    public void setDevice(LmConnectedDeviceHolder device) {
        this.device = device;
    }
}
