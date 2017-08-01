package lm.macro.web.controller.common;

import com.google.common.collect.Lists;
import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.device.service.LmAndroidDeviceService;
import lm.macro.auto.android.screen.LmSyncAndroidScreen;
import lm.macro.auto.data.model.process.LmAdbProcess;
import lm.macro.auto.data.service.LmAdbProcessRepositoryService;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.device.LmConnectedDeviceManager;
import lm.macro.auto.manager.process.LmAdbProcessManager;
import lm.macro.auto.utils.pixel.LmPixelUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/common")
public class LmCommonController {
    @Resource
    private LmConnectedDeviceManager connectedDeviceManager;

    @Resource
    private LmAndroidDeviceService androidDeviceService;

    @Resource
    private LmAdbProcessManager processManager;

    @Resource
    private LmAdbProcessRepositoryService processRepositoryService;

    @RequestMapping("/getAdbProcessList")
    public List<LmAdbProcess> getAdbProcessList() throws Exception {
        List<LmConnectedDeviceHolder> connectedDeviceList = connectedDeviceManager.getConnectedDeviceList();
        List<LmAdbProcess> processList = processManager.getProcessList(true);

        processRepositoryService.save(processList);

        List<LmAdbProcess> result = new ArrayList<>();

        for (LmAdbProcess process : processList) {
            boolean find = false;

            for (LmConnectedDeviceHolder holder : Lists.newArrayList(connectedDeviceList)) {
                if (holder.getDevice() == null) {
                    connectedDeviceList.remove(holder);
                    continue;
                }

                if (holder.getDevice().getPort() == process.getHostPort()) {
                    find = true;
                    break;
                }
            }

            if (!find) {
                result.add(process);
            }
        }

        return result;
    }

    @RequestMapping("/getConnectedDeviceList")
    public List<LmConnectedDeviceHolder> getConnectedDeviceList() {
        return connectedDeviceManager.getConnectedDeviceList();
    }

    @RequestMapping("/connectDevice")
    public List<LmConnectedDeviceHolder> connectDevice(LmAdbProcess process) {
        LmAndroidDevice device = androidDeviceService.getDeviceByPort(process.getHostPort());

        connectedDeviceManager.connect(device);

        return getConnectedDeviceList();
    }

    @RequestMapping("/disConnectDevice")
    public List<LmConnectedDeviceHolder> disConnectDevice(int port) {
        LmConnectedDeviceHolder device = connectedDeviceManager.getConnectedDeviceByPort(port);
        connectedDeviceManager.disConnect(device);
        return getConnectedDeviceList();
    }

    @RequestMapping("/getDeviceScreen")
    public byte[] getDeviceScreen(int port) throws Exception {
        LmConnectedDeviceHolder device = connectedDeviceManager.getConnectedDeviceByPort(port);

        LmSyncAndroidScreen syncAndroidScreen = new LmSyncAndroidScreen();
        syncAndroidScreen.setFileName("myDeviceScreen.png");
        syncAndroidScreen.refreshScreen(device.getDevice());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(syncAndroidScreen.getScreenShotIO(), "png", outputStream);
        outputStream.flush();
        byte[] imageInByte = outputStream.toByteArray();
        outputStream.close();

        return imageInByte;
    }

    @RequestMapping("/getDeviceScreenCrop")
    public void getDeviceScreenCrop(
            @RequestParam("x") int x,
            @RequestParam("y") int y,
            @RequestParam("w") int w,
            @RequestParam("h") int h,
            @RequestParam("port") int port,
            HttpServletResponse response) throws Exception {
        LmConnectedDeviceHolder device = connectedDeviceManager.getConnectedDeviceByPort(port);
        LmSyncAndroidScreen syncAndroidScreen = new LmSyncAndroidScreen();
        syncAndroidScreen.setFileName("myDeviceScreen.png");
        syncAndroidScreen.refreshScreen(device.getDevice());

        BufferedImage bi = LmPixelUtils.crop(syncAndroidScreen.getScreenShotIO(), x, y, w, h);

        File cropFile = new File("getDeviceScreenCrop.png");
        ImageIO.write(bi, "png", cropFile);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline; filename=\"cropImage.png\"");
        response.setContentLength((int) cropFile.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(cropFile));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        cropFile.delete();
    }
}