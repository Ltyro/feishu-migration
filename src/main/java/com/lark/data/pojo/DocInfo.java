package com.lark.data.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lark.data.bean.BasicEntity;

@TableName("doc_info")
public class DocInfo extends BasicEntity {
    private String oldUserOpenId;
    private String oldToken;
    private String oldName;
    private String oldType;
    private String oldParentToken;
    private Integer oldLevel;
    private String newUserOpenId;
    private String newToken;
    private String newName;
    private String newType;
    private String procStatus;
    private String procMsg;
    private String oldExternalAccess;
    private String oldSecureLabel;
    private String status;

    protected DocInfo(final DocInfoBuilder<?, ?> b) {
        super(b);
        this.oldUserOpenId = b.oldUserOpenId;
        this.oldToken = b.oldToken;
        this.oldName = b.oldName;
        this.oldType = b.oldType;
        this.oldParentToken = b.oldParentToken;
        this.oldLevel = b.oldLevel;
        this.newUserOpenId = b.newUserOpenId;
        this.newToken = b.newToken;
        this.newName = b.newName;
        this.newType = b.newType;
        this.procStatus = b.procStatus;
        this.procMsg = b.procMsg;
        this.oldExternalAccess = b.oldExternalAccess;
        this.oldSecureLabel = b.oldSecureLabel;
        this.status = b.status;
    }

    public static DocInfoBuilder<?, ?> builder() {
        return new DocInfoBuilderImpl();
    }

    public String getOldUserOpenId() {
        return this.oldUserOpenId;
    }

    public String getOldToken() {
        return this.oldToken;
    }

    public String getOldName() {
        return this.oldName;
    }

    public String getOldType() {
        return this.oldType;
    }

    public String getOldParentToken() {
        return this.oldParentToken;
    }

    public Integer getOldLevel() {
        return this.oldLevel;
    }

    public String getNewUserOpenId() {
        return this.newUserOpenId;
    }

    public String getNewToken() {
        return this.newToken;
    }

    public String getNewName() {
        return this.newName;
    }

    public String getNewType() {
        return this.newType;
    }

    public String getProcStatus() {
        return this.procStatus;
    }

    public String getProcMsg() {
        return this.procMsg;
    }

    public String getOldExternalAccess() {
        return this.oldExternalAccess;
    }

    public String getOldSecureLabel() {
        return this.oldSecureLabel;
    }

    public String getStatus() {
        return this.status;
    }

    public void setOldUserOpenId(final String oldUserOpenId) {
        this.oldUserOpenId = oldUserOpenId;
    }

    public void setOldToken(final String oldToken) {
        this.oldToken = oldToken;
    }

    public void setOldName(final String oldName) {
        this.oldName = oldName;
    }

    public void setOldType(final String oldType) {
        this.oldType = oldType;
    }

    public void setOldParentToken(final String oldParentToken) {
        this.oldParentToken = oldParentToken;
    }

    public void setOldLevel(final Integer oldLevel) {
        this.oldLevel = oldLevel;
    }

    public void setNewUserOpenId(final String newUserOpenId) {
        this.newUserOpenId = newUserOpenId;
    }

    public void setNewToken(final String newToken) {
        this.newToken = newToken;
    }

    public void setNewName(final String newName) {
        this.newName = newName;
    }

    public void setNewType(final String newType) {
        this.newType = newType;
    }

    public void setProcStatus(final String procStatus) {
        this.procStatus = procStatus;
    }

    public void setProcMsg(final String procMsg) {
        this.procMsg = procMsg;
    }

    public void setOldExternalAccess(final String oldExternalAccess) {
        this.oldExternalAccess = oldExternalAccess;
    }

