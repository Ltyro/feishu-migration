package com.lark.data.dto.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class UserAccessTokenDTO implements Serializable {
    private String accessToken;
    private String sid;
    private String tokenType;
    private Integer expiresIn;
    private String name;
    private String enName;
    private String avatarUrl;
    private String avatarThumb;
    private String avatarMiddle;
    private String avatarBig;
    private String openId;
    private String unionId;
    private String email;
    private String userId;
    private String mobile;
    private String tenantKey;
    private Integer refreshExpiresIn;
    private String refreshToken;
    private String enterprise_email;

    public UserAccessTokenDTO() {
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getSid() {
        return this.sid;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public Integer getExpiresIn() {
        return this.expiresIn;
    }

    public String getName() {
        return this.name;
    }

    public String getEnName() {
        return this.enName;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getAvatarThumb() {
        return this.avatarThumb;
    }

    public String getAvatarMiddle() {
        return this.avatarMiddle;
    }

    public String getAvatarBig() {
        return this.avatarBig;
    }

    public String getOpenId() {
        return this.openId;
    }

    public String getUnionId() {
        return this.unionId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getTenantKey() {
        return this.tenantKey;
    }

    public Integer getRefreshExpiresIn() {
        return this.refreshExpiresIn;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String getEnterprise_email() {
        return this.enterprise_email;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public void setSid(final String sid) {
        this.sid = sid;
    }

    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(final Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setEnName(final String enName) {
        this.enName = enName;
    }

    public void setAvatarUrl(final String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setAvatarThumb(final String avatarThumb) {
        this.avatarThumb = avatarThumb;
    }

    public void setAvatarMiddle(final String avatarMiddle) {
        this.avatarMiddle = avatarMiddle;
    }

    public void setAvatarBig(final String avatarBig) {
        this.avatarBig = avatarBig;
    }

    public void setOpenId(final String openId) {
        this.openId = openId;
    }

    public void setUnionId(final String unionId) {
        this.unionId = unionId;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public void setTenantKey(final String tenantKey) {
        this.tenantKey = tenantKey;
    }

    public void setRefreshExpiresIn(final Integer refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setEnterprise_email(final String enterprise_email) {
        this.enterprise_email = enterprise_email;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof UserAccessTokenDTO)) {
            return false;
        } else {
            UserAccessTokenDTO other = (UserAccessTokenDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$expiresIn = this.getExpiresIn();
                Object other$expiresIn = other.getExpiresIn();
                if (this$expiresIn == null) {
                    if (other$expiresIn != null) {
                        return false;
                    }
                } else if (!this$expiresIn.equals(other$expiresIn)) {
                    return false;
                }

                Object this$refreshExpiresIn = this.getRefreshExpiresIn();
                Object other$refreshExpiresIn = other.getRefreshExpiresIn();
                if (this$refreshExpiresIn == null) {
                    if (other$refreshExpiresIn != null) {
                        return false;
                    }
                } else if (!this$refreshExpiresIn.equals(other$refreshExpiresIn)) {
                    return false;
                }

                Object this$accessToken = this.getAccessToken();
                Object other$accessToken = other.getAccessToken();
                if (this$accessToken == null) {
                    if (other$accessToken != null) {
                        return false;
                    }
                } else if (!this$accessToken.equals(other$accessToken)) {
                    return false;
                }

                Object this$sid = this.getSid();
                Object other$sid = other.getSid();
                if (this$sid == null) {
                    if (other$sid != null) {
                        return false;
                    }
                } else if (!this$sid.equals(other$sid)) {
                    return false;
                }

                Object this$tokenType = this.getTokenType();
                Object other$tokenType = other.getTokenType();
                if (this$tokenType == null) {
                    if (other$tokenType != null) {
                        return false;
                    }
                } else if (!this$tokenType.equals(other$tokenType)) {
                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                Object this$enName = this.getEnName();
                Object other$enName = other.getEnName();
                if (this$enName == null) {
                    if (other$enName != null) {
                        return false;
                    }
                } else if (!this$enName.equals(other$enName)) {
                    return false;
                }

                Object this$avatarUrl = this.getAvatarUrl();
                Object other$avatarUrl = other.getAvatarUrl();
                if (this$avatarUrl == null) {
                    if (other$avatarUrl != null) {
                        return false;
                    }
                } else if (!this$avatarUrl.equals(other$avatarUrl)) {
                    return false;
                }

                Object this$avatarThumb = this.getAvatarThumb();
                Object other$avatarThumb = other.getAvatarThumb();
                if (this$avatarThumb == null) {
                    if (other$avatarThumb != null) {
                        return false;
                    }
                } else if (!this$avatarThumb.equals(other$avatarThumb)) {
                    return false;
                }

                Object this$avatarMiddle = this.getAvatarMiddle();
                Object other$avatarMiddle = other.getAvatarMiddle();
                if (this$avatarMiddle == null) {
                    if (other$avatarMiddle != null) {
                        return false;
                    }
                } else if (!this$avatarMiddle.equals(other$avatarMiddle)) {
                    return false;
                }

                Object this$avatarBig = this.getAvatarBig();
                Object other$avatarBig = other.getAvatarBig();
                if (this$avatarBig == null) {
                    if (other$avatarBig != null) {
                        return false;
                    }
                } else if (!this$avatarBig.equals(other$avatarBig)) {
                    return false;
                }

                Object this$openId = this.getOpenId();
                Object other$openId = other.getOpenId();
                if (this$openId == null) {
                    if (other$openId != null) {
                        return false;
                    }
                } else if (!this$openId.equals(other$openId)) {
                    return false;
                }

                Object this$unionId = this.getUnionId();
                Object other$unionId = other.getUnionId();
                if (this$unionId == null) {
                    if (other$unionId != null) {
                        return false;
                    }
                } else if (!this$unionId.equals(other$unionId)) {
                    return false;
                }

                Object this$email = this.getEmail();
                Object other$email = other.getEmail();
                if (this$email == null) {
                    if (other$email != null) {
                        return false;
                    }
                } else if (!this$email.equals(other$email)) {
                    return false;
                }

                Object this$userId = this.getUserId();
                Object other$userId = other.getUserId();
                if (this$userId == null) {
                    if (other$userId != null) {
                        return false;
                    }
                } else if (!this$userId.equals(other$userId)) {
                    return false;
                }

                Object this$mobile = this.getMobile();
                Object other$mobile = other.getMobile();
                if (this$mobile == null) {
                    if (other$mobile != null) {
                        return false;
                    }
                } else if (!this$mobile.equals(other$mobile)) {
                    return false;
                }

                Object this$tenantKey = this.getTenantKey();
                Object other$tenantKey = other.getTenantKey();
                if (this$tenantKey == null) {
                    if (other$tenantKey != null) {
                        return false;
                    }
                } else if (!this$tenantKey.equals(other$tenantKey)) {
                    return false;
                }

                Object this$refreshToken = this.getRefreshToken();
                Object other$refreshToken = other.getRefreshToken();
                if (this$refreshToken == null) {
                    if (other$refreshToken != null) {
                        return false;
                    }
                } else if (!this$refreshToken.equals(other$refreshToken)) {
                    return false;
                }

                Object this$enterprise_email = this.getEnterprise_email();
                Object other$enterprise_email = other.getEnterprise_email();
                if (this$enterprise_email == null) {
                    if (other$enterprise_email != null) {
                        return false;
                    }
                } else if (!this$enterprise_email.equals(other$enterprise_email)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserAccessTokenDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $expiresIn = this.getExpiresIn();
        result = result * 59 + ($expiresIn == null ? 43 : $expiresIn.hashCode());
        Object $refreshExpiresIn = this.getRefreshExpiresIn();
        result = result * 59 + ($refreshExpiresIn == null ? 43 : $refreshExpiresIn.hashCode());
        Object $accessToken = this.getAccessToken();
        result = result * 59 + ($accessToken == null ? 43 : $accessToken.hashCode());
        Object $sid = this.getSid();
        result = result * 59 + ($sid == null ? 43 : $sid.hashCode());
        Object $tokenType = this.getTokenType();
        result = result * 59 + ($tokenType == null ? 43 : $tokenType.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $enName = this.getEnName();
        result = result * 59 + ($enName == null ? 43 : $enName.hashCode());
        Object $avatarUrl = this.getAvatarUrl();
        result = result * 59 + ($avatarUrl == null ? 43 : $avatarUrl.hashCode());
        Object $avatarThumb = this.getAvatarThumb();
        result = result * 59 + ($avatarThumb == null ? 43 : $avatarThumb.hashCode());
        Object $avatarMiddle = this.getAvatarMiddle();
        result = result * 59 + ($avatarMiddle == null ? 43 : $avatarMiddle.hashCode());
        Object $avatarBig = this.getAvatarBig();
        result = result * 59 + ($avatarBig == null ? 43 : $avatarBig.hashCode());
        Object $openId = this.getOpenId();
        result = result * 59 + ($openId == null ? 43 : $openId.hashCode());
        Object $unionId = this.getUnionId();
        result = result * 59 + ($unionId == null ? 43 : $unionId.hashCode());
        Object $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        Object $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        Object $mobile = this.getMobile();
        result = result * 59 + ($mobile == null ? 43 : $mobile.hashCode());
        Object $tenantKey = this.getTenantKey();
        result = result * 59 + ($tenantKey == null ? 43 : $tenantKey.hashCode());
        Object $refreshToken = this.getRefreshToken();
        result = result * 59 + ($refreshToken == null ? 43 : $refreshToken.hashCode());
        Object $enterprise_email = this.getEnterprise_email();
        result = result * 59 + ($enterprise_email == null ? 43 : $enterprise_email.hashCode());
        return result;
    }

    public String toString() {
        return "UserAccessTokenDTO(accessToken=" + this.getAccessToken() + ", sid=" + this.getSid() + ", tokenType=" + this.getTokenType() + ", expiresIn=" + this.getExpiresIn() + ", name=" + this.getName() + ", enName=" + this.getEnName() + ", avatarUrl=" + this.getAvatarUrl() + ", avatarThumb=" + this.getAvatarThumb() + ", avatarMiddle=" + this.getAvatarMiddle() + ", avatarBig=" + this.getAvatarBig() + ", openId=" + this.getOpenId() + ", unionId=" + this.getUnionId() + ", email=" + this.getEmail() + ", userId=" + this.getUserId() + ", mobile=" + this.getMobile() + ", tenantKey=" + this.getTenantKey() + ", refreshExpiresIn=" + this.getRefreshExpiresIn() + ", refreshToken=" + this.getRefreshToken() + ", enterprise_email=" + this.getEnterprise_email() + ")";
    }
}
