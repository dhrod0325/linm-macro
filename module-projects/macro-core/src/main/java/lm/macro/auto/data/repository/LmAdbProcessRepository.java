package lm.macro.auto.data.repository;

import lm.macro.auto.data.model.process.LmAdbProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dhrod0325 on 2017-07-11.
 */
public interface LmAdbProcessRepository extends CrudRepository<LmAdbProcess, String> {
}