    public void setOldSecureLabel(final String oldSecureLabel) {
        this.oldSecureLabel = oldSecureLabel;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String toString() {
        return "DocInfo(oldUserOpenId=" + this.getOldUserOpenId() + ", oldToken=" + this.getOldToken() + ", oldName=" + this.getOldName() + ", oldType=" + this.getOldType() + ", oldParentToken=" + this.getOldParentToken() + ", oldLevel=" + this.getOldLevel() + ", newUserOpenId=" + this.getNewUserOpenId() + ", newToken=" + this.getNewToken() + ", newName=" + this.getNewName() + ", newType=" + this.getNewType() + ", procStatus=" + this.getProcStatus() + ", procMsg=" + this.getProcMsg() + ", oldExternalAccess=" + this.getOldExternalAccess() + ", oldSecureLabel=" + this.getOldSecureLabel() + ", status=" + this.getStatus() + ")";
    }

    public DocInfo() {
    }

    public DocInfo(final String oldUserOpenId, final String oldToken, final String oldName, final String oldType, final String oldParentToken, final Integer oldLevel, final String newUserOpenId, final String newToken, final String newName, final String newType, final String procStatus, final String procMsg, final String oldExternalAccess, final String oldSecureLabel, final String status) {
        this.oldUserOpenId = oldUserOpenId;
        this.oldToken = oldToken;
        this.oldName = oldName;
        this.oldType = oldType;
        this.oldParentToken = oldParentToken;
        this.oldLevel = oldLevel;
        this.newUserOpenId = newUserOpenId;
        this.newToken = newToken;
        this.newName = newName;
        this.newType = newType;
        this.procStatus = procStatus;
        this.procMsg = procMsg;
        this.oldExternalAccess = oldExternalAccess;
        this.oldSecureLabel = oldSecureLabel;
        this.status = status;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DocInfo)) {
            return false;
        } else {
            DocInfo other = (DocInfo)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$oldLevel = this.getOldLevel();
                Object other$oldLevel = other.getOldLevel();
                if (this$oldLevel == null) {
                    if (other$oldLevel != null) {
                        return false;
                    }
                } else if (!this$oldLevel.equals(other$oldLevel)) {
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

                Object this$oldToken = this.getOldToken();
                Object other$oldToken = other.getOldToken();
                if (this$oldToken == null) {
                    if (other$oldToken != null) {
                        return false;
                    }
                } else if (!this$oldToken.equals(other$oldToken)) {
                    return false;
                }

                Object this$oldName = this.getOldName();
                Object other$oldName = other.getOldName();
                if (this$oldName == null) {
                    if (other$oldName != null) {
                        return false;
                    }
                } else if (!this$oldName.equals(other$oldName)) {
                    return false;
                }

                Object this$oldType = this.getOldType();
                Object other$oldType = other.getOldType();
                if (this$oldType == null) {
                    if (other$oldType != null) {
                        return false;
                    }
                } else if (!this$oldType.equals(other$oldType)) {
                    return false;
                }

                Object this$oldParentToken = this.getOldParentToken();
                Object other$oldParentToken = other.getOldParentToken();
                if (this$oldParentToken == null) {
                    if (other$oldParentToken != null) {
                        return false;
                    }
                } else if (!this$oldParentToken.equals(other$oldParentToken)) {
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

                Object this$newToken = this.getNewToken();
                Object other$newToken = other.getNewToken();
                if (this$newToken == null) {
                    if (other$newToken != null) {
                        return false;
                    }
                } else if (!this$newToken.equals(other$newToken)) {
                    return false;
                }

                Object this$newName = this.getNewName();
                Object other$newName = other.getNewName();
                if (this$newName == null) {
                    if (other$newName != null) {
                        return false;
                    }
                } else if (!this$newName.equals(other$newName)) {
                    return false;
                }

                Object this$newType = this.getNewType();
                Object other$newType = other.getNewType();
                if (this$newType == null) {
                    if (other$newType != null) {
                        return false;
                    }
                } else if (!this$newType.equals(other$newType)) {
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

                Object this$oldExternalAccess = this.getOldExternalAccess();
                Object other$oldExternalAccess = other.getOldExternalAccess();
                if (this$oldExternalAccess == null) {
                    if (other$oldExternalAccess != null) {
                        return false;
                    }
                } else if (!this$oldExternalAccess.equals(other$oldExternalAccess)) {
                    return false;
                }

                Object this$oldSecureLabel = this.getOldSecureLabel();
                Object other$oldSecureLabel = other.getOldSecureLabel();
                if (this$oldSecureLabel == null) {
                    if (other$oldSecureLabel != null) {
                        return false;
                    }
                } else if (!this$oldSecureLabel.equals(other$oldSecureLabel)) {
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

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DocInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $oldLevel = this.getOldLevel();
        result = result * 59 + ($oldLevel == null ? 43 : $oldLevel.hashCode());
        Object $oldUserOpenId = this.getOldUserOpenId();
        result = result * 59 + ($oldUserOpenId == null ? 43 : $oldUserOpenId.hashCode());
        Object $oldToken = this.getOldToken();
        result = result * 59 + ($oldToken == null ? 43 : $oldToken.hashCode());
        Object $oldName = this.getOldName();
        result = result * 59 + ($oldName == null ? 43 : $oldName.hashCode());
        Object $oldType = this.getOldType();
        result = result * 59 + ($oldType == null ? 43 : $oldType.hashCode());
        Object $oldParentToken = this.getOldParentToken();
        result = result * 59 + ($oldParentToken == null ? 43 : $oldParentToken.hashCode());
        Object $newUserOpenId = this.getNewUserOpenId();
        result = result * 59 + ($newUserOpenId == null ? 43 : $newUserOpenId.hashCode());
        Object $newToken = this.getNewToken();
        result = result * 59 + ($newToken == null ? 43 : $newToken.hashCode());
        Object $newName = this.getNewName();
        result = result * 59 + ($newName == null ? 43 : $newName.hashCode());
        Object $newType = this.getNewType();
        result = result * 59 + ($newType == null ? 43 : $newType.hashCode());
        Object $procStatus = this.getProcStatus();
        result = result * 59 + ($procStatus == null ? 43 : $procStatus.hashCode());
        Object $procMsg = this.getProcMsg();
        result = result * 59 + ($procMsg == null ? 43 : $procMsg.hashCode());
        Object $oldExternalAccess = this.getOldExternalAccess();
        result = result * 59 + ($oldExternalAccess == null ? 43 : $oldExternalAccess.hashCode());
        Object $oldSecureLabel = this.getOldSecureLabel();
        result = result * 59 + ($oldSecureLabel == null ? 43 : $oldSecureLabel.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public abstract static class DocInfoBuilder<C extends DocInfo, B extends DocInfoBuilder<C, B>> extends BasicEntity.BasicEntityBuilder<C, B> {
        private String oldUserOpenId;
        private String oldToken;
        private String oldName;
        private String oldType;
        private String oldParentToken;
        private Integer oldLevel;
        private String newUserOpenId;
        private String newToken;
        private String newName;
        private String newType;
        private String procStatus;
        private String procMsg;
        private String oldExternalAccess;
        private String oldSecureLabel;
        private String status;

        public DocInfoBuilder() {
        }

        protected abstract B self();

        public abstract C build();

        public B oldUserOpenId(final String oldUserOpenId) {
            this.oldUserOpenId = oldUserOpenId;
            return (B)this.self();
        }

        public B oldToken(final String oldToken) {
            this.oldToken = oldToken;
            return (B)this.self();
        }

        public B oldName(final String oldName) {
            this.oldName = oldName;
            return (B)this.self();
        }

        public B oldType(final String oldType) {
            this.oldType = oldType;
            return (B)this.self();
        }

        public B oldParentToken(final String oldParentToken) {
            this.oldParentToken = oldParentToken;
            return (B)this.self();
        }

        public B oldLevel(final Integer oldLevel) {
            this.oldLevel = oldLevel;
            return (B)this.self();
        }

        public B newUserOpenId(final String newUserOpenId) {
            this.newUserOpenId = newUserOpenId;
            return (B)this.self();
        }

        public B newToken(final String newToken) {
            this.newToken = newToken;
            return (B)this.self();
        }

        public B newName(final String newName) {
            this.newName = newName;
            return (B)this.self();
        }

        public B newType(final String newType) {
            this.newType = newType;
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

        public B oldExternalAccess(final String oldExternalAccess) {
            this.oldExternalAccess = oldExternalAccess;
            return (B)this.self();
        }

        public B oldSecureLabel(final String oldSecureLabel) {
            this.oldSecureLabel = oldSecureLabel;
            return (B)this.self();
        }

        public B status(final String status) {
            this.status = status;
            return (B)this.self();
        }

        public String toString() {
            return "DocInfo.DocInfoBuilder(super=" + super.toString() + ", oldUserOpenId=" + this.oldUserOpenId + ", oldToken=" + this.oldToken + ", oldName=" + this.oldName + ", oldType=" + this.oldType + ", oldParentToken=" + this.oldParentToken + ", oldLevel=" + this.oldLevel + ", newUserOpenId=" + this.newUserOpenId + ", newToken=" + this.newToken + ", newName=" + this.newName + ", newType=" + this.newType + ", procStatus=" + this.procStatus + ", procMsg=" + this.procMsg + ", oldExternalAccess=" + this.oldExternalAccess + ", oldSecureLabel=" + this.oldSecureLabel + ", status=" + this.status + ")";
        }
    }

    private static final class DocInfoBuilderImpl extends DocInfoBuilder<DocInfo, DocInfoBuilderImpl> {
        private DocInfoBuilderImpl() {
        }

        protected DocInfoBuilderImpl self() {
            return this;
        }

        public DocInfo build() {
            return new DocInfo(this);
        }
    }
}
