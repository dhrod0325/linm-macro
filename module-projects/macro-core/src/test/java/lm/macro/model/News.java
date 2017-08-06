package lm.macro.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class News {
    @Attribute(required = false)
    private String version;

    @Element(name = "channel", required = true)
    private Channel channel;

    public List<Item> getItems() {
        return channel.getItems();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}

