package lm.macro.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import lm.macro.auto.common.LmCommon;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class LmGunLoginService implements LmLoginService {
    @Override
    public LmUser loadUserByIdAndPassword(String id, String pw) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(LmCommon.WEB_SERVER_URL + "/linm/login/login.php");

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(new ArrayList<NameValuePair>() {{
            add(new BasicNameValuePair("id", id));
            add(new BasicNameValuePair("pw", pw));
        }});


        post.setEntity(formEntity);

        CloseableHttpResponse response = client.execute(post);

        return new ObjectMapper().readValue(response.getEntity().getContent(), LmUser.class);
    }
}
