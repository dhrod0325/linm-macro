package lm.macro.auto.server;

import lm.macro.auto.log.LmLog;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;

//@Component
public class LmServerCommunicationScheduler {
    private LmLog logger = new LmLog(getClass());

    @Resource
    private LmConnectedDeviceManager connectedDeviceManager;

    @Resource
    private LmServerCommunicationService serverCommunicationService;

    @Scheduled(fixedDelay = 1000)
    public void schedule() {
        long time = System.currentTimeMillis();

        List<LmConnectedDeviceHolder> devices = connectedDeviceManager.getConnectedDeviceList();

        for (LmConnectedDeviceHolder deviceHolder : devices) {
            if (deviceHolder.getRunTime() == -1)
                deviceHolder.setRunTime(System.currentTimeMillis());

            try {
                File file = new File("screenShot.png");
                ImageIO.write(deviceHolder.getPcInstance().getScreen().getScreenShotIO(), "png", file);
                serverCommunicationService.sendToImageFile(deviceHolder.getIpAddress().getHostAddress(), file);

                if (file.exists())
                    file.delete();

            } catch (NullPointerException ignored) {

            } catch (Exception e) {
                e.printStackTrace();
            }
//            deviceHolder.getPcInstance().getScreen().getScreenShotIO()
        }
    }
}
