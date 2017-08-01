package lm.macro.auto.utils.cmd;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Netstat {
    private String type;
    private String src;
    private String dest;
    private String state;
    private int pid;

    public Netstat() {
    }

    public Netstat(String type, String src, String dest, String state, int pid) {
        this.type = type;
        this.src = src;
        this.dest = dest;
        this.state = state;
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
