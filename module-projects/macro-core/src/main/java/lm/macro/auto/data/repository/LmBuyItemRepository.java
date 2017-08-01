package lm.macro.auto.data.repository;

import lm.macro.auto.data.model.item.LmBuyItem;
import lm.macro.auto.data.model.item.LmBuyItemId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LmBuyItemRepository extends CrudRepository<LmBuyItem, LmBuyItemId> {
    List<LmBuyItem> findAllByDeviceSerial(String deviceSerial);
}
