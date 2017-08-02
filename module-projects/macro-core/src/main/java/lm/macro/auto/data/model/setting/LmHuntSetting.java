package lm.macro.auto.data.model.setting;

import lm.macro.auto.data.model.item.LmHuntMap;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LmHuntSetting {
    public static final String DAMAGED_MOTION_TELEPORT = "DAMAGED_MOTION_TELEPORT";
    public static final String DAMAGED_MOTION_HOME = "DAMAGED_MOTION_HOME";
    public static final String DAMAGED_MOTION_ATTACK = "DAMAGED_MOTION_ATTACK";

    public static List<String> DAMAGED_MOTIONS() {
        return new ArrayList<String>() {
            {
                add(DAMAGED_MOTION_TELEPORT);
                add(DAMAGED_MOTION_HOME);
                add(DAMAGED_MOTION_ATTACK);
            }
        };
    }

    @Id
    private String deviceSerial;

    @OneToMany(cascade = CascadeType.ALL)
    private List<LmHuntMap> huntMapList;

    private boolean redPotionEmptyGoHome;

    private boolean orangePotionEmptyGoHome;

    private boolean hpGoHome;

    private int hpGoHomeValue;

    private boolean arrowGoHome;

    @Column(columnDefinition = "integer default 30")
    private int arrowCheckCount;

    /**
     * 사냥터에서 매크로 시작
     */
    @Column(columnDefinition = "boolean default 0")
    private boolean huntStartInMap;

    @Column(columnDefinition = "integer default 70")
    private int huntStartMinHp;

    @Column(columnDefinition = "boolean default 0")
    private boolean redPotionEmptyStopMacro;

    /**
     * 귀환시 매크로 중지
     */
    @Column(columnDefinition = "boolean default 0")
    private boolean homeInStopMacro;

    @Column(columnDefinition = "boolean default 0")
    private boolean heavyGoHome;

    @Column(columnDefinition = "boolean default 0")
    private boolean useShopping;

    @Column(columnDefinition = "boolean default 0")
    private boolean useSelfCheck;

    @Column(columnDefinition = "boolean default 0")
    private boolean useAutoCheck;

    @Column(columnDefinition = "integer default 30")
    private int selfCheckTime = 30;

    @Column(columnDefinition = "integer default 30")
    private int autoCheckTime = 30;

    @Column(columnDefinition = "boolean default 1")
    private boolean useCheckLetter = true;

    @Column(columnDefinition = "boolean default 0")
    private boolean useHuntTeleport = false;

    private String poisonUseSlot = "";

    private String damagedMotion;

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public boolean isRedPotionEmptyGoHome() {
        return redPotionEmptyGoHome;
    }

    public void setRedPotionEmptyGoHome(boolean redPotionEmptyGoHome) {
        this.redPotionEmptyGoHome = redPotionEmptyGoHome;
    }

    public boolean isOrangePotionEmptyGoHome() {
        return orangePotionEmptyGoHome;
    }

    public void setOrangePotionEmptyGoHome(boolean orangePotionEmptyGoHome) {
        this.orangePotionEmptyGoHome = orangePotionEmptyGoHome;
    }

    public boolean isHpGoHome() {
        return hpGoHome;
    }

    public void setHpGoHome(boolean hpGoHome) {
        this.hpGoHome = hpGoHome;
    }

    public int getHpGoHomeValue() {
        return hpGoHomeValue;
    }

    public void setHpGoHomeValue(int hpGoHomeValue) {
        this.hpGoHomeValue = hpGoHomeValue;
    }

    public boolean isArrowGoHome() {
        return arrowGoHome;
    }

    public void setArrowGoHome(boolean arrowGoHome) {
        this.arrowGoHome = arrowGoHome;
    }

    public int getArrowCheckCount() {
        return arrowCheckCount;
    }

    public void setArrowCheckCount(int arrowCheckCount) {
        this.arrowCheckCount = arrowCheckCount;
    }

    public boolean isHuntStartInMap() {
        return huntStartInMap;
    }

    public void setHuntStartInMap(boolean huntStartInMap) {
        this.huntStartInMap = huntStartInMap;
    }

    public boolean isHomeInStopMacro() {
        return homeInStopMacro;
    }

    public void setHomeInStopMacro(boolean homeInStopMacro) {
        this.homeInStopMacro = homeInStopMacro;
    }

    public boolean isHeavyGoHome() {
        return heavyGoHome;
    }

    public void setHeavyGoHome(boolean heavyGoHome) {
        this.heavyGoHome = heavyGoHome;
    }

    public boolean isUseShopping() {
        return useShopping;
    }

    public void setUseShopping(boolean useShopping) {
        this.useShopping = useShopping;
    }

    public boolean isUseSelfCheck() {
        return useSelfCheck;
    }

    public void setUseSelfCheck(boolean useSelfCheck) {
        this.useSelfCheck = useSelfCheck;
    }

    public int getSelfCheckTime() {
        return selfCheckTime;
    }

    public void setSelfCheckTime(int selfCheckTime) {
        this.selfCheckTime = selfCheckTime;
    }

    public boolean isUseAutoCheck() {
        return useAutoCheck;
    }

    public void setUseAutoCheck(boolean useAutoCheck) {
        this.useAutoCheck = useAutoCheck;
    }

    public int getAutoCheckTime() {
        return autoCheckTime;
    }

    public void setAutoCheckTime(int autoCheckTime) {
        this.autoCheckTime = autoCheckTime;
    }

    public boolean isUseCheckLetter() {
        return useCheckLetter;
    }

    public void setUseCheckLetter(boolean useCheckLetter) {
        this.useCheckLetter = useCheckLetter;
    }

    public boolean isUseHuntTeleport() {
        return useHuntTeleport;
    }

    public void setUseHuntTeleport(boolean useHuntTeleport) {
        this.useHuntTeleport = useHuntTeleport;
    }

    public String getDamagedMotion() {
        return damagedMotion;
    }

    public void setDamagedMotion(String damagedMotion) {
        this.damagedMotion = damagedMotion;
    }

    public List<LmHuntMap> getHuntMapList() {
        return huntMapList;
    }

    public void setHuntMapList(List<LmHuntMap> huntMapList) {
        this.huntMapList = huntMapList;
    }

    public int getHuntStartMinHp() {
        return huntStartMinHp;
    }

    public void setHuntStartMinHp(int huntStartMinHp) {
        this.huntStartMinHp = huntStartMinHp;
    }

    public String getPoisonUseSlot() {
        return poisonUseSlot;
    }

    public void setPoisonUseSlot(String poisonUseSlot) {
        this.poisonUseSlot = poisonUseSlot;
    }

    public boolean isRedPotionEmptyStopMacro() {
        return redPotionEmptyStopMacro;
    }

    public void setRedPotionEmptyStopMacro(boolean redPotionEmptyStopMacro) {
        this.redPotionEmptyStopMacro = redPotionEmptyStopMacro;
    }
}
