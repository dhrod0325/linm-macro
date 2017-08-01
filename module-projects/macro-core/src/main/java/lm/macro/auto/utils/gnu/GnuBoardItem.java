package lm.macro.auto.utils.gnu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GnuBoardItem {
    private String href;
    private String wr_subject;
    private String wr_content;
    private String wr_id;

    public String getWr_subject() {
        return wr_subject;
    }

    public void setWr_subject(String wr_subject) {
        this.wr_subject = wr_subject;
    }

    public String getWr_content() {
        return wr_content;
    }

    public void setWr_content(String wr_content) {
        this.wr_content = wr_content;
    }

    public String getWr_id() {
        return wr_id;
    }

    public void setWr_id(String wr_id) {
        this.wr_id = wr_id;
    }

    public String getContentShort() {
        int cutLength = 20;
        if (wr_content.length() > cutLength) {
            return StringUtils.substring(wr_content, 0, cutLength) + "...";
        } else {
            return wr_content;
        }
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
