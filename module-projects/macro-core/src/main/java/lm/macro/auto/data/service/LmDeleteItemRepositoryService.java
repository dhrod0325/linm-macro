package lm.macro.auto.data.service;

import lm.macro.auto.data.model.item.LmDeleteItem;
import lm.macro.auto.data.repository.LmDeleteItemRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dhrod0325 on 2017-07-22.
 */
public class LmDeleteItemRepositoryService {
    @Resource
    private LmDeleteItemRepository repository;

    public List<LmDeleteItem> findAllByDeviceSerial(String deviceSerial) {
        return repository.findAllByDeviceSerial(deviceSerial);
    }

    public void save(LmDeleteItem vo) {
        repository.save(vo);
    }
}
