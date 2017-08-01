package lm.macro.auto.data.service;

import lm.macro.auto.data.model.item.LmBuyItem;
import lm.macro.auto.data.repository.LmBuyItemRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class LmBuyItemRepositoryService {
    @Resource
    private LmBuyItemRepository repository;

    public List<LmBuyItem> findAllByDeviceSerial(String deviceSerial) {
        return repository.findAllByDeviceSerial(deviceSerial);
    }

    public void save(LmBuyItem lmBuyItem) {
        repository.save(lmBuyItem);
    }
}
