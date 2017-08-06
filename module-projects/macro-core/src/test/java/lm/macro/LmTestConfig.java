package lm.macro;

import lm.macro.spring.config.LmAppConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Configuration
@Import({LmAppConfiguration.class})
@ComponentScan(value = "lm.macro",
        excludeFilters = {
                @ComponentScan.Filter(value = Main.class, type = FilterType.ASSIGNABLE_TYPE),
        })
public class LmTestConfig {
}
