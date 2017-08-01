package lm.macro.auto.data.repository;

import lm.macro.auto.data.model.item.LmDeleteItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dhrod0325 on 2017-07-22.
 */
public interface LmDeleteItemRepository extends CrudRepository<LmDeleteItem, String> {
    List<LmDeleteItem> findAllByDeviceSerial(String deviceSerial);
}
