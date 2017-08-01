package lm.macro.sns.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class SnsToken {
    @Id
    private String snsType;

    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private String refreshToken;
    private String scope;
    private Date registerDate;

    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        
        if (registerDate != null) {
            long registerTime = registerDate.getTime();
            long expiredTime = TimeUnit.SECONDS.toMillis(expiresIn);

            return currentTime > registerTime + expiredTime;
        }

        return true;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSnsType() {
        return snsType;
    }

    public void setSnsType(String snsType) {
        this.snsType = snsType;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
