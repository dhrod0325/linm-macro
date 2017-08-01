package lm.macro.auto.data.service;

import lm.macro.auto.data.model.setting.LmHuntSetting;
import lm.macro.auto.data.repository.LmHuntSettingRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LmHuntSettingRepositoryService {
    @Resource
    private LmHuntSettingRepository huntSettingRepository;
    
    public void save(LmHuntSetting huntSetting) {
        huntSettingRepository.save(huntSetting);
    }

    public LmHuntSetting findByDeviceSerial(String serial) {
        return huntSettingRepository.findByDeviceSerial(serial);
    }
}
