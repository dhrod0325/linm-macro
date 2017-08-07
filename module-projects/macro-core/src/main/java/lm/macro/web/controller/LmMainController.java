package lm.macro.web.controller;

import lm.macro.auto.common.LmCommon;
import lm.macro.auto.constants.LmMotionClickSlot;
import lm.macro.auto.graphics.LmDeleteItemGraphics;
import lm.macro.auto.graphics.LmShopItemGraphics;
import lm.macro.auto.graphics.LmTeleportGraphics;
import lm.macro.auto.utils.LmStringUtils;
import lm.macro.auto.utils.gnu.GnuUtils;
import lm.macro.security.LmUserDetailsHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class LmMainController {
    @Resource
    private LmTeleportGraphics teleportGraphics;

    @Resource
    private LmShopItemGraphics shopItemGraphics;

    @Resource
    private LmDeleteItemGraphics deleteItemGraphics;

    @RequestMapping("/")
    public String index(ModelMap model) throws Exception {
        model.addAttribute("user", LmUserDetailsHelper.getUser());

        model.addAttribute("noticeList", GnuUtils.getLatestList(
                LmCommon.WEB_SERVER_URL + "/linm/bbs/list.php",
                "notice"));

        return "index";
    }

    @RequestMapping("/setMacro")
    public String setMacro(ModelMap model) {
        model.addAttribute("motionNames", LmStringUtils.enumValuesStringList(LmMotionClickSlot.class));
        model.addAttribute("teleportGraphics", teleportGraphics.getGraphics());
        model.addAttribute("shopGraphics", shopItemGraphics.getGraphics());
        model.addAttribute("deleteGraphics", deleteItemGraphics.getGraphics());
        model.addAttribute("joystickLocations", LmCommon.조이스틱좌표());


        return "macro/setMacro";
    }

    @RequestMapping("/setParty")
    public String setParty(ModelMap model) {
        return "macro/setParty";
    }

    @RequestMapping("/monitor")
    public String monitor(ModelMap model) {
        return "monitor/monitor";
    }

    @RequestMapping("/log")
    public String log() {
        return "log/log";
    }

    @RequestMapping("/editor")
    public String editor() {
        return "editor/editor";
    }
}
