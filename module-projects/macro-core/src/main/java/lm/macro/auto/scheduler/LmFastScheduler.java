package lm.macro.auto.scheduler;

import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.data.model.setting.LmHuntSetting;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import lm.macro.auto.manager.screen.LmScreenManager;
import lm.macro.auto.object.instance.LmAiInstance;
import lm.macro.auto.object.instance.LmPcInstance;
import lm.macro.auto.object.instance.LmPcState;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class LmFastScheduler {
    @Resource
    private LmScreenManager screenManager;

    @Resource
    private LmConnectedDeviceManager connectedDeviceManager;

    protected LmLog logger = new LmLog(getClass());

    @Scheduled(fixedDelay = 200)
    public void schedule() {
        List<LmConnectedDeviceHolder> devices = connectedDeviceManager.getConnectedDeviceList();

        for (LmConnectedDeviceHolder deviceHolder : devices) {
            try {
                LmAiInstance is = deviceHolder.getPcInstance();

                if (is != null && is instanceof LmPcInstance) {
                    LmPcInstance instance = (LmPcInstance) is;

                    if (instance.getState() == LmPcState.PLAY) {
                        LmAndroidScreen screen = screenManager.getScreen(deviceHolder.getDevice());

                        if (instance.isDamaged(screen)) {
                            if (LmHuntSetting.DAMAGED_MOTION_HOME.equals(instance.getHuntSetting().getDamagedMotion())) {
                                instance.startHomeAndShopping(screen);
                            } else if (LmHuntSetting.DAMAGED_MOTION_TELEPORT.equals(instance.getHuntSetting().getDamagedMotion())) {
                                instance.damagedTeleport(screen);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
