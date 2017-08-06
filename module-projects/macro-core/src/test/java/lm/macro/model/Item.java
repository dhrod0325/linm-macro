package lm.macro.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Root(name = "item")
public class Item implements Serializable {
    @Element(required = false)
    private String title;

    @Element(required = false)
    private String originallink;

    @Element(required = false)
    private String link;

    @Element(required = false)
    private String description;

    @Element(required = false)
    private String pubDate;

    public String getTitleText() {
        try {
            return title;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getDescriptionText() {
        try {
            return description;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginallink() {
        return originallink;
    }

    public void setOriginallink(String originallink) {
        this.originallink = originallink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDate(String format) {
        try {
            SimpleDateFormat lec = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
            Date date = lec.parse(pubDate);

            return new SimpleDateFormat(format).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
