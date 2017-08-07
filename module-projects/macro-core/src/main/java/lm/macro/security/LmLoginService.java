package lm.macro.security;

public interface LmLoginService {
    LmUser loadUserByIdAndPassword(String id, String pw) throws Exception;
}
