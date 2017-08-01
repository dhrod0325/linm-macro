package lm.macro.sns.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import lm.macro.sns.util.SnsUtils;
import lm.macro.sns.kakao.KakaoApi;
import lm.macro.sns.kakao.KakaoFeedMessage;
import lm.macro.sns.model.SnsToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class KakaoSnsService extends SnsAbstractService {
    public static final String callback = "http://localhost:8080/sns/oauthCallback?mode=kakao";
    public static final String apiKey = "2d7e749627268ec53f46cd8de0d251ab";

    @Override
    public String getSnsType() {
        return "kakao";
    }

    @Override
    public OAuth20Service createService() {
        return new ServiceBuilder(apiKey)
                .apiSecret("kakao")
                .callback(callback)
                .build(KakaoApi.instance());
    }

    public Response feed(String title, String description, String imageUrl, String webUrl) throws Exception {
        KakaoFeedMessage feedMessage = new KakaoFeedMessage();
        feedMessage.setTitle(title);
        feedMessage.setDescription(description);
        feedMessage.setImage_url(imageUrl);
        feedMessage.setWeb_url(webUrl);

        SnsToken token = getSnsToken();

        if (token != null) {
            OAuth2AccessToken accessToken = SnsUtils.toOAuth2AccessToken(token);

            OAuthRequest request = new OAuthRequest(Verb.POST, "https://kapi.kakao.com/v2/api/talk/memo/default/send");
            request.addBodyParameter("template_object", feedMessage.toParam());
            getService().signRequest(accessToken, request);

            return getService().execute(request);
        }

        return null;
    }
}
