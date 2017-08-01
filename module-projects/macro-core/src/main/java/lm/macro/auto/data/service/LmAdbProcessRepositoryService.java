package lm.macro.auto.data.service;

import lm.macro.auto.data.model.process.LmAdbProcess;
import lm.macro.auto.data.repository.LmAdbProcessRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
public class LmAdbProcessRepositoryService {
    @Resource
    private LmAdbProcessRepository adbProcessRepository;

    @Transactional
    public void save(List<LmAdbProcess> processList) {
        adbProcessRepository.deleteAll();
        adbProcessRepository.save(processList);
    }
}
