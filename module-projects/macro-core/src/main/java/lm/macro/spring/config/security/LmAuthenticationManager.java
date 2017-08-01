package lm.macro.spring.config.security;

import lm.macro.login.LmLoginService;
import lm.macro.login.LmUser;
import org.springframework.security.authentication.rcp.RemoteAuthenticationException;
import org.springframework.security.authentication.rcp.RemoteAuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

public class LmAuthenticationManager implements RemoteAuthenticationManager {
    @Resource
    private LmLoginService loginService;

    @Override
    public Collection<? extends GrantedAuthority> attemptAuthentication(String username, String password) throws RemoteAuthenticationException {
        try {
            LmUser user = loginService.loadUserByIdAndPassword(username, password);
            String role = "GRANT_" + user.getMacroUseType();

            return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
