package lm.macro.sns.repository;

import lm.macro.sns.model.SnsToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SnsTokenRepository extends CrudRepository<SnsToken, Long> {
    SnsToken findBySnsType(String snsType);

    @Transactional
    @Modifying
    void removeAllBySnsType(String snsType);
}
