package lm.macro.auto.data.service;

import lm.macro.auto.data.model.item.LmHuntMap;
import lm.macro.auto.data.repository.LmHuntMapRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LmHuntMapRepositoryService {
    @Resource
    private LmHuntMapRepository huntMapRepository;

    public LmHuntMap save(LmHuntMap vo) {
        return huntMapRepository.save(vo);
    }
}
