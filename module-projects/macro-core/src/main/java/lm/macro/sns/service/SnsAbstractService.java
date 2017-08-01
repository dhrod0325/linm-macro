package lm.macro.sns.service;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import lm.macro.sns.model.SnsToken;
import lm.macro.sns.repository.SnsTokenRepository;
import lm.macro.sns.util.SnsUtils;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;

public abstract class SnsAbstractService {
    protected OAuth20Service service;

    @Resource
    private SnsTokenRepository tokenRepository;

    public abstract String getSnsType();

    protected abstract OAuth20Service createService();

    public SnsAbstractService() {
        this.service = createService();
    }

    public String getAuthorizationUrl() {
        return createService().getAuthorizationUrl();
    }

    public void refreshToken() throws Exception {

        SnsToken snsToken = tokenRepository.findBySnsType(getSnsType());

        if (snsToken != null && snsToken.getRefreshToken() != null) {
            OAuth2AccessToken token = service.refreshAccessToken(snsToken.getRefreshToken());
            SnsToken updateToken = SnsUtils.copy(token);
            updateToken.setSnsType(getSnsType());
            updateToken.setRegisterDate(new Date());

            save(updateToken);
        }
    }

    public void requestToken(String code) throws Exception {
        OAuth20Service service = createService();

        OAuth2AccessToken token = service.getAccessToken(code);
        SnsToken snsToken = SnsUtils.copy(token);
        snsToken.setSnsType(getSnsType());
        snsToken.setRegisterDate(new Date());

        save(snsToken);
    }

    public void save(SnsToken token) {
        tokenRepository.removeAllBySnsType(getSnsType());
        tokenRepository.save(token);
    }

    public SnsToken getSnsToken() {
        return tokenRepository.findBySnsType(getSnsType());
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 0)
    public void refreshTokenScheduled() {
        Iterable<SnsToken> snsTokens = tokenRepository.findAll();

        for (SnsToken token : snsTokens) {
            try {
                if (token.isExpired()) {
                    refreshToken();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public OAuth20Service getService() {
        return service;
    }
}