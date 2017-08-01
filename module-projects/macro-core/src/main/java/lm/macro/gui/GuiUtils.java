package lm.macro.gui;

import lm.macro.Main;
import lm.macro.spring.app.LmAppContext;
import org.springframework.boot.SpringApplication;

public class GuiUtils {
    public static void update() {

    }

    public static void start(GuiCallback callback) {
        new Thread(() -> {
            SpringApplication.run(Main.class);
            callback.run();
        }).start();
    }

    public static void shutDown() {
        if (LmAppContext.getCtx() != null) {
            try {
                SpringApplication.exit(LmAppContext.getCtx());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Runtime.getRuntime().exit(0);
    }

    public interface GuiCallback {
        void run();
    }
}