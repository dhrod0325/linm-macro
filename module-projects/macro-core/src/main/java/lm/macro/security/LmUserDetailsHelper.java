package lm.macro.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class LmUserDetailsHelper {
    public static UserDetails getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LmUser) authentication.getPrincipal();
    }
}
