package lm.macro.auto.object.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lm.macro.auto.android.screen.LmAndroidScreen;
import lm.macro.auto.common.LmCommon;
import lm.macro.auto.constants.LmMotionClickSlot;
import lm.macro.auto.data.model.item.LmBuyItem;
import lm.macro.auto.data.model.item.LmHuntJoystick;
import lm.macro.auto.data.model.item.LmHuntMap;
import lm.macro.auto.data.model.setting.LmHuntSetting;
import lm.macro.auto.data.service.LmBuyItemRepositoryService;
import lm.macro.auto.data.service.LmHuntSettingRepositoryService;
import lm.macro.auto.graphics.LmGraphic;
import lm.macro.auto.graphics.LmGraphics;
import lm.macro.auto.graphics.LmVillageGraphics;
import lm.macro.auto.motion.LmAutoPatternService;
import lm.macro.auto.object.LmSlot;
import lm.macro.auto.object.manager.LmPartyManager;
import lm.macro.auto.object.pixel.LmCustomPixel;
import lm.macro.auto.object.pixel.LmMatPixel;
import lm.macro.auto.object.pixel.LmPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.object.pixel.impl.*;
import lm.macro.auto.packet.items.LmItemUsePacket;
import lm.macro.auto.utils.LmCommonUtils;
import lm.macro.auto.utils.LmCvUtils;
import lm.macro.auto.utils.LmTimeChecker;
import lm.macro.sns.service.KakaoSnsService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LmPcInstance extends LmAbstractInstance {
    @Resource
    private LmAutoPatternService autoPatternService;

    @Resource
    private LmItemUsePacket itemUsePacket;

    @Resource
    private LmHuntSettingRepositoryService huntSettingService;

    @Resource
    private LmBuyItemRepositoryService buyItemRepositoryService;

    @Resource
    private LmBag bag;

    @Resource
    private KakaoSnsService kakaoSnsService;

    private LmShopInstance shopInstance = new LmShopInstance();

    private LmTeleportInstanceImpl teleportInstance = new LmTeleportInstanceImpl();

    private LmVillageGraphics villageGraphics = new LmVillageGraphics();

    private LmAuto autoPixel = new LmAuto();

    private LmSelf selfPixel = new LmSelf();

    private String name;

    private int hp = -1;
    private int mp = -1;
    private double exp = -1;

    private boolean auto = false;

    private boolean self = false;

    private LmTimeChecker autoCheckTime = new LmTimeChecker();

    private LmTimeChecker selfCheckTime = new LmTimeChecker();

    private LmTimeChecker letterCheckTime = new LmTimeChecker();

    private LmTimeChecker poisonCheckTime = new LmTimeChecker();

    private LmTimeChecker commonCheckTime = new LmTimeChecker();

    private LmGraphic village;

    private LmHuntSetting huntSetting;

    private LmDieRestart dieRestart = new LmDieRestart();

    private LmJoystick joystick = new LmJoystick();

    private LmAndroidScreen screen;

    private long macroStartTime = System.currentTimeMillis();

    private double startExp = -9999;

    private int lastHuntMapIndex = -1;

    public int getLastHuntMapIndex() {
        return lastHuntMapIndex;
    }

    public void setLastHuntMapIndex(int lastHuntMapIndex) {
        this.lastHuntMapIndex = lastHuntMapIndex;
    }

    @Override
    @JsonIgnore
    public LmAndroidScreen getScreen() {
        return screen;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void refreshScreen() throws Exception {
        screen.refreshScreen(device);
    }

    private void initialize(LmAndroidScreen screen) {
        try {
            if (huntSettingService != null) {
                huntSetting = huntSettingService.findByDeviceSerial(device.getSerial());
            } else {
                huntSetting = new LmHuntSetting();
            }

            setVillage(villageGraphics.isInVillage(screen));

            this.screen = screen;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ai(LmAndroidScreen screen, long time) throws Exception {
        if (screen == null)
            return;

        initialize(screen);

        if (!commonCheckTime.isWaitTime(time, 5)) {
            logger.debug(String.format("[%s] ?????? ????????? : %s", getName(), getUseItems()));
        }

        if (dieRestart.getDieCount() > 2) {
            state = LmPcState.STOP;
            dieRestart.setDieCount(0);

            return;
        }

        if (state == LmPcState.SHOP) {
            startShopMacro();
        }

        //???????????????????????? ?????????????????? ?????? ??????
        if (state == LmPcState.STOP) {
            return;
        }

        if (state == LmPcState.PLAY) {
            checkLetter(time);

            calcHpAndMp(screen);

            startHunt(screen, time);
        }
    }

    private void startShopMacro() throws Exception {
        LmPixel ???????????? = new LmCustomPixel(660, 427, 98, 26);
        ????????????.click(device);
        Thread.sleep(1000);
        LmPixelData a = LmCvUtils.cropAndFindMatch(screen.getScreenShotIO(), 170, 140, 500, 86, LmGraphics.DIA, 0.9);

        if (a.isExists()) {
            LmCustomPixel ????????? = new LmCustomPixel(251, 172, 37, 31);
            ?????????.click(device);
            Thread.sleep(200);
            LmCustomPixel ?????? = new LmCustomPixel(680, 386, 85, 22);
            ??????.click(device);
            Thread.sleep(200);
            LmCustomPixel ???????????? = new LmCustomPixel(407, 365, 100, 22);
            ????????????.click(device);
        }
    }

    private void poisonCheck(long time, LmAndroidScreen screen) throws Exception {
        if (poisonCheckTime.isWaitTime(time, 1)) {
            return;
        }

        LmPixelData p = screen.findPixelMatch(LmGraphics.IS_POISON, 0.9);

        if (p.isExists()) {
            String motionName = huntSetting.getPoisonUseSlot();

            if (!StringUtils.isEmpty(motionName)) {
                useSlotFromString(motionName);
            }
        }
    }

    private void checkLetter(long time) {
        try {
            if (huntSetting.isUseCheckLetter()) {
                if (!letterCheckTime.isWaitTime(time, 60 * 30)) {
                    new LmLetter().check(device);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startHunt(LmAndroidScreen screen, long time) {
        try {
            if (isInVillage()) {
                if (getHp() < 60) {
                    logger.debug("HP 60% ???????????? ??????????????????.");
                    return;
                }

                if (huntSetting.isUseShopping()) {
                    shoppingStart(screen);
                }

                if (huntSetting.isUseHuntTeleport()) {
                    screen.refreshScreen(device);
                    LmCommonUtils.sleep(500);

                    LmHuntMap map;

                    if (huntSetting.getHuntMapList().size() == 1) {
                        map = huntSetting.getHuntMapList().get(0);
                    } else {
                        while (true) {
                            int mapIndex = RandomUtils.nextInt(0, huntSetting.getHuntMapList().size());

                            if (mapIndex != lastHuntMapIndex) {
                                map = huntSetting.getHuntMapList().get(mapIndex);
                                setLastHuntMapIndex(mapIndex);
                                break;
                            }
                        }
                    }

                    if (teleportInstance.toTeleport(device, screen, map.getName(), !map.isNoDungeon())) {
                        LmCommonUtils.sleep(500);
                        screen.refreshScreen(device);
                        LmCommonUtils.sleep(500);

                        for (LmHuntJoystick huntJoystick : map.getHuntJoysticks()) {
                            int dis = 160;

                            long delay = TimeUnit.SECONDS.toMillis((long) huntJoystick.getJoystickDelay());

                            if (LmCommon.????????????_???.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeTop(device, dis, delay);
                            } else if (LmCommon.????????????_??????.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeTopRight(device, dis, delay);
                            } else if (LmCommon.????????????_???.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeRight(device, dis, delay);
                            } else if (LmCommon.????????????_??????.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeBottomRight(device, dis, delay);
                            } else if (LmCommon.????????????_???.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeBottom(device, dis, delay);
                            } else if (LmCommon.????????????_??????.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeBottomLeft(device, dis, delay);
                            } else if (LmCommon.????????????_???.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeLeft(device, dis, delay);
                            } else if (LmCommon.????????????_??????.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeTopLeft(device, dis, delay);
                            }
                        }

                        autoPixel.click(device);

                        setSelf(screen, true);
                    } else {
                        LmPixels.????????????().click(device);
                    }
                }
            } else {
                boolean isDamaged = isDamaged(screen);

                /*
                if (isDamaged) {
                    if (LmHuntSetting.DAMAGED_MOTION_HOME.equals(huntSetting.getDamagedMotion())) {
                        logger.debug("?????? ???????????? ?????? ?????? ... ", device);
                        saveScreenShot(screen.getScreenShotIO(), "???????????????");
                        startHomeAndShopping(screen);
                        kakaoSnsService.feed("??????", "???????????? ???????????? ?????? ????????????.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");
                        return;
                    } else if (LmHuntSetting.DAMAGED_MOTION_TELEPORT.equals(huntSetting.getDamagedMotion())) {
                        logger.debug("?????? ???????????? ?????? ???????????? ... ", device);
                        saveScreenShot(screen.getScreenShotIO(), "?????????????????????");
                        damagedTeleport(screen);
                        kakaoSnsService.feed("??????", "???????????? ???????????? ????????????????????????.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");
                        return;
                    } else if (LmHuntSetting.DAMAGED_MOTION_ATTACK.equalsIgnoreCase(huntSetting.getDamagedMotion())) {
                    }
                }
                */

                if (huntSetting.isHpGoHome()) {
                    if (hp > 0 && hp < huntSetting.getHpGoHomeValue()) {
                        logger.debug("HP??? ??????????????? ??????????????? ??????...", device);
                        saveScreenShot(screen.getScreenShotIO(), "??????????????????");
                        startHomeAndShopping(screen);
                        kakaoSnsService.feed("??????", "???????????? ???????????? ??????????????????.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");

                        return;
                    }
                }

                if (huntSetting.isRedPotionEmptyGoHome()) {
                    LmMatPixel potionEmpty = new LmMatPixel(screen, LmGraphics.POTION_EMPTY, 0.99);

                    Map<String, Integer> items = getUseItems();
                    items.putIfAbsent(LmCommon.????????????, 0);

                    if (items.get(LmCommon.????????????) < 10 || potionEmpty.getPixelData().isExists()) {
                        logger.debug("????????? ????????? ??????...", device);
                        startHomeAndShopping(screen, huntSetting.isRedPotionEmptyStopMacro());
                        kakaoSnsService.feed("??????", "???????????? ????????? ?????? ??????????????????.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");
                        return;
                    }
                }

                if (huntSetting.isArrowGoHome()) {
                    if (isEmptyArrow(screen)) {
                        buyArrow();
                        return;
                    }

                    Integer ??????????????? = getUseItems().get(LmCommon.?????????);

                    if (??????????????? != null && getUseItems().get(LmCommon.?????????) < 10) {
                        buyArrow();
                        return;
                    }
                }

                if (huntSetting.isHeavyGoHome()) {
                    if (bag.isHeavy(screen)) {
                        logger.debug("?????? ???????????? ??????...", device);
                        startHomeAndShopping(screen);
                    }

                    return;
                }

                if (!isDamaged) {
//                    poisonCheck(time, screen);
                    autoCheck(screen, time);
                    dieRestart.restart(screen, device);
                    executeAutoPatterns();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buyArrow() throws Exception {
        String message = "???????????? ????????? ??????...";
        logger.debug(message, device);
        startHomeAndShopping(screen);
        kakaoSnsService.feed("??????", "???????????? ???????????? ????????? ??????????????????.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");
    }

    public void damagedTeleport(LmAndroidScreen screen) throws Exception {
        for (int i = 0; i < 50; i++) {
            screen.refreshScreen(device);
            LmCommonUtils.sleep(200);

            if (!isDamaged(screen)) {
                return;
            }

            LmSlot.useSlot(0, device, LmSlot.SlotType.SLOT7);
        }
    }

    public void goHomeAndSleep2000(LmAndroidScreen screen) throws Exception {
        for (int i = 0; i < 50; i++) {
            screen.refreshScreen(device);
            LmCommonUtils.sleep(200);
            if (villageGraphics.isInVillage(screen) != null) {
                return;
            }
            LmSlot.useSlot(0, device, LmSlot.SlotType.SLOT8);
        }
    }

    public List<LmBuyItem> checkRealBuyItems() throws Exception {
        Map<String, Integer> useItems = getUseItems();
        List<LmBuyItem> results = new ArrayList<>();
        List<LmBuyItem> buyItems = getBuyItems();

        if (useItems == null)
            return results;

        for (LmBuyItem buyItem : buyItems) {
            LmBuyItem copyItem = new LmBuyItem();
            BeanUtils.copyProperties(buyItem, copyItem);

            int count = 0;

            try {
                count = useItems.get(copyItem.getName());
            } catch (Exception ignored) {
            }

            if (count > 0) {
                int realBuyCount = copyItem.getBuyCount() - count;

                if (LmCommon.?????????.equals(copyItem.getName())) {

                    int arrBuyCount = copyItem.getBuyCount() - (count / 10);

                    if (arrBuyCount > 100) {
                        copyItem.setBuyCount(arrBuyCount);
                    } else {
                        copyItem.setBuyCount(0);
                    }
                } else if (LmCommon.????????????.equals(copyItem.getName()) && realBuyCount < 20) {
                    copyItem.setBuyCount(0);
                } else {
                    copyItem.setBuyCount(realBuyCount);
                }
            }

            if (copyItem.getBuyCount() > 0) {
                results.add(copyItem);
            }
        }

        return results;
    }

    private void shoppingStart(LmAndroidScreen screen) {
        try {
            if (!huntSetting.isUseShopping()) {
                return;
            }

            setState(LmPcState.SHOPPING);

            List<LmBuyItem> buyItems = checkRealBuyItems();

            if (buyItems.size() == 0) {
                setState(LmPcState.PLAY);
                return;
            } else {
                logger.debug(String.format("%d??? ??????????????? ????????? ??????????????? ?????? ?????? ?????? ?????????.", buyItems.size()));
            }

            LmCommonUtils.sleep(500);
            screen.refreshScreen(device);
            LmCommonUtils.sleep(500);

            /*
            * ?????? ????????? ????????? ???????????? ????????????.
            * */
            if (villageGraphics.isInVillage(screen) == null) {
                goHomeAndSleep2000(screen);
            }

            /**
             * ?????? ?????? ?????? ??????
             */
            LmCommonUtils.sleep(1000);
            screen.refreshScreen(device);
            LmCommonUtils.sleep(100);

            boolean buyItem = shopInstance.startBuyItem(buyItems, device, screen);

            if (!buyItem) {
                teleportInstance.toTeleport(device, screen, LmCommon.????????????, false);
            }

            setState(LmPcState.PLAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startHomeAndShopping(LmAndroidScreen screen, boolean stopMacro) throws Exception {
        goHomeAndSleep2000(screen);

        if (stopMacro) {
            setState(LmPcState.STOP);
        } else {
            if (huntSetting.isHomeInStopMacro()) {
                setState(LmPcState.STOP);
                logger.debug("????????? ????????? ?????? ???????????? ?????? ????????? ??????", device);
            } else {
                shoppingStart(screen);
            }
        }
    }

    /**
     * ????????? ????????? ????????? ???????????? ?????? ????????? ?????? ??????????????? ?????? ??????
     *
     * @param screen ??????
     */
    public void startHomeAndShopping(LmAndroidScreen screen) throws Exception {
        startHomeAndShopping(screen, false);
    }

    private void autoCheck(LmAndroidScreen screen, long time) {
        if (huntSetting.isUseAutoCheck()) {
            if (autoCheckTime.isWaitTime(time, huntSetting.getAutoCheckTime())) {
                return;
            }
            setAuto(screen, true);
        }

        if (huntSetting.isUseSelfCheck()) {
            if (selfCheckTime.isWaitTime(time, huntSetting.getSelfCheckTime())) {
                return;
            }
            setSelf(screen, true);
        }
    }

    public void setSelf(LmAndroidScreen screen, boolean isSelf) {
        selfPixel.setBoolean(device, screen, isSelf);
        setSelf(isSelf);
    }

    public void setAuto(LmAndroidScreen screen, boolean isAuto) {
        autoPixel.setBoolean(device, screen, isAuto);
        setAuto(isAuto);
    }

    public boolean isDamaged(LmAndroidScreen screen) throws Exception {
        LmPixelData t = screen.findPixelMatch(LmGraphics.ATTACK);
        return t.getPercent() > 0.94;
    }

    public boolean isEmptyArrow(LmAndroidScreen screen) throws Exception {
        LmPixelData t = screen.findPixelMatch(LmGraphics.EMPTY_ARR);
        return t.getPercent() > 0.94;
    }

    public void calcHpAndMp(LmAndroidScreen screen) {
        int _hp = (int) new LmHp().percent(screen);
        int _mp = (int) new LmMp().percent(screen);
        double _exp = new LmExp().percent(screen);

        setHp(_hp);
        setMp(_mp);

        if (_exp != 100)
            setExp(_exp);

        if ((startExp == -9999 || (exp < startExp && exp != 0)) && state == LmPcState.PLAY) {
            startExp = _exp;
        }

        logger.trace(String.format("?????? HP : %d, ?????? MP : %d", getHp(), getMp()), device);
    }

    public void executeAutoPatterns() {
        if (isInVillage()) {
            return;
        }

        if (autoPatternService != null)
            autoPatternService.execute(this);
    }

    public void useSlot(int slotPage, LmSlot.SlotType slotType) {
        try {
            LmSlot.slotData.get(slotType).click(device);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public boolean isInVillage() {
        return village != null;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }

    public Map<String, Integer> getUseItems() {
        if (itemUsePacket != null)
            return itemUsePacket.getDataMapByPort(device.getPort());

        return new HashMap<>();
    }

    public LmGraphic getVillage() {
        return village;
    }

    public void setVillage(LmGraphic village) {
        this.village = village;
    }

    public LmHuntSetting getHuntSetting() {
        return huntSettingService.findByDeviceSerial(device.getSerial());
    }

    public List<LmBuyItem> getBuyItems() {
        return buyItemRepositoryService.findAllByDeviceSerial(device.getSerial());
    }

    public void saveScreenShot(BufferedImage image, String dir) throws IOException {
        LmCommonUtils.saveScreenShot(device, image, dir);
    }

    public void useSlotFromString(String motionName) {
        if (LmMotionClickSlot.SLOT1.toString().equals(motionName)) {
            useSlot(0, LmSlot.SlotType.SLOT1);
        } else if (LmMotionClickSlot.SLOT2.toString().equals(motionName)) {
            useSlot(0, LmSlot.SlotType.SLOT2);
        } else if (LmMotionClickSlot.SLOT3.toString().equals(motionName)) {
            useSlot(0, LmSlot.SlotType.SLOT3);
        } else if (LmMotionClickSlot.SLOT4.toString().equals(motionName)) {
            useSlot(0, LmSlot.SlotType.SLOT4);
        } else if (LmMotionClickSlot.SLOT5.toString().equals(motionName)) {
            useSlot(0, LmSlot.SlotType.SLOT5);
        } else if (LmMotionClickSlot.SLOT6.toString().equals(motionName)) {
            useSlot(0, LmSlot.SlotType.SLOT6);
        } else if (LmMotionClickSlot.SLOT7.toString().equals(motionName)) {
            useSlot(0, LmSlot.SlotType.SLOT7);
        } else if (LmMotionClickSlot.SLOT8.toString().equals(motionName)) {
            useSlot(0, LmSlot.SlotType.SLOT8);
        }
    }

    @Resource
    public void setPartyManager(LmPartyManager partyManager) {
    }

    public long getMacroStartTime() {
        return macroStartTime;
    }

    public void setMacroStartTime(long macroStartTime) {
        this.macroStartTime = macroStartTime;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public double getStartExp() {
        return startExp;
    }

    public void setStartExp(double startExp) {
        this.startExp = startExp;
    }

    public double getMacroUpExp() {
        return exp - startExp;
    }

    public long getPlayTime() {
        return System.currentTimeMillis() - macroStartTime;
    }

    public long getPlayTimeSecond() {
        return TimeUnit.MILLISECONDS.toSeconds(getPlayTime());
    }

    public long getPlayTimeMinute() {
        return TimeUnit.MILLISECONDS.toMinutes(getPlayTime());
    }

    public long getPlayTimeHour() {
        return TimeUnit.MILLISECONDS.toHours(getPlayTime());
    }

}