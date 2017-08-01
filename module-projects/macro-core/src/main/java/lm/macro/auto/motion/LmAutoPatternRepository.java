package lm.macro.auto.motion;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LmAutoPatternRepository extends CrudRepository<LmAutoPattern, Long> {
    List<LmAutoPattern> findByDevicePort(int port);
}
