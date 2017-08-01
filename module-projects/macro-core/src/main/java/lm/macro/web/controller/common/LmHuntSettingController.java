package lm.macro.web.controller.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lm.macro.auto.data.model.item.LmHuntMap;
import lm.macro.auto.data.model.setting.LmHuntSetting;
import lm.macro.auto.data.service.LmHuntSettingRepositoryService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hunt")
public class LmHuntSettingController {
    @Resource
    private LmHuntSettingRepositoryService huntSettingService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "huntMapList", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    List<LmHuntMap> huntMaps = new ObjectMapper().readValue(text, new TypeReference<List<LmHuntMap>>() {
                    });
                    setValue(huntMaps);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequestMapping("/save")
    public LmHuntSetting save(LmHuntSetting huntSetting) {
        huntSettingService.save(huntSetting);

        return huntSetting;
    }
}