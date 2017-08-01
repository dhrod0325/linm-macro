package lm.macro.auto.manager.process;

import lm.macro.auto.data.model.process.LmAdbProcess;

import java.util.List;

public interface LmAdbProcessManager {
    List<LmAdbProcess> getProcessList();

    List<LmAdbProcess> getProcessList(boolean refresh);

    void refresh();

    LmAdbProcess getAdbProcessByHostPort(int port);
}
