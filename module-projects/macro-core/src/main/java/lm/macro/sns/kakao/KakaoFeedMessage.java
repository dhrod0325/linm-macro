package lm.macro.sns.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class KakaoFeedMessage implements KakaoSendMessage {
    private String object_type = "feed";
    private String title;
    private String description;
    private String image_url;
    private String web_url;

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    @Override
    public String toParam() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("object_type", "feed");
        data.put("content", new HashMap<String, Object>() {{
            put("title", title);
            put("description", description);
            put("image_url", image_url);
            put("link", new HashMap<String, Object>() {{
                put("web_url", web_url);
            }});
        }});

        return new ObjectMapper().writeValueAsString(data);
    }
}
