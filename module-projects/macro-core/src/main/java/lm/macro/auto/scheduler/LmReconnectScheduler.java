package lm.macro.auto.scheduler;

import lm.macro.auto.log.LmLog;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class LmReconnectScheduler {
    private LmLog logger = new LmLog(getClass());

    @Resource
    private LmConnectedDeviceManager connectedDeviceManager;

    @Scheduled(fixedDelay = 3000)
    public void schedule() {
        long time = System.currentTimeMillis();

        List<LmConnectedDeviceHolder> devices = connectedDeviceManager.getConnectedDeviceList();

        for (LmConnectedDeviceHolder deviceHolder : devices) {
            if (deviceHolder.getRunTime() == -1)
                deviceHolder.setRunTime(System.currentTimeMillis());


            //재접속 프로세스 작성
        }
    }
}
