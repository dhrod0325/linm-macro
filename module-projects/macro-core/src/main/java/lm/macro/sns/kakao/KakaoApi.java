package lm.macro.sns.kakao;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.builder.api.OAuth2SignatureType;

/**
 * Created by dhrod0325 on 2017-07-20.
 */
public class KakaoApi extends DefaultApi20 {
    public static KakaoApi instance() {
        return KakaoApi.InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://kauth.kakao.com/oauth/token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://kauth.kakao.com/oauth/authorize";
    }

    @Override
    public OAuth2SignatureType getSignatureType() {
        return OAuth2SignatureType.BEARER_URI_QUERY_PARAMETER;
    }

    private static class InstanceHolder {
        private static final KakaoApi INSTANCE = new KakaoApi();
    }
}
