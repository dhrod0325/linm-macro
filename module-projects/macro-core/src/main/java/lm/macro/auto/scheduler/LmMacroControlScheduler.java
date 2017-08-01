package lm.macro.auto.scheduler;

import com.google.common.collect.Maps;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.log.LmLog;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import lm.macro.auto.manager.screen.LmScreenManager;
import lm.macro.auto.object.instance.LmAiInstance;
import lm.macro.auto.object.instance.LmPcState;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LmMacroControlScheduler {
    private LmLog logger = new LmLog(getClass());

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private LmConnectedDeviceManager connectedDeviceManager;

    @Resource
    private TaskExecutor taskExecutor;

    @Resource
    private LmScreenManager screenManager;

    private final Map<LmConnectedDeviceHolder, Runnable> taskList = new HashMap<>();

    @Scheduled(initialDelay = 0, fixedDelay = LmCommon.MACRO_SCHEDULER_TIME)
    public void runMacro() {
        synchronized (taskList) {
            List<LmConnectedDeviceHolder> devices = connectedDeviceManager.getConnectedDeviceList();

            for (LmConnectedDeviceHolder deviceHolder : devices) {
                if (deviceHolder.isReconnect())
                    continue;

                if (!taskList.containsKey(deviceHolder)) {
                    taskList.put(deviceHolder, () -> {
                        while (true) {
                            try {
                                deviceHolder.setRunning(true);
                                schedule(deviceHolder);
                                if (!deviceHolder.isConnect()) {
                                    deviceHolder.setRunning(false);
                                    deviceHolder.getPcInstance().setState(LmPcState.STOP);
                                    Maps.newHashMap(taskList).remove(deviceHolder);
                                    break;
                                }
                                Thread.sleep(LmCommon.MACRO_SCHEDULER_TIME);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e) {
                                logger.error("화면 최소화 상태 예상");
                            }
                        }
                    });
                }
            }

            for (LmConnectedDeviceHolder holder : taskList.keySet()) {
                if (holder.isReconnect())
                    continue;

                if (holder.isConnect() && !holder.isRunning()) {
                    taskExecutor.execute(taskList.get(holder));
                }
            }

            simpMessagingTemplate.convertAndSend("/socket/devices", devices);
        }
    }

    public void schedule(LmConnectedDeviceHolder deviceHolder) {
        try {
            LmAiInstance instance = deviceHolder.getPcInstance();
            LmAndroidScreen screen = screenManager.getScreen(deviceHolder.getDevice());

            if (screen != null && instance != null) {
                screen.refreshScreen(deviceHolder.getDevice());
                instance.ai(screen, System.currentTimeMillis());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
