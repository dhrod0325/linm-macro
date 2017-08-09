package lm.macro.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lm.macro.auto.common.LmCommon;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LmGunLoginService implements LmLoginService {
    @Override
    public LmUser loadUserByIdAndPassword(String id, String pw) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(LmCommon.WEB_SERVER_URL + "/linm/login/login2.php");

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(new ArrayList<NameValuePair>() {{
            add(new BasicNameValuePair("id", id));
            add(new BasicNameValuePair("pw", pw));
        }});

        post.setEntity(formEntity);

        CloseableHttpResponse response = client.execute(post);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));

        return objectMapper.readValue(response.getEntity().getContent(), LmUser.class);
    }

    public static void main(String[] args) throws Exception {
        LmGunLoginService t = new LmGunLoginService();
        LmUser user = t.loadUserByIdAndPassword("test", "1234");
        System.out.println(user.getMacroDataList());

    }
}
