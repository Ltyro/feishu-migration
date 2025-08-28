package com.lark.data.dto.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lark.data.bean.ResponseEntity;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class TenantAccessTokenDTO extends ResponseEntity {
    private String tenantAccessToken;
    private int expire;

    public TenantAccessTokenDTO() {
    }

    public String getTenantAccessToken() {
        return this.tenantAccessToken;
    }

    public int getExpire() {
        return this.expire;
    }

    public void setTenantAccessToken(final String tenantAccessToken) {
        this.tenantAccessToken = tenantAccessToken;
    }

    public void setExpire(final int expire) {
        this.expire = expire;
    }

    public String toString() {
        return "TenantAccessTokenDTO(tenantAccessToken=" + this.getTenantAccessToken() + ", expire=" + this.getExpire() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TenantAccessTokenDTO)) {
            return false;
        } else {
            TenantAccessTokenDTO other = (TenantAccessTokenDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else if (this.getExpire() != other.getExpire()) {
                return false;
            } else {
                Object this$tenantAccessToken = this.getTenantAccessToken();
                Object other$tenantAccessToken = other.getTenantAccessToken();
                if (this$tenantAccessToken == null) {
                    if (other$tenantAccessToken != null) {
                        return false;
                    }
                } else if (!this$tenantAccessToken.equals(other$tenantAccessToken)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TenantAccessTokenDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + this.getExpire();
        Object $tenantAccessToken = this.getTenantAccessToken();
        result = result * 59 + ($tenantAccessToken == null ? 43 : $tenantAccessToken.hashCode());
        return result;
    }
}
