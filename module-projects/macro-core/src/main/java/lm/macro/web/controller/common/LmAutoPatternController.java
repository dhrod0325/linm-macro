package lm.macro.web.controller.common;

import lm.macro.auto.motion.LmAutoPattern;
import lm.macro.auto.motion.LmAutoPatternService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/autoPattern")
public class LmAutoPatternController {
    @Resource
    private LmAutoPatternService autoPatternService;

    @RequestMapping("/save")
    public List<LmAutoPattern> save(LmAutoPattern autoPattern) {
        autoPatternService.save(autoPattern);

        return autoPatternService.findByDevicePort(autoPattern.getDevicePort());
    }

    @RequestMapping("/remove")
    public List<LmAutoPattern> remove(LmAutoPattern autoPattern) {
        autoPatternService.remove(autoPattern);
        return autoPatternService.findByDevicePort(autoPattern.getDevicePort());
    }

    @RequestMapping("/findByDevicePort")
    public List<LmAutoPattern> findByDevicePort(LmAutoPattern autoPattern) {
        return autoPatternService.findByDevicePort(autoPattern.getDevicePort());
    }

    @RequestMapping("/stopAutoPattern")
    public LmAutoPattern stopAutoPattern(LmAutoPattern autoPattern) {
        LmAutoPattern pattern = autoPatternService.findOne(autoPattern);
        pattern.setStart(false);
        autoPatternService.save(pattern);

        return pattern;
    }

    @RequestMapping("/startAutoPattern")
    public LmAutoPattern startAutoPattern(LmAutoPattern autoPattern) {
        LmAutoPattern pattern = autoPatternService.findOne(autoPattern);
        pattern.setStart(true);
        autoPatternService.save(pattern);

        return autoPattern;
    }
}
