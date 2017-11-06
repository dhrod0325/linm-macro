package lm.macro.auto.object.instance;

/**
 * Created by dhrod0325 on 2017-07-08.
 */
public enum LmPcState {
    PLAY("PLAY"),
    STOP("STOP"),
    SHOPPING("SHOPPING"), SHOP("SHOP");

    private String name;

    LmPcState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
