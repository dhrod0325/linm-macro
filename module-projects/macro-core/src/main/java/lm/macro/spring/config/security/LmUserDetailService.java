package lm.macro.spring.config.security;

import lm.macro.login.LmLoginService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

public class LmUserDetailService implements UserDetailsService {
    @Resource
    private LmLoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            loginService.loadUserByIdAndPassword(username, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
