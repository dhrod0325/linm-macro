package lm.macro.web.controller.login;

import lm.macro.security.LmUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LmLoginController {
    @RequestMapping("/login")
    public String login(@ModelAttribute("user") LmUser user) {
        return "login/login";
    }
}