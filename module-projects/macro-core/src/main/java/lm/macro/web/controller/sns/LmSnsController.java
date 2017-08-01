package lm.macro.web.controller.sns;

import lm.macro.sns.service.KakaoSnsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sns")
public class LmSnsController {
    @Resource(name = "kakaoSnsService")
    private KakaoSnsService kakaoSnsService;

    @RequestMapping("/setting")
    public String snsSetting(ModelMap model) {
        model.addAttribute("kakaoAuthorizationUrl", kakaoSnsService.getAuthorizationUrl());
        model.addAttribute("kakaoToken", kakaoSnsService.getSnsToken());

        return "sns/snsSetting";
    }

    @RequestMapping("/oauthCallback")
    public String oauth_callback(@RequestParam("code") String code, @RequestParam("mode") String mode) throws Exception {

        if ("kakao".equalsIgnoreCase(mode)) {
            kakaoSnsService.requestToken(code);
        }

        return "redirect:/sns/setting";
    }
}
