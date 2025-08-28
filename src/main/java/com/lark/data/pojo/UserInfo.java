package com.lark.data.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lark.data.bean.BasicEntity;

@TableName("user_info")
public class UserInfo extends BasicEntity {
    private String oldUserId;
    private String oldUserName;
    private String oldUserEmail;
    private String oldUserMobile;
    private String oldUserOpenId;
    private String oldUserToken;
    private String oldUserRefreshToken;
    private String oldRootFolderToken;
    private String newUserId;
    private String newUserName;
    private String newUserEmail;
    private String newUserMobile;
    private String newUserOpenId;
    private String newUserToken;
    private String newUserRefreshToken;
    private String newRootFolderToken;
    private String status;
    private String procStatus;
    private String procMsg;

    protected UserInfo(final UserInfoBuilder<?, ?> b) {
        super(b);
        this.oldUserId = b.oldUserId;
        this.oldUserName = b.oldUserName;
        this.oldUserEmail = b.oldUserEmail;
        this.oldUserMobile = b.oldUserMobile;
        this.oldUserOpenId = b.oldUserOpenId;
        this.oldUserToken = b.oldUserToken;
        this.oldUserRefreshToken = b.oldUserRefreshToken;
        this.oldRootFolderToken = b.oldRootFolderToken;
        this.newUserId = b.newUserId;
        this.newUserName = b.newUserName;
        this.newUserEmail = b.newUserEmail;
        this.newUserMobile = b.newUserMobile;
        this.newUserOpenId = b.newUserOpenId;
        this.newUserToken = b.newUserToken;
        this.newUserRefreshToken = b.newUserRefreshToken;
        this.newRootFolderToken = b.newRootFolderToken;
        this.status = b.status;
        this.procStatus = b.procStatus;
        this.procMsg = b.procMsg;
    }

    public static UserInfoBuilder<?, ?> builder() {
        return new UserInfoBuilderImpl();
    }

    public String getOldUserId() {
        return this.oldUserId;
    }

    public String getOldUserName() {
        return this.oldUserName;
    }

    public String getOldUserEmail() {
        return this.oldUserEmail;
    }

    public String getOldUserMobile() {
        return this.oldUserMobile;
    }

    public String getOldUserOpenId() {
        return this.oldUserOpenId;
    }

    public String getOldUserToken() {
        return this.oldUserToken;
    }

    public String getOldUserRefreshToken() {
        return this.oldUserRefreshToken;
    }

    public String getOldRootFolderToken() {
        return this.oldRootFolderToken;
    }

    public String getNewUserId() {
        return this.newUserId;
    }

    public String getNewUserName() {
        return this.newUserName;
    }

    public String getNewUserEmail() {
        return this.newUserEmail;
    }

    public String getNewUserMobile() {
        return this.newUserMobile;
    }

    public String getNewUserOpenId() {
        return this.newUserOpenId;
    }

    public String getNewUserToken() {
        return this.newUserToken;
    }

    public String getNewUserRefreshToken() {
        return this.newUserRefreshToken;
    }

    public String getNewRootFolderToken() {
        return this.newRootFolderToken;
    }

    public String getStatus() {
        return this.status;
    }

    public String getProcStatus() {
        return this.procStatus;
    }

    public String getProcMsg() {
        return this.procMsg;
    }

    public void setOldUserId(final String oldUserId) {
        this.oldUserId = oldUserId;
    }

    public void setOldUserName(final String oldUserName) {
        this.oldUserName = oldUserName;
    }

    public void setOldUserEmail(final String oldUserEmail) {
        this.oldUserEmail = oldUserEmail;
    }

    public void setOldUserMobile(final String oldUserMobile) {
        this.oldUserMobile = oldUserMobile;
    }

    public void setOldUserOpenId(final String oldUserOpenId) {
        this.oldUserOpenId = oldUserOpenId;
    }

    public void setOldUserToken(final String oldUserToken) {
        this.oldUserToken = oldUserToken;
    }

    public void setOldUserRefreshToken(final String oldUserRefreshToken) {
        this.oldUserRefreshToken = oldUserRefreshToken;
    }

    public void setOldRootFolderToken(final String oldRootFolderToken) {
        this.oldRootFolderToken = oldRootFolderToken;
    }

