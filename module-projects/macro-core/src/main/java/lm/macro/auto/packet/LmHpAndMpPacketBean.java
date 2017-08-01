package lm.macro.auto.packet;

/**
 * Created by dhrod0325 on 2017-07-09.
 */
public class LmHpAndMpPacketBean {
    public int hp;
    public int maxHp;

    public int mp;
    public int maxMp;

    public LmHpAndMpPacketBean(int hp, int maxHp, int mp, int maxMp) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.mp = mp;
        this.maxMp = maxMp;
    }

    @Override
    public String toString() {
        return "HpAndMp{" +
                "hp=" + hp +
                ", maxHp=" + maxHp +
                ", mp=" + mp +
                ", maxMp=" + maxMp +
                '}';
    }
}
