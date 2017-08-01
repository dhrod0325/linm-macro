package lm.macro.web.controller.common;

import lm.macro.auto.data.model.item.LmBuyItem;
import lm.macro.auto.data.service.LmBuyItemRepositoryService;
import lm.macro.auto.graphics.LmShopItemGraphics;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/buyItem")
public class LmBuyItemSettingController {
    @Resource
    private LmBuyItemRepositoryService buyItemRepositoryService;

    @Resource
    private LmShopItemGraphics shopItemGraphics;

    public void initBinder() {

    }

    @RequestMapping("/save")
    public Object save(@RequestParam("deviceSerial") String deviceSerial, HttpServletRequest request) throws Exception {
        Map<String, String[]> param = request.getParameterMap();

        for (String key : param.keySet()) {
            try {
                shopItemGraphics.getByName(key);
            } catch (Exception e) {
                continue;
            }

            String[] v = param.get(key);

            if (v.length > 0) {
                int value = StringUtils.isEmpty(v[0]) ? 0 : Integer.valueOf(v[0]);

                buyItemRepositoryService.save(new LmBuyItem(deviceSerial, key, value));
            }
        }

        return null;
    }
}