    public void setNewUserId(final String newUserId) {
        this.newUserId = newUserId;
    }

    public void setNewUserName(final String newUserName) {
        this.newUserName = newUserName;
    }

    public void setNewUserEmail(final String newUserEmail) {
        this.newUserEmail = newUserEmail;
    }

    public void setNewUserMobile(final String newUserMobile) {
        this.newUserMobile = newUserMobile;
    }

    public void setNewUserOpenId(final String newUserOpenId) {
        this.newUserOpenId = newUserOpenId;
    }

    public void setNewUserToken(final String newUserToken) {
        this.newUserToken = newUserToken;
    }

    public void setNewUserRefreshToken(final String newUserRefreshToken) {
        this.newUserRefreshToken = newUserRefreshToken;
    }

    public void setNewRootFolderToken(final String newRootFolderToken) {
        this.newRootFolderToken = newRootFolderToken;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setProcStatus(final String procStatus) {
        this.procStatus = procStatus;
    }

    public void setProcMsg(final String procMsg) {
        this.procMsg = procMsg;
    }

    public String toString() {
        return "UserInfo(oldUserId=" + this.getOldUserId() + ", oldUserName=" + this.getOldUserName() + ", oldUserEmail=" + this.getOldUserEmail() + ", oldUserMobile=" + this.getOldUserMobile() + ", oldUserOpenId=" + this.getOldUserOpenId() + ", oldUserToken=" + this.getOldUserToken() + ", oldUserRefreshToken=" + this.getOldUserRefreshToken() + ", oldRootFolderToken=" + this.getOldRootFolderToken() + ", newUserId=" + this.getNewUserId() + ", newUserName=" + this.getNewUserName() + ", newUserEmail=" + this.getNewUserEmail() + ", newUserMobile=" + this.getNewUserMobile() + ", newUserOpenId=" + this.getNewUserOpenId() + ", newUserToken=" + this.getNewUserToken() + ", newUserRefreshToken=" + this.getNewUserRefreshToken() + ", newRootFolderToken=" + this.getNewRootFolderToken() + ", status=" + this.getStatus() + ", procStatus=" + this.getProcStatus() + ", procMsg=" + this.getProcMsg() + ")";
    }

    public UserInfo() {
    }

    public UserInfo(final String oldUserId, final String oldUserName, final String oldUserEmail, final String oldUserMobile, final String oldUserOpenId, final String oldUserToken, final String oldUserRefreshToken, final String oldRootFolderToken, final String newUserId, final String newUserName, final String newUserEmail, final String newUserMobile, final String newUserOpenId, final String newUserToken, final String newUserRefreshToken, final String newRootFolderToken, final String status, final String procStatus, final String procMsg) {
        this.oldUserId = oldUserId;
        this.oldUserName = oldUserName;
        this.oldUserEmail = oldUserEmail;
        this.oldUserMobile = oldUserMobile;
        this.oldUserOpenId = oldUserOpenId;
        this.oldUserToken = oldUserToken;
        this.oldUserRefreshToken = oldUserRefreshToken;
        this.oldRootFolderToken = oldRootFolderToken;
        this.newUserId = newUserId;
        this.newUserName = newUserName;
        this.newUserEmail = newUserEmail;
        this.newUserMobile = newUserMobile;
        this.newUserOpenId = newUserOpenId;
        this.newUserToken = newUserToken;
        this.newUserRefreshToken = newUserRefreshToken;
        this.newRootFolderToken = newRootFolderToken;
        this.status = status;
        this.procStatus = procStatus;
        this.procMsg = procMsg;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof UserInfo)) {
            return false;
        } else {
            UserInfo other = (UserInfo)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$oldUserId = this.getOldUserId();
                Object other$oldUserId = other.getOldUserId();
                if (this$oldUserId == null) {
                    if (other$oldUserId != null) {
                        return false;
                    }
                } else if (!this$oldUserId.equals(other$oldUserId)) {
                    return false;
                }

                Object this$oldUserName = this.getOldUserName();
                Object other$oldUserName = other.getOldUserName();
                if (this$oldUserName == null) {
                    if (other$oldUserName != null) {
                        return false;
                    }
                } else if (!this$oldUserName.equals(other$oldUserName)) {
                    return false;
                }

                Object this$oldUserEmail = this.getOldUserEmail();
                Object other$oldUserEmail = other.getOldUserEmail();
                if (this$oldUserEmail == null) {
                    if (other$oldUserEmail != null) {
                        return false;
                    }
                } else if (!this$oldUserEmail.equals(other$oldUserEmail)) {
                    return false;
                }

                Object this$oldUserMobile = this.getOldUserMobile();
                Object other$oldUserMobile = other.getOldUserMobile();
                if (this$oldUserMobile == null) {
                    if (other$oldUserMobile != null) {
                        return false;
                    }
                } else if (!this$oldUserMobile.equals(other$oldUserMobile)) {
                    return false;
                }

                Object this$oldUserOpenId = this.getOldUserOpenId();
                Object other$oldUserOpenId = other.getOldUserOpenId();
                if (this$oldUserOpenId == null) {
                    if (other$oldUserOpenId != null) {
                        return false;
                    }
                } else if (!this$oldUserOpenId.equals(other$oldUserOpenId)) {
                    return false;
                }

                Object this$oldUserToken = this.getOldUserToken();
                Object other$oldUserToken = other.getOldUserToken();
                if (this$oldUserToken == null) {
                    if (other$oldUserToken != null) {
                        return false;
                    }
                } else if (!this$oldUserToken.equals(other$oldUserToken)) {
                    return false;
                }

                Object this$oldUserRefreshToken = this.getOldUserRefreshToken();
                Object other$oldUserRefreshToken = other.getOldUserRefreshToken();
                if (this$oldUserRefreshToken == null) {
                    if (other$oldUserRefreshToken != null) {
                        return false;
                    }
                } else if (!this$oldUserRefreshToken.equals(other$oldUserRefreshToken)) {
                    return false;
                }

                Object this$oldRootFolderToken = this.getOldRootFolderToken();
                Object other$oldRootFolderToken = other.getOldRootFolderToken();
                if (this$oldRootFolderToken == null) {
                    if (other$oldRootFolderToken != null) {
                        return false;
                    }
                } else if (!this$oldRootFolderToken.equals(other$oldRootFolderToken)) {
                    return false;
                }

                Object this$newUserId = this.getNewUserId();
                Object other$newUserId = other.getNewUserId();
                if (this$newUserId == null) {
                    if (other$newUserId != null) {
                        return false;
                    }
                } else if (!this$newUserId.equals(other$newUserId)) {
                    return false;
                }

                Object this$newUserName = this.getNewUserName();
                Object other$newUserName = other.getNewUserName();
                if (this$newUserName == null) {
                    if (other$newUserName != null) {
                        return false;
                    }
                } else if (!this$newUserName.equals(other$newUserName)) {
                    return false;
                }

                Object this$newUserEmail = this.getNewUserEmail();
                Object other$newUserEmail = other.getNewUserEmail();
                if (this$newUserEmail == null) {
                    if (other$newUserEmail != null) {
                        return false;
                    }
                } else if (!this$newUserEmail.equals(other$newUserEmail)) {
                    return false;
                }

                Object this$newUserMobile = this.getNewUserMobile();
                Object other$newUserMobile = other.getNewUserMobile();
                if (this$newUserMobile == null) {
                    if (other$newUserMobile != null) {
                        return false;
                    }
                } else if (!this$newUserMobile.equals(other$newUserMobile)) {
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

                Object this$newUserToken = this.getNewUserToken();
                Object other$newUserToken = other.getNewUserToken();
                if (this$newUserToken == null) {
                    if (other$newUserToken != null) {
                        return false;
                    }
                } else if (!this$newUserToken.equals(other$newUserToken)) {
                    return false;
                }

                Object this$newUserRefreshToken = this.getNewUserRefreshToken();
                Object other$newUserRefreshToken = other.getNewUserRefreshToken();
                if (this$newUserRefreshToken == null) {
                    if (other$newUserRefreshToken != null) {
                        return false;
                    }
                } else if (!this$newUserRefreshToken.equals(other$newUserRefreshToken)) {
                    return false;
                }

                Object this$newRootFolderToken = this.getNewRootFolderToken();
                Object other$newRootFolderToken = other.getNewRootFolderToken();
                if (this$newRootFolderToken == null) {
                    if (other$newRootFolderToken != null) {
                        return false;
                    }
                } else if (!this$newRootFolderToken.equals(other$newRootFolderToken)) {
                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                Object this$procStatus = this.getProcStatus();
                Object other$procStatus = other.getProcStatus();
                if (this$procStatus == null) {
                    if (other$procStatus != null) {
                        return false;
                    }
                } else if (!this$procStatus.equals(other$procStatus)) {
                    return false;
                }

                Object this$procMsg = this.getProcMsg();
                Object other$procMsg = other.getProcMsg();
                if (this$procMsg == null) {
                    if (other$procMsg != null) {
                        return false;
                    }
                } else if (!this$procMsg.equals(other$procMsg)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $oldUserId = this.getOldUserId();
        result = result * 59 + ($oldUserId == null ? 43 : $oldUserId.hashCode());
        Object $oldUserName = this.getOldUserName();
        result = result * 59 + ($oldUserName == null ? 43 : $oldUserName.hashCode());
        Object $oldUserEmail = this.getOldUserEmail();
        result = result * 59 + ($oldUserEmail == null ? 43 : $oldUserEmail.hashCode());
        Object $oldUserMobile = this.getOldUserMobile();
        result = result * 59 + ($oldUserMobile == null ? 43 : $oldUserMobile.hashCode());
        Object $oldUserOpenId = this.getOldUserOpenId();
        result = result * 59 + ($oldUserOpenId == null ? 43 : $oldUserOpenId.hashCode());
        Object $oldUserToken = this.getOldUserToken();
        result = result * 59 + ($oldUserToken == null ? 43 : $oldUserToken.hashCode());
        Object $oldUserRefreshToken = this.getOldUserRefreshToken();
        result = result * 59 + ($oldUserRefreshToken == null ? 43 : $oldUserRefreshToken.hashCode());
        Object $oldRootFolderToken = this.getOldRootFolderToken();
        result = result * 59 + ($oldRootFolderToken == null ? 43 : $oldRootFolderToken.hashCode());
        Object $newUserId = this.getNewUserId();
        result = result * 59 + ($newUserId == null ? 43 : $newUserId.hashCode());
        Object $newUserName = this.getNewUserName();
        result = result * 59 + ($newUserName == null ? 43 : $newUserName.hashCode());
        Object $newUserEmail = this.getNewUserEmail();
        result = result * 59 + ($newUserEmail == null ? 43 : $newUserEmail.hashCode());
        Object $newUserMobile = this.getNewUserMobile();
        result = result * 59 + ($newUserMobile == null ? 43 : $newUserMobile.hashCode());
        Object $newUserOpenId = this.getNewUserOpenId();
        result = result * 59 + ($newUserOpenId == null ? 43 : $newUserOpenId.hashCode());
        Object $newUserToken = this.getNewUserToken();
        result = result * 59 + ($newUserToken == null ? 43 : $newUserToken.hashCode());
        Object $newUserRefreshToken = this.getNewUserRefreshToken();
        result = result * 59 + ($newUserRefreshToken == null ? 43 : $newUserRefreshToken.hashCode());
        Object $newRootFolderToken = this.getNewRootFolderToken();
        result = result * 59 + ($newRootFolderToken == null ? 43 : $newRootFolderToken.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $procStatus = this.getProcStatus();
        result = result * 59 + ($procStatus == null ? 43 : $procStatus.hashCode());
        Object $procMsg = this.getProcMsg();
        result = result * 59 + ($procMsg == null ? 43 : $procMsg.hashCode());
        return result;
    }

    public abstract static class UserInfoBuilder<C extends UserInfo, B extends UserInfoBuilder<C, B>> extends BasicEntity.BasicEntityBuilder<C, B> {
        private String oldUserId;
        private String oldUserName;
        private String oldUserEmail;
        private String oldUserMobile;
        private String oldUserOpenId;
        private String oldUserToken;
        private String oldUserRefreshToken;
        private String oldRootFolderToken;
        private String newUserId;
        private String newUserName;
        private String newUserEmail;
        private String newUserMobile;
        private String newUserOpenId;
        private String newUserToken;
        private String newUserRefreshToken;
        private String newRootFolderToken;
        private String status;
        private String procStatus;
        private String procMsg;

        public UserInfoBuilder() {
        }

        protected abstract B self();

        public abstract C build();

        public B oldUserId(final String oldUserId) {
            this.oldUserId = oldUserId;
            return (B)this.self();
        }

        public B oldUserName(final String oldUserName) {
            this.oldUserName = oldUserName;
            return (B)this.self();
        }

        public B oldUserEmail(final String oldUserEmail) {
            this.oldUserEmail = oldUserEmail;
            return (B)this.self();
        }

        public B oldUserMobile(final String oldUserMobile) {
            this.oldUserMobile = oldUserMobile;
            return (B)this.self();
        }

        public B oldUserOpenId(final String oldUserOpenId) {
            this.oldUserOpenId = oldUserOpenId;
            return (B)this.self();
        }

        public B oldUserToken(final String oldUserToken) {
            this.oldUserToken = oldUserToken;
            return (B)this.self();
        }

        public B oldUserRefreshToken(final String oldUserRefreshToken) {
            this.oldUserRefreshToken = oldUserRefreshToken;
            return (B)this.self();
        }

        public B oldRootFolderToken(final String oldRootFolderToken) {
            this.oldRootFolderToken = oldRootFolderToken;
            return (B)this.self();
        }

        public B newUserId(final String newUserId) {
            this.newUserId = newUserId;
            return (B)this.self();
        }

        public B newUserName(final String newUserName) {
            this.newUserName = newUserName;
            return (B)this.self();
        }

        public B newUserEmail(final String newUserEmail) {
            this.newUserEmail = newUserEmail;
            return (B)this.self();
        }

        public B newUserMobile(final String newUserMobile) {
            this.newUserMobile = newUserMobile;
            return (B)this.self();
        }

        public B newUserOpenId(final String newUserOpenId) {
            this.newUserOpenId = newUserOpenId;
            return (B)this.self();
        }

        public B newUserToken(final String newUserToken) {
            this.newUserToken = newUserToken;
            return (B)this.self();
        }

        public B newUserRefreshToken(final String newUserRefreshToken) {
            this.newUserRefreshToken = newUserRefreshToken;
            return (B)this.self();
        }

        public B newRootFolderToken(final String newRootFolderToken) {
            this.newRootFolderToken = newRootFolderToken;
            return (B)this.self();
        }

        public B status(final String status) {
            this.status = status;
            return (B)this.self();
        }

        public B procStatus(final String procStatus) {
            this.procStatus = procStatus;
            return (B)this.self();
        }

        public B procMsg(final String procMsg) {
            this.procMsg = procMsg;
            return (B)this.self();
        }

        public String toString() {
            return "UserInfo.UserInfoBuilder(super=" + super.toString() + ", oldUserId=" + this.oldUserId + ", oldUserName=" + this.oldUserName + ", oldUserEmail=" + this.oldUserEmail + ", oldUserMobile=" + this.oldUserMobile + ", oldUserOpenId=" + this.oldUserOpenId + ", oldUserToken=" + this.oldUserToken + ", oldUserRefreshToken=" + this.oldUserRefreshToken + ", oldRootFolderToken=" + this.oldRootFolderToken + ", newUserId=" + this.newUserId + ", newUserName=" + this.newUserName + ", newUserEmail=" + this.newUserEmail + ", newUserMobile=" + this.newUserMobile + ", newUserOpenId=" + this.newUserOpenId + ", newUserToken=" + this.newUserToken + ", newUserRefreshToken=" + this.newUserRefreshToken + ", newRootFolderToken=" + this.newRootFolderToken + ", status=" + this.status + ", procStatus=" + this.procStatus + ", procMsg=" + this.procMsg + ")";
        }
    }

    private static final class UserInfoBuilderImpl extends UserInfoBuilder<UserInfo, UserInfoBuilderImpl> {
        private UserInfoBuilderImpl() {
        }

        protected UserInfoBuilderImpl self() {
            return this;
        }

        public UserInfo build() {
            return new UserInfo(this);
        }
    }
}
