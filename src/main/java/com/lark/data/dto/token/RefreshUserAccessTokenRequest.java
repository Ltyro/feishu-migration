package com.lark.data.dto.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class RefreshUserAccessTokenRequest {
    private String grantType;
    private String refreshToken;

    public String getGrantType() {
        return this.grantType;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setGrantType(final String grantType) {
        this.grantType = grantType;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RefreshUserAccessTokenRequest)) {
            return false;
        } else {
            RefreshUserAccessTokenRequest other = (RefreshUserAccessTokenRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$grantType = this.getGrantType();
                Object other$grantType = other.getGrantType();
                if (this$grantType == null) {
                    if (other$grantType != null) {
                        return false;
                    }
                } else if (!this$grantType.equals(other$grantType)) {
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

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RefreshUserAccessTokenRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $grantType = this.getGrantType();
        result = result * 59 + ($grantType == null ? 43 : $grantType.hashCode());
        Object $refreshToken = this.getRefreshToken();
        result = result * 59 + ($refreshToken == null ? 43 : $refreshToken.hashCode());
        return result;
    }

    public String toString() {
        return "RefreshUserAccessTokenRequest(grantType=" + this.getGrantType() + ", refreshToken=" + this.getRefreshToken() + ")";
    }

    public RefreshUserAccessTokenRequest(final String grantType, final String refreshToken) {
        this.grantType = grantType;
        this.refreshToken = refreshToken;
    }
}
