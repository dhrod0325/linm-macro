package lm.macro.security;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class LmUser implements UserDetails {
    @Id
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

    @Transient
    public List<GrantedAuthority> getAuthorityList() {
        String role = "GRANT_" + macroUseType;
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorityList();
    }

    @Transient
    @Override
    public String getPassword() {
        return pw;
    }

    @Transient
    @Override
    public String getUsername() {
        return id;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}
