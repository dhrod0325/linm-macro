package lm.macro;

import lm.macro.auto.android.device.model.LmAndroidDevice;
import lm.macro.auto.android.device.service.impl.LmAdbAndroidService;
import lm.macro.auto.android.screen.LmLocalAndroidScreen;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.manager.device.LmConnectedDeviceHolder;
import lm.macro.auto.manager.process.LmInMemoryAdbProcessManager;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.object.pixel.LmPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.utils.LmCvUtils;
import lm.macro.auto.utils.cmd.ProcessUtils;
import org.opencv.core.Core;

import java.util.List;

public class Test {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void restartDevice(LmConnectedDeviceHolder connectedDeviceHolder) throws Exception {
        connectedDeviceHolder.adbProcessManager().refresh();
        ProcessUtils.killByPid(connectedDeviceHolder.getAdbProcess().getPid());
        Thread.sleep(5000);

        new Thread(() -> {
            try {
                ProcessUtils.execCommand(connectedDeviceHolder.getAdbProcess().getCommandLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) throws Exception {
        LmAdbAndroidService deviceService = new LmAdbAndroidService();
        List<LmAndroidDevice> deviceList = deviceService.getDeviceList();

        LmInMemoryAdbProcessManager adbProcessManager = new LmInMemoryAdbProcessManager();
        adbProcessManager.refresh();

        LmAndroidDevice device = deviceList.get(0);
        LmConnectedDeviceHolder deviceHolder = new LmConnectedDeviceHolder(device);

        LmLocalAndroidScreen screen = new LmLocalAndroidScreen();
        screen.setAdbProcessManager(adbProcessManager);
        screen.setFileDir(device.getSerial());
        screen.setFileName("testScreen.png");
        screen.afterPropertiesSet();
        screen.refreshScreen(deviceHolder.getDevice());

//        for (int i = 0; i < LmCommon.TELEPORT_FIND_MAX_COUNT; i++) {
//            screen.refreshScreen(device);
//            LmCommonUtils.sleep(500);
//            if (!LmGameScreenUtils.isTeleportScreen(screen)) {
//                continue;
//            }
//            LmMatPixel data = new LmMatPixel(screen, new LmTeleportGraphics().getByName("17 ????????? ?????????").toMat());
//            LmPixelData pos = screen.findPixelMatch(data.getMat(), 0.83);
//            if (pos.isExists()) {
//                data.click(device);
//                break;
//            } else {
//                device.swipe(76, 400, 76, 350);
//                LmCommonUtils.sleep(3000);
//            }
//        }
//        LmNpcShopPixel shopPixel = new LmNpcShopPixel();
//        shopPixel.click(device, screen, 5, new LmNpcShopPixel.LmNpcShopCallback() {
//            @Override
//            public void onOpen() throws Exception {
//            }
//        });
//        LmTeleportInstance teleportInstance = new LmTeleportInstanceImpl();
//        teleportInstance.toTeleport(device, screen, "46 ?????? ?????? ????????????", false);
//        teleportInstance.toTeleport(device, screen, "44 ?????? ?????? ????????????", false);

//        LmMatPixel matPixel = new LmMatPixel(screen, Imgcodecs.imread(LmCommon.IMAGE_PATH + "shop_arrow.jpg"));
//        LmPixelData pixelData = screen.findPixelMatch(matPixel.getMat(), 0.92);
//
//        System.out.println(pixelData);
//        LmVillageGraphics villageGraphics = new LmVillageGraphics();
//        System.out.println(villageGraphics.isInVillage(LmCommon.???????????????, screen));
//        LmTeleportInstance teleportInstance = new LmTeleportInstanceImpl();
//        teleportInstance.toTeleport(device, screen, "48 ????????? ??????", false);

//        LmPixelData t = screen.findPixelMatch(LmGraphics.EMPTY_ARR, 0.95);
//        System.out.println(t);

//        new Thread(() -> {
//            while (true) {
//                try {
//                    screen.refreshScreen(deviceHolder.getDevice());
//
//                    LmPixel ???????????? = new LmCustomPixel(660, 427, 98, 26);
//                    ????????????.click(device);
//
//                    Thread.sleep(1000);
//
//                    LmPixelData a = LmCvUtils.cropAndFindMatch(screen.getScreenShotIO(), 170, 140, 500, 86, LmGraphics.DIA, 0.9);
//
//                    if (a.isExists()) {
//                        LmCustomPixel ????????? = new LmCustomPixel(251, 172, 37, 31);
//                        ?????????.click(device);
//                        Thread.sleep(200);
//                        LmCustomPixel ?????? = new LmCustomPixel(680, 386, 85, 22);
//                        ??????.click(device);
//                        Thread.sleep(200);
//                        LmCustomPixel ???????????? = new LmCustomPixel(407, 365, 100, 22);
//                        ????????????.click(device);
//                    }
//
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

//        LmPartyManager partyManager = new LmPartyManager();

//        for (LmAndroidDevice device : deviceList) {
//            LmConnectedDeviceHolder deviceHolder = new LmConnectedDeviceHolder(device);
//
//            LmPcInstance instance = (LmPcInstance) deviceHolder.getPcInstance();
//            partyManager.addParty(instance);
//
//            instance.setPartyManager(partyManager);
//
//            new Thread(() -> {
//                while (true) {
//                    try {
//                        LmLocalAndroidScreen screen = new LmLocalAndroidScreen();
//                        screen.setAdbProcessManager(adbProcessManager);
//                        screen.setFileDir(device.getSerial());
//                        screen.setFileName("testScreen.png");
//                        screen.afterPropertiesSet();
//                        screen.refreshScreen(deviceHolder.getDevice());
//
//                        instance.setState(LmPcState.PLAY);
//                        instance.ai(screen, System.currentTimeMillis());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }


//        List<LmBuyItem> items = new ArrayList<>();
//        items.add(new LmBuyItem(LmCommon.????????????, 2));
//        LmShopInstance shopInstance = new LmShopInstance();
//        shopInstance.startBuyItem(items, device, screen);

//        LmLocalAndroidScreen screen = new LmLocalAndroidScreen();
//        screen.setFileDir("");
//        screen.setFileName("asdf.png");
//        screen.loadScreenFromBuffer(ImageIO.read(new File("dddd.png")));
//
//        LmPixelData t = screen.findPixelMatch(LmGraphics.ATTACK);
//        System.out.println(t);

//        LmBag bag = new LmBag();
//        bag.delete(Imgcodecs.imread("images/item_1.jpg"), 5, device, screen);
//        LmPcInstance pcInstance = new LmPcInstance();
//        while (true) {
//            screen.refreshScreen(device);
//            System.out.println(LmGameScreenUtils.isNormalGameScreen(screen));
//            Thread.sleep(500);
//        }

//        LmVillageGraphics villageGraphics = new LmVillageGraphics();
//        boolean t = villageGraphics.isInVillage("???????????????", screen);
//        System.out.println(t);
//
//        List<LmBuyItem> items = new ArrayList<>();
//        items.add(new LmBuyItem(LmCommon.???????????????, 2));
//        LmShopInstance shopInstance = new LmShopInstance();
//        shopInstance.startBuyItem(items, device, screen);

//
//        LmTeleportInstance teleportInstance = new LmTeleportInstance();
//        teleportInstance.toTeleport(device, screen,"???????????? ?????? ??????", false);

//        LmNpcShopPixel npcShopPixel = new LmNpcShopPixel();
//        npcShopPixel.click(device, 5, () -> {
//            try {
//                Thread.sleep(1000);
//                LmShopInstance shopInstance = new LmShopInstance();
//                List<LmBuyItem> items = new ArrayList<>();
//                items.add(new LmBuyItem("????????????", 2));
//                shopInstance.buyItem(items, device, screen);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

//        List<LmAdbUtils.DeviceInfo> t = LmAdbUtils.getDevices();
//        for (LmAdbUtils.DeviceInfo deviceInfo : t) {
//            System.out.println(deviceInfo + ",./sdf,/.asf,./asdf,/.,/.");
//        }

//        new LmLetter().check(device, screen);
//        LmAuto s = new LmAuto();
////

////
//        LmBag bag = new LmBag();
//        LmCustomPixel findItem = bag.findItem(0, LmBag.LmItemType.ETC, Imgcodecs.imread("images/image3333.jpg"), device, screen);
//        int number = bag.getItemCount(findItem, device, screen);
//        System.out.println(number);
//        LmNumberPad p = new LmNumberPad(device);
//        p.enterNumber(15);


//        bag.melt(Imgcodecs.imread("images/item_w_2.jpg"), device, screen);
//        LmMatPixel matPixel = new LmMatPixel(screen, Imgcodecs.imread("images/item1.jpg"));
//        LmCustomPixel k = bag.findItem(LmBag.LmItemType.ETC, matPixel.getMat(), device, screen);
//        System.out.println(ReflectionToStringBuilder.toString(k));
//        LmCustomPixel[] t = bag.getItemCells();
//        for (LmCustomPixel pixel : t) {
//            System.out.println(ReflectionToStringBuilder.toString(pixel));
//        }

        //        bag.scrollUp(device);
//        bag.scrollUp(device);
//        menu.openBag(device, screen);
//        Thread.sleep(1000);
//        menu.closeBag(device, screen);

//        double t = new LmAttackCheck().percent(screen);
//
//        System.out.println(t);

//        LmWarehouseInstance warehouseInstance = new LmWarehouseInstance();
//        warehouseInstance.findWareHouse(device, screen);
//        LmPixelData t = screen.findPixelMatch(LmGraphics.NPC_WAREHOUSE, 0.7);
//        System.out.println(t);
//        BufferedImage bi = screen.getScreenShotIO();
//        Mat mat = LmCvUtils.bufferedImageToMat(bi);
//        BufferedImage bi2 = LmPixelUtils.crop(bi, 556, 120, 220, 280);
//        ImageIO.write(bi2, "png", new java.io.File("test.png"));

//        LmAuto auto = new LmAuto();
//        while (true) {
//            screen.refreshScreen(device);
//            auto.setBoolean(device, screen, true);
//            Thread.sleep(500);
//        }

//        LmMatPixel potionEmpty = new LmMatPixel(screen, LmGraphics.POTION_EMPTY, 0.99);
//        System.out.println(potionEmpty.getPixelData());
//        System.out.println(new LmVillageGraphics().isInVillage("???????????????", screen));

//        LmBag menuButton = new LmBag();
//        menuButton.setOpen(true, device, screen);
//
//        Thread.sleep(2000);
//
//        screen.refreshScreen(device);
//
//        menuButton.setOpen(false, device, screen);

//        LmShopInstance shopInstance = new LmShopInstance();
//        LmConnectedDeviceHolder connectedDeviceHolder = new LmConnectedDeviceHolder(device);
//        restartDevice(connectedDeviceHolder);
//        Thread.sleep(30000);
//        restartDevice(connectedDeviceHolder);

//        new LmVillageGraphics().isInVillage(screen);
//        LmMatPixel moveButton = new LmMatPixel(screen, LmGraphics.BUTTON_MOVE_OK);
//        System.out.println(moveButton.getPixelData());
//        LmPixelData t = LmCvUtils.findMatchScreen(screen.getCvScreenShot(), LmGraphics.TELE_28);
//        LmAndroidScreen screen = new LmAndroidScreenImpl(new LmSyncAndroidScreen(device.getSerial(), "capture.png"));
//        screen.refreshScreen();

//        LmJadbAndroidService deviceService = new LmJadbAndroidService();
//        deviceService.setConnection(new JadbConnection());
//        List<LmAndroidDevice> s = deviceService.getDeviceList();
//        LmAndroidDevice device = s.get(0);
//        LmConnectedDeviceHolder holder = new LmConnectedDeviceHolder(device);
//        LmInMemoryAdbProcessManager m = new LmInMemoryAdbProcessManager();
//        m.refresh();
//        holder.setAdbProcessManager(m);
//        System.out.println(device.getPort());
//        System.out.println(holder.getAdbProcess());
//
//        LmJoystick joystick = new LmJoystick();
//        joystick.swipeBottomRight(device, 100, 2000);
//        joystick.swipeBottom(device, 100, 2000);
//        joystick.swipeBottomLeft(device, 100, 2000);

//        List<LmAdbProcess> t = LmAdbProcessUtils.getAdbProcessList();
//
//        for (LmAdbProcess p : t) {
//            System.out.println(p.getHostPort());
//        }
    }
}