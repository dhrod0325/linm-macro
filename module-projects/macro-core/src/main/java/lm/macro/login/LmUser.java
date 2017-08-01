package lm.macro.login;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

public class LmUser {
    @NotEmpty
    private String id;
    @NotEmpty
    private String pw;
    private String name;
    private Date registerDate;
    private Date macroUseDate;
    private String message;
    /**
     * 1- 1개월
     * 2- 3개월
     */
    private int macroUseType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getMacroUseDate() {
        return macroUseDate;
    }

    public void setMacroUseDate(Date macroUseDate) {
        this.macroUseDate = macroUseDate;
    }

    public int getMacroUseType() {
        return macroUseType;
    }

    public void setMacroUseType(int macroUseType) {
        this.macroUseType = macroUseType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLoggedIn() {
        return id != null;
    }
}
