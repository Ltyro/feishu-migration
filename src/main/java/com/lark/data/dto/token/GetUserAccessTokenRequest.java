package com.lark.data.dto.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class GetUserAccessTokenRequest {
    private String grantType;
    private String code;

    public String getGrantType() {
        return this.grantType;
    }

    public String getCode() {
        return this.code;
    }

    public void setGrantType(final String grantType) {
        this.grantType = grantType;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GetUserAccessTokenRequest)) {
            return false;
        } else {
            GetUserAccessTokenRequest other = (GetUserAccessTokenRequest)o;
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

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GetUserAccessTokenRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $grantType = this.getGrantType();
        result = result * 59 + ($grantType == null ? 43 : $grantType.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        return result;
    }

    public String toString() {
        return "GetUserAccessTokenRequest(grantType=" + this.getGrantType() + ", code=" + this.getCode() + ")";
    }

    public GetUserAccessTokenRequest(final String grantType, final String code) {
        this.grantType = grantType;
        this.code = code;
    }
}
