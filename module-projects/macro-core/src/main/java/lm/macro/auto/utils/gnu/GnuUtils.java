package lm.macro.auto.utils.gnu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

public class GnuUtils {
    public static List<GnuBoardItem> getLatestList(String url, String bo_table) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(String.format("%s?bo_table=%s", url, bo_table));
        HttpResponse httpResponse = client.execute(get);

        return new ObjectMapper().readValue(httpResponse.getEntity().getContent(), new TypeReference<List<GnuBoardItem>>() {
        });
    }
}
