package lm.macro.auto.data.repository;

import lm.macro.auto.data.model.setting.LmHuntSetting;
import org.springframework.data.repository.CrudRepository;

public interface LmHuntSettingRepository extends CrudRepository<LmHuntSetting, String> {
    LmHuntSetting findByDeviceSerial(String serial);
}
