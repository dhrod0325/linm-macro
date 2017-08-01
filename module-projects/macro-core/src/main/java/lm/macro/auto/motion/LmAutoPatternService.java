package lm.macro.auto.motion;

import com.google.common.collect.Lists;
import lm.macro.auto.object.instance.LmPcInstance;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class LmAutoPatternService implements InitializingBean {
    @Resource
    private LmAutoPatternRepository autoPatternRepository;

    private List<LmAutoPattern> autoPatterns = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        refresh();
    }

    public void refresh() {
        autoPatterns.clear();
        autoPatterns.addAll(Lists.newArrayList(autoPatternRepository.findAll()));
    }

    public void save(LmAutoPattern autoPattern) {
        autoPatternRepository.save(autoPattern);

        refresh();
    }

    public void remove(LmAutoPattern autoPattern) {
        autoPatternRepository.delete(autoPattern.getId());
        refresh();
    }

    public void execute(LmPcInstance instance) {
        int port = instance.getDevice().getPort();

        List<LmAutoPattern> autoPatterns = findByDevicePort(port);

        for (LmAutoPattern autoPattern : autoPatterns) {
            try {
                new LmAutoPatternExecutor().execute(instance, autoPattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<LmAutoPattern> findByDevicePort(int port) {
        List<LmAutoPattern> result = new ArrayList<>();

        for (LmAutoPattern autoPattern : autoPatterns) {
            if (autoPattern.getDevicePort() == port) {
                result.add(autoPattern);
            }
        }

        return result;
    }

    public LmAutoPattern findOne(LmAutoPattern autoPattern) {
        refresh();

        for (LmAutoPattern pattern : autoPatterns) {
            if (pattern.getId().equals(autoPattern.getId())) {
                return pattern;
            }
        }

        return null;
    }
}