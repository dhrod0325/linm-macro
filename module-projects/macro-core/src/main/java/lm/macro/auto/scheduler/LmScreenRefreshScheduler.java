//package lm.macro.auto.scheduler;
//
//import lm.macro.auto.android.device.model.LmAndroidDevice;
//import lm.macro.auto.android.screen.LmAbstractAndroidScreen;
//import lm.macro.auto.common.LmCommon;
//import lm.macro.auto.log.LmLog;
//import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
//import lm.macro.auto.manager.device.LmConnectedDeviceManager;
//import lm.macro.auto.manager.screen.LmScreenManager;
//import lm.macro.spring.app.LmAppContext;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.List;
//
////@Component
//public class LmScreenRefreshScheduler {
//    private LmLog logger = new LmLog(getClass());
//
//    @Resource
//    private LmConnectedDeviceManager connectedDeviceManager;
//
//    @Scheduled(initialDelay = 0, fixedDelay = LmCommon.SCREEN_SCHEDULE_TIME)
//    public void refreshScreen() {
//        List<LmConnectedDeviceHolder> devices = connectedDeviceManager.getConnectedDeviceList();
//        for (LmConnectedDeviceHolder deviceHolder : devices) {
//            if (deviceHolder.isReconnect())
//                continue;
//            if (deviceHolder.getDevice() != null && deviceHolder.getAdbProcess() != null) {
//                captureScreenShot(deviceHolder.getDevice());
//            } else {
//                logger.debug("디바이스 제거...");
//                connectedDeviceManager.getConnectedDeviceList().remove(deviceHolder);
//            }
//        }
//    }
//
//    public void captureScreenShot(LmAndroidDevice device) {
//        try {
//            LmAbstractAndroidScreen screen = LmAppContext.getBean("captureScreen");
//            screen.refreshScreen(device);
//            LmScreenManager.getInstance().setScreen(device, screen);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}