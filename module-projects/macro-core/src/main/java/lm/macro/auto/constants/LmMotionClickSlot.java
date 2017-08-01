package lm.macro.auto.constants;

public enum LmMotionClickSlot {
    SLOT1("스킬1클릭"),
    SLOT2("스킬2클릭"),
    SLOT3("스킬3클릭"),
    SLOT4("스킬4클릭"),
    SLOT5("스킬5클릭"),
    SLOT6("스킬6클릭"),
    SLOT7("스킬7클릭"),
    SLOT8("스킬8클릭");

    String name;

    LmMotionClickSlot(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
