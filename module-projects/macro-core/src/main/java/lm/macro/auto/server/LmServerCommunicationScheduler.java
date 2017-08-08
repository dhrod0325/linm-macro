package lm.macro.auto.server;

import lm.macro.auto.log.LmLog;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import lm.macro.security.LmUser;
import lm.macro.security.LmUserDetailsHelper;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

//@Component
public class LmServerCommunicationScheduler {
    private LmLog logger = new LmLog(getClass());

    @Resource
    private LmConnectedDeviceManager connectedDeviceManager;

    @Resource
    private LmServerCommunicationService serverCommunicationService;

    @Scheduled(fixedDelay = 1000)
    public void schedule() {
        LmUser user = (LmUser) LmUserDetailsHelper.getUser();

        if (user != null) {

        }
    }
}
