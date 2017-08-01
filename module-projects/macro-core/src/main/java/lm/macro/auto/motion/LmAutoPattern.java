package lm.macro.auto.motion;

import javax.persistence.*;

@Entity
public class LmAutoPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int devicePort;

    private String name;

    private String type1;

    @Column(columnDefinition = "integer default 0")
    private int value1;

    @Column(columnDefinition = "integer default 0")
    private int above1;

    @Column(columnDefinition = "integer default 0")
    private int below1;

    private String type2;

    @Column(columnDefinition = "integer default 0")
    private int value2;

    @Column(columnDefinition = "integer default 0")
    private int above2;

    @Column(columnDefinition = "integer default 0")
    private int below2;

    private String motion;

    private boolean start = false;

    @Transient
    private long useTime;
    @Column(columnDefinition = "integer default 1")
    private int delay = 1;

    public LmAutoPattern() {
    }

    public LmAutoPattern(String name, String type1, int value1, int above1, int below1, String motion) {
        this.name = name;
        this.type1 = type1;
        this.value1 = value1;
        this.above1 = above1;
        this.below1 = below1;
        this.motion = motion;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getAbove1() {
        return above1;
    }

    public void setAbove1(int above1) {
        this.above1 = above1;
    }

    public int getBelow1() {
        return below1;
    }

    public void setBelow1(int below1) {
        this.below1 = below1;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LmAutoPattern) {
            LmAutoPattern p = (LmAutoPattern) obj;

            if (name.equals(p.getName())) {
                return true;
            }
        }

        return super.equals(obj);
    }

    public int getDevicePort() {
        return devicePort;
    }

    public void setDevicePort(int devicePort) {
        this.devicePort = devicePort;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public int getAbove2() {
        return above2;
    }

    public void setAbove2(int above2) {
        this.above2 = above2;
    }

    public int getBelow2() {
        return below2;
    }

    public void setBelow2(int below2) {
        this.below2 = below2;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelayMilliSecond() {
        return delay * 1000;
    }

    public boolean validate() {
        //HP 체크
        boolean check1 = getValue1() >= getAbove1() && getValue1() <= getBelow1() && getValue1() != 0;

        //MP 체크
        boolean check2 = getValue2() >= getAbove2() && getValue2() <= getBelow2();

        return check1 && check2 && isStart();
    }
}
