package lm.macro.auto.server;

import lm.macro.security.LmUser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class LmServerCommunicationService {
    private static final String SERVER_URL = "http://linm.ideapeople.co.kr";

    public void connectCheck(LmUser user) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(String.format("%s/linm/module/uploadScreen.php", SERVER_URL));
        
    }

    public HttpResponse sendToImageFile(String userName, File file) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(String.format("%s/linm/module/uploadScreen.php", SERVER_URL));
        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("screen", fileBody);
        builder.addTextBody("userName", userName);

        HttpEntity entity = builder.build();
        post.setEntity(entity);

        return client.execute(post);
    }
}
