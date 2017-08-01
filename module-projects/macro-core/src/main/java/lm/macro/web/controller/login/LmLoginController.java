package lm.macro.web.controller.login;

import lm.macro.login.LmLoginService;
import lm.macro.login.LmUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LmLoginController {
//    @Resource
//    private LmLoginService loginService;

    @RequestMapping("/login")
    public String login(@ModelAttribute("user") LmUser user) {
        return "login/login";
    }

//    @RequestMapping("/login/proc")
//    public String loginProc(@Valid @ModelAttribute("user") LmUser user, BindingResult bindingResult, HttpSession session, ModelMap model) throws Exception {
//        if (bindingResult.hasErrors()) {
//            return login(user);
//        }
//
//        LmUser responseUser = loginService.loadUserByIdAndPassword(user.getId(), user.getPw());
//
//        model.addAttribute("responseUser", responseUser);
//
//        if (responseUser.isLoggedIn()) {
//            session.setAttribute("user", responseUser);
//            return "redirect:/";
//        } else {
//            return "login/login";
//        }
//    }
}