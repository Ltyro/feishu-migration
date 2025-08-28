package com.lark.data.dto.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DriveMemberDTO {
    private String memberType;
    private String memberOpenId;
    private String memberUserId;
    private String perm;

    public DriveMemberDTO() {
    }

    public String getMemberType() {
        return this.memberType;
    }

    public String getMemberOpenId() {
        return this.memberOpenId;
    }

    public String getMemberUserId() {
        return this.memberUserId;
    }

    public String getPerm() {
        return this.perm;
    }

    public void setMemberType(final String memberType) {
        this.memberType = memberType;
    }

    public void setMemberOpenId(final String memberOpenId) {
        this.memberOpenId = memberOpenId;
    }

    public void setMemberUserId(final String memberUserId) {
        this.memberUserId = memberUserId;
    }

    public void setPerm(final String perm) {
        this.perm = perm;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DriveMemberDTO)) {
            return false;
        } else {
            DriveMemberDTO other = (DriveMemberDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$memberType = this.getMemberType();
                Object other$memberType = other.getMemberType();
                if (this$memberType == null) {
                    if (other$memberType != null) {
                        return false;
                    }
                } else if (!this$memberType.equals(other$memberType)) {
                    return false;
                }

                Object this$memberOpenId = this.getMemberOpenId();
                Object other$memberOpenId = other.getMemberOpenId();
                if (this$memberOpenId == null) {
                    if (other$memberOpenId != null) {
                        return false;
                    }
                } else if (!this$memberOpenId.equals(other$memberOpenId)) {
                    return false;
                }

                Object this$memberUserId = this.getMemberUserId();
                Object other$memberUserId = other.getMemberUserId();
                if (this$memberUserId == null) {
                    if (other$memberUserId != null) {
                        return false;
                    }
                } else if (!this$memberUserId.equals(other$memberUserId)) {
                    return false;
                }

                Object this$perm = this.getPerm();
                Object other$perm = other.getPerm();
                if (this$perm == null) {
                    if (other$perm != null) {
                        return false;
                    }
                } else if (!this$perm.equals(other$perm)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DriveMemberDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $memberType = this.getMemberType();
        result = result * 59 + ($memberType == null ? 43 : $memberType.hashCode());
        Object $memberOpenId = this.getMemberOpenId();
        result = result * 59 + ($memberOpenId == null ? 43 : $memberOpenId.hashCode());
        Object $memberUserId = this.getMemberUserId();
        result = result * 59 + ($memberUserId == null ? 43 : $memberUserId.hashCode());
        Object $perm = this.getPerm();
        result = result * 59 + ($perm == null ? 43 : $perm.hashCode());
        return result;
    }

    public String toString() {
        return "DriveMemberDTO(memberType=" + this.getMemberType() + ", memberOpenId=" + this.getMemberOpenId() + ", memberUserId=" + this.getMemberUserId() + ", perm=" + this.getPerm() + ")";
    }
}
