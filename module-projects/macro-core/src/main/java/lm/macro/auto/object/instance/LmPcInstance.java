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
import lm.macro.auto.object.pixel.LmMatPixel;
import lm.macro.auto.object.pixel.LmPixelData;
import lm.macro.auto.object.pixel.impl.*;
import lm.macro.auto.packet.items.LmItemUsePacket;
import lm.macro.auto.utils.LmCommonUtils;
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
    public void ai(LmAndroidScreen screen, long time) {
        if (screen == null)
            return;

        initialize(screen);

        if (!commonCheckTime.isWaitTime(time, 5)) {
            logger.debug(String.format("[%s] 현재 아이템 : %s", getName(), getUseItems()));
        }

        //초기화상태이거나 스탑상태라면 동작 정지
        if (state == LmPcState.STOP) {
            return;
        }

        if (state == LmPcState.PLAY) {
            checkLetter(time, screen);

            calcHpAndMp(screen);

            startHunt(screen, time);
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

    private void checkLetter(long time, LmAndroidScreen screen) {
        try {
            if (huntSetting.isUseCheckLetter()) {
                if (!letterCheckTime.isWaitTime(time, 60 * 30)) {
                    new LmLetter().check(device, screen);
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
                    logger.debug("HP 60% 이하라서 대기중입니다.");
                    return;
                }

                if (huntSetting.isUseShopping()) {
                    shoppingStart(screen);
                }

                if (huntSetting.isUseHuntTeleport()) {
                    screen.refreshScreen(device);
                    LmCommonUtils.sleep(500);

                    int v = RandomUtils.nextInt(0, huntSetting.getHuntMapList().size() - 1);
                    LmHuntMap map = huntSetting.getHuntMapList().get(v);

                    if (teleportInstance.toTeleport(device, screen, map.getName(), !map.isNoDungeon())) {
                        LmCommonUtils.sleep(500);
                        screen.refreshScreen(device);
                        LmCommonUtils.sleep(500);
                        for (LmHuntJoystick huntJoystick : map.getHuntJoysticks()) {
                            int dis = 500;

                            long delay = TimeUnit.SECONDS.toMillis((long) huntJoystick.getJoystickDelay());

                            if (LmCommon.조이스틱_상.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeTop(device, dis, delay);
                            } else if (LmCommon.조이스틱_우상.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeTopRight(device, dis, delay);
                            } else if (LmCommon.조이스틱_우.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeRight(device, dis, delay);
                            } else if (LmCommon.조이스틱_우하.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeBottomRight(device, dis, delay);
                            } else if (LmCommon.조이스틱_하.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeBottom(device, dis, delay);
                            } else if (LmCommon.조이스틱_좌하.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeBottomLeft(device, dis, delay);
                            } else if (LmCommon.조이스틱_좌.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeLeft(device, dis, delay);
                            } else if (LmCommon.조이스틱_좌상.equals(huntJoystick.getJoystickLoc())) {
                                joystick.swipeTopLeft(device, dis, delay);
                            }
                        }

                        autoPixel.click(device);
                        setSelf(screen, true);
                    } else {
                        //텔포에 실패했다...
                        LmPixels.메뉴버튼().click(device);
                    }
                }
            } else {
                autoCheck(screen, time);

                dieRestart.restart(screen, device);

                if (isDamaged(screen)) {
                    if (LmHuntSetting.DAMAGED_MOTION_HOME.equals(huntSetting.getDamagedMotion())) {
                        logger.debug("유저 공격으로 인해 귀환 ... ", device);
                        saveScreenShot(screen.getScreenShotIO(), "처맞구귀환");
                        startHomeAndShopping(screen);

                        kakaoSnsService.feed("알림", "캐릭터가 공격당해 귀환 했습니다.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");

                        return;
                    } else if (LmHuntSetting.DAMAGED_MOTION_TELEPORT.equals(huntSetting.getDamagedMotion())) {
                        logger.debug("유저 공격으로 인해 순간이동 ... ", device);

                        saveScreenShot(screen.getScreenShotIO(), "처맞구순간이동");

                        damagedTeleport(screen);

                        kakaoSnsService.feed("알림", "캐릭터가 공격당해 순간이동했습니다.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");

                        return;
                    } else if (LmHuntSetting.DAMAGED_MOTION_ATTACK.equalsIgnoreCase(huntSetting.getDamagedMotion())) {
                        logger.debug("반격처리 진행 ", device);
                    }
                }

                if (huntSetting.isHpGoHome()) {
                    if (hp > 0 && hp < huntSetting.getHpGoHomeValue()) {
                        logger.debug("HP가 설정값보다 작기때문에 귀환...", device);

                        saveScreenShot(screen.getScreenShotIO(), "피없어서귀환");

                        startHomeAndShopping(screen);

                        kakaoSnsService.feed("알림", "캐릭터가 피가없어 귀환했습니다.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");

                        return;
                    }
                }

                if (huntSetting.isRedPotionEmptyGoHome()) {
                    LmMatPixel potionEmpty = new LmMatPixel(screen, LmGraphics.POTION_EMPTY, 0.99);

                    Map<String, Integer> items = getUseItems();

                    if (items != null) {
                        if (items.get(LmCommon.빨간물약) < 10 && potionEmpty.getPixelData().isExists()) {
                            logger.debug("물약이 없어서 귀환...", device);
                            startHomeAndShopping(screen, huntSetting.isRedPotionEmptyStopMacro());
                            kakaoSnsService.feed("알림", "캐릭터가 물약이 없어 귀환했습니다.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");
                            return;
                        }
                    } else {
                        if (potionEmpty.getPixelData().isExists()) {
                            logger.debug("물약이 없어서 귀환...", device);
                            startHomeAndShopping(screen, huntSetting.isRedPotionEmptyStopMacro());
                            kakaoSnsService.feed("알림", "캐릭터가 물약이 없어 귀환했습니다.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");
                            return;
                        }
                    }


                }

                if (huntSetting.isArrowGoHome()) {
                    if (isEmptyArrow(screen)) {
                        buyArrow();
                        return;
                    }

                    Integer 은화살수량 = getUseItems().get(LmCommon.은화살);

                    if (은화살수량 != null && getUseItems().get(LmCommon.은화살) < 10) {
                        buyArrow();
                        return;
                    }
                }

                if (huntSetting.isHeavyGoHome()) {
                    if (bag.isHeavy(screen)) {
                        logger.debug("가방 무거워서 귀환...", device);
                        startHomeAndShopping(screen);
                    }

                    return;
                }

                poisonCheck(time, screen);

                executeAutoPatterns();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buyArrow() throws Exception {
        String message = "은화살이 없어서 귀환...";
        logger.debug(message, device);
        startHomeAndShopping(screen);
        kakaoSnsService.feed("알림", "캐릭터가 은화살이 없어서 귀환했습니다.", "https://img.youtube.com/vi/kCrLup8D30Q/0.jpg", "http://null");
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
                if (LmCommon.은화살.equals(copyItem.getName())) {
                    copyItem.setBuyCount(copyItem.getBuyCount() - (count / 10));
                } else {
                    copyItem.setBuyCount(copyItem.getBuyCount() - count);
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
                logger.debug(String.format("%d개 구매해야할 상품이 있기때문에 상품 구매 모드 시작함.", buyItems.size()));
            }

            LmCommonUtils.sleep(500);
            screen.refreshScreen(device);
            LmCommonUtils.sleep(500);

            /*
            * 현재 위치가 마을이 아니라면 귀환한다.
            * */
            if (villageGraphics.isInVillage(screen) == null) {
                goHomeAndSleep2000(screen);
            }

            /**
             * 현재 위치 마을 상태
             */
            LmCommonUtils.sleep(1000);
            screen.refreshScreen(device);
            LmCommonUtils.sleep(100);

            boolean buyItem = shopInstance.startBuyItem(buyItems, device, screen);

            if (!buyItem) {
                teleportInstance.toTeleport(device, screen, LmCommon.상점마을, false);
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
                logger.debug("귀환시 매크로 정지 옵션으로 인해 메크로 정지", device);
            } else {
                shoppingStart(screen);
            }
        }
    }


    /**
     * 집으로 귀환후 매크로 정지상태 설정 체크후 체크 안되있으면 쇼핑 시작
     *
     * @param screen 화면
     */
    private void startHomeAndShopping(LmAndroidScreen screen) throws Exception {
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

        logger.trace(String.format("현재 HP : %d, 현재 MP : %d", getHp(), getMp()), device);
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

    private void saveScreenShot(BufferedImage image, String dir) throws IOException {
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