package lm.macro.auto.object.instance;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.log.LmLog;
import lm.macro.spring.app.LmAppContext;

public abstract class LmAbstractInstance implements LmAiInstance {
    protected LmLog logger = new LmLog(getClass());

    protected LmPcState state = LmPcState.STOP;

    protected LmAndroidDevice device;

    public LmAbstractInstance() {
        LmAppContext.autowireBean(this);
    }

    @Override
    public void setState(LmPcState state) {
        this.state = state;
    }

    @Override
    public LmPcState getState() {
        return state;
    }

    public LmAndroidDevice getDevice() {
        return device;
    }

    @Override
    public void setDevice(LmAndroidDevice device) {
        this.device = device;
    }
}
