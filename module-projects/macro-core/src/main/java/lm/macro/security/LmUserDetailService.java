package lm.macro.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

public class LmUserDetailService implements UserDetailsService {
    @Resource
    private LmUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            LmUser user = userRepository.findOne(username);

            if (user == null) {
                throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
            } else {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new UsernameNotFoundException("");
    }

}
