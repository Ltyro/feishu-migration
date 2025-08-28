package com.lark.data.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lark.data.bean.BasicEntity;

@TableName("transfer_result")
public class TransferResult extends BasicEntity {
    private String oldUserOpenId;
    private String newUserOpenId;
    private String tenantType;
    private String url;
    private String token;
    private String sheetId;

    protected TransferResult(final TransferResultBuilder<?, ?> b) {
        super(b);
        this.oldUserOpenId = b.oldUserOpenId;
        this.newUserOpenId = b.newUserOpenId;
        this.tenantType = b.tenantType;
        this.url = b.url;
        this.token = b.token;
        this.sheetId = b.sheetId;
    }

    public static TransferResultBuilder<?, ?> builder() {
        return new TransferResultBuilderImpl();
    }

    public String getOldUserOpenId() {
        return this.oldUserOpenId;
    }

    public String getNewUserOpenId() {
        return this.newUserOpenId;
    }

    public String getTenantType() {
        return this.tenantType;
    }

    public String getUrl() {
        return this.url;
    }

    public String getToken() {
        return this.token;
    }

    public String getSheetId() {
        return this.sheetId;
    }

    public void setOldUserOpenId(final String oldUserOpenId) {
        this.oldUserOpenId = oldUserOpenId;
    }

    public void setNewUserOpenId(final String newUserOpenId) {
        this.newUserOpenId = newUserOpenId;
    }

    public void setTenantType(final String tenantType) {
        this.tenantType = tenantType;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setSheetId(final String sheetId) {
        this.sheetId = sheetId;
    }

    public String toString() {
        return "TransferResult(oldUserOpenId=" + this.getOldUserOpenId() + ", newUserOpenId=" + this.getNewUserOpenId() + ", tenantType=" + this.getTenantType() + ", url=" + this.getUrl() + ", token=" + this.getToken() + ", sheetId=" + this.getSheetId() + ")";
    }

    public TransferResult() {
    }

    public TransferResult(final String oldUserOpenId, final String newUserOpenId, final String tenantType, final String url, final String token, final String sheetId) {
        this.oldUserOpenId = oldUserOpenId;
        this.newUserOpenId = newUserOpenId;
        this.tenantType = tenantType;
        this.url = url;
        this.token = token;
        this.sheetId = sheetId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TransferResult)) {
            return false;
        } else {
            TransferResult other = (TransferResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$oldUserOpenId = this.getOldUserOpenId();
                Object other$oldUserOpenId = other.getOldUserOpenId();
                if (this$oldUserOpenId == null) {
                    if (other$oldUserOpenId != null) {
                        return false;
                    }
                } else if (!this$oldUserOpenId.equals(other$oldUserOpenId)) {
                    return false;
                }

                Object this$newUserOpenId = this.getNewUserOpenId();
                Object other$newUserOpenId = other.getNewUserOpenId();
                if (this$newUserOpenId == null) {
                    if (other$newUserOpenId != null) {
                        return false;
                    }
                } else if (!this$newUserOpenId.equals(other$newUserOpenId)) {
                    return false;
                }

                Object this$tenantType = this.getTenantType();
                Object other$tenantType = other.getTenantType();
                if (this$tenantType == null) {
                    if (other$tenantType != null) {
                        return false;
                    }
                } else if (!this$tenantType.equals(other$tenantType)) {
                    return false;
                }

                Object this$url = this.getUrl();
                Object other$url = other.getUrl();
                if (this$url == null) {
                    if (other$url != null) {
                        return false;
                    }
                } else if (!this$url.equals(other$url)) {
                    return false;
                }

                Object this$token = this.getToken();
                Object other$token = other.getToken();
                if (this$token == null) {
                    if (other$token != null) {
                        return false;
                    }
                } else if (!this$token.equals(other$token)) {
                    return false;
                }

                Object this$sheetId = this.getSheetId();
                Object other$sheetId = other.getSheetId();
                if (this$sheetId == null) {
                    if (other$sheetId != null) {
                        return false;
                    }
                } else if (!this$sheetId.equals(other$sheetId)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TransferResult;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $oldUserOpenId = this.getOldUserOpenId();
        result = result * 59 + ($oldUserOpenId == null ? 43 : $oldUserOpenId.hashCode());
        Object $newUserOpenId = this.getNewUserOpenId();
        result = result * 59 + ($newUserOpenId == null ? 43 : $newUserOpenId.hashCode());
        Object $tenantType = this.getTenantType();
        result = result * 59 + ($tenantType == null ? 43 : $tenantType.hashCode());
        Object $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        Object $sheetId = this.getSheetId();
        result = result * 59 + ($sheetId == null ? 43 : $sheetId.hashCode());
        return result;
    }

    public abstract static class TransferResultBuilder<C extends TransferResult, B extends TransferResultBuilder<C, B>> extends BasicEntity.BasicEntityBuilder<C, B> {
        private String oldUserOpenId;
        private String newUserOpenId;
        private String tenantType;
        private String url;
        private String token;
        private String sheetId;

        public TransferResultBuilder() {
        }

        protected abstract B self();

        public abstract C build();

        public B oldUserOpenId(final String oldUserOpenId) {
            this.oldUserOpenId = oldUserOpenId;
            return (B)this.self();
        }

        public B newUserOpenId(final String newUserOpenId) {
            this.newUserOpenId = newUserOpenId;
            return (B)this.self();
        }

        public B tenantType(final String tenantType) {
            this.tenantType = tenantType;
            return (B)this.self();
        }

        public B url(final String url) {
            this.url = url;
            return (B)this.self();
        }

        public B token(final String token) {
            this.token = token;
            return (B)this.self();
        }

        public B sheetId(final String sheetId) {
            this.sheetId = sheetId;
            return (B)this.self();
        }

        public String toString() {
            return "TransferResult.TransferResultBuilder(super=" + super.toString() + ", oldUserOpenId=" + this.oldUserOpenId + ", newUserOpenId=" + this.newUserOpenId + ", tenantType=" + this.tenantType + ", url=" + this.url + ", token=" + this.token + ", sheetId=" + this.sheetId + ")";
        }
    }

    private static final class TransferResultBuilderImpl extends TransferResultBuilder<TransferResult, TransferResultBuilderImpl> {
        private TransferResultBuilderImpl() {
        }

        protected TransferResultBuilderImpl self() {
            return this;
        }

        public TransferResult build() {
            return new TransferResult(this);
        }
    }
}
