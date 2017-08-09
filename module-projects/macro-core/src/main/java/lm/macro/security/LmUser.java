package lm.macro.security;

import com.google.common.base.Splitter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private int macroUseType;

    private String macroInfo;

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

    public String getMacroInfo() {
        return macroInfo;
    }

    public void setMacroInfo(String macroInfo) {
        this.macroInfo = macroInfo;
    }

    @Transient
    public int getTotalUseAbleCount() {
        List<LmMacroInfo> data = getMacroUseAbleDataList();

        int sum = 0;

        for (LmMacroInfo item : data) {
            sum += item.getMacroCount();
        }

        return sum;
    }

    public List<LmMacroInfo> getMacroUseAbleDataList() {
        List<LmMacroInfo> result = new ArrayList<>();
        List<LmMacroInfo> data = getMacroDataList();

        for (LmMacroInfo item : data) {
            Date currentDate = new Date();

            if (currentDate.getTime() < item.getEndDate().getTime()) {
                result.add(item);
            }
        }

        return result;
    }

    @Transient
    public List<LmMacroInfo> getMacroDataList() {
        List<LmMacroInfo> result = new ArrayList<>();

        if (macroInfo != null) {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Set<String> list = StringUtils.commaDelimitedListToSet(macroInfo);

            for (String info : list) {
                try {
                    Iterable<String> keyValue = Splitter.on("|").split(info.trim());
                    Iterator<String> iter = keyValue.iterator();
                    String a = iter.next();
                    String b = iter.next();

                    String endDate = a.split("=")[1];
                    String macroCount = b.split("=")[1];

                    LmMacroInfo macroInfo = new LmMacroInfo();
                    macroInfo.setEndDate(transFormat.parse(endDate));
                    macroInfo.setMacroCount(Integer.parseInt(macroCount));

                    result.add(macroInfo);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public class LmMacroInfo {
        private Date endDate;
        private int macroCount;

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public int getMacroCount() {
            return macroCount;
        }

        public void setMacroCount(int macroCount) {
            this.macroCount = macroCount;
        }

        @Override
        public String toString() {
            return "LmMacroInfo{" +
                    "endDate=" + endDate +
                    ", macroCount=" + macroCount +
                    '}';
        }
    }
}
