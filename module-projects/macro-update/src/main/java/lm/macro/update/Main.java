package lm.macro.update;

import lm.macro.update.gui.GuiUpdateMain;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        killAdb();

        new GuiUpdateMain().start();
    }

    private static void killAdb() throws Exception {
        List<LmProcess> t = LmProcessUtils.processList("adb.exe");

        for (LmProcess process : t) {
            LmProcessUtils.killByPid(process.getPid());
        }
    }
}
