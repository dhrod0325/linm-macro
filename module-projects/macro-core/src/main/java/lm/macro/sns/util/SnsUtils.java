package lm.macro.sns.util;

import com.github.scribejava.core.model.OAuth2AccessToken;
import lm.macro.sns.model.SnsToken;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class SnsUtils {
    public static OAuth2AccessToken toOAuth2AccessToken(SnsToken snsToken) throws InvocationTargetException, IllegalAccessException {
        OAuth2AccessToken result = new OAuth2AccessToken(snsToken.getAccessToken());
        BeanUtils.copyProperties(result, snsToken);

        return result;
    }

    public static SnsToken copy(OAuth2AccessToken token) throws InvocationTargetException, IllegalAccessException {
        SnsToken result = new SnsToken();
        BeanUtils.copyProperties(result, token);

        return result;
    }
}
