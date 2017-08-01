package lm.macro.login;

public interface LmLoginService {
    LmUser loadUserByIdAndPassword(String id, String pw) throws Exception;
}
