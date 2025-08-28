package com.lark.data.dto.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class GetTenantAccessTokenRequest {
    private String appId;
    private String appSecret;

    public String getAppId() {
        return this.appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppId(final String appId) {
        this.appId = appId;
    }

    public void setAppSecret(final String appSecret) {
        this.appSecret = appSecret;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GetTenantAccessTokenRequest)) {
            return false;
        } else {
            GetTenantAccessTokenRequest other = (GetTenantAccessTokenRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$appId = this.getAppId();
                Object other$appId = other.getAppId();
                if (this$appId == null) {
                    if (other$appId != null) {
                        return false;
                    }
                } else if (!this$appId.equals(other$appId)) {
                    return false;
                }

                Object this$appSecret = this.getAppSecret();
                Object other$appSecret = other.getAppSecret();
                if (this$appSecret == null) {
                    if (other$appSecret != null) {
                        return false;
                    }
                } else if (!this$appSecret.equals(other$appSecret)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GetTenantAccessTokenRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $appId = this.getAppId();
        result = result * 59 + ($appId == null ? 43 : $appId.hashCode());
        Object $appSecret = this.getAppSecret();
        result = result * 59 + ($appSecret == null ? 43 : $appSecret.hashCode());
        return result;
    }

    public String toString() {
        return "GetTenantAccessTokenRequest(appId=" + this.getAppId() + ", appSecret=" + this.getAppSecret() + ")";
    }

    public GetTenantAccessTokenRequest() {
    }

    public GetTenantAccessTokenRequest(final String appId, final String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }
}
