package lm.macro.security;

import lm.macro.auto.log.LmLog;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Resource;
import java.net.*;

public class LmAuthenticationProvider implements AuthenticationProvider {
    private LmLog logger = new LmLog(getClass());

    @Resource
    private LmLoginService loginService;

    @Resource
    private LmUserRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String pw = (String) authentication.getCredentials();
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        if (pw == null || id == null) {
            throw new BadCredentialsException("Bad credentials");
        }
        
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(id, pw.toCharArray());
            }
        });

        try {
            LmUser user = loginService.loadUserByIdAndPassword(id, pw);
            if (user.isLoggedIn()) {
                repository.save(user);

                return new UsernamePasswordAuthenticationToken(
                        user,
                        authentication.getCredentials(),
                        user.getAuthorityList());
            } else {
                return authentication;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
