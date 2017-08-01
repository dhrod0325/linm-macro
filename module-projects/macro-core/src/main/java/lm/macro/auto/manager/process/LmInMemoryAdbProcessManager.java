package lm.macro.auto.manager.process;

import lm.macro.auto.data.model.process.LmAdbProcess;
import lm.macro.auto.utils.LmAdbProcessUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LmInMemoryAdbProcessManager implements InitializingBean, LmAdbProcessManager {
    private final List<LmAdbProcess> processList = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        refresh();
    }

    @Override
    public void refresh() {
        processList.clear();
        processList.addAll(LmAdbProcessUtils.getAdbProcessList());
    }

    @Override
    public LmAdbProcess getAdbProcessByHostPort(int port) {
        List<LmAdbProcess> k = getProcessList();

        for (LmAdbProcess p : k) {
            if (p.getHostPort() == port) {
                return p;
            }
        }

        return null;
    }

    @Override
    public List<LmAdbProcess> getProcessList(boolean refresh) {
        if (refresh) {
            refresh();
        }

        return processList;
    }

    @Override
    public List<LmAdbProcess> getProcessList() {
        return getProcessList(false);
    }
}
