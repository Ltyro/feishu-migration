package com.lark.data.dto.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QueryDocMemberResponseDTO {
    public List<DriveMemberDTO> members;

    public QueryDocMemberResponseDTO() {
    }

    public List<DriveMemberDTO> getMembers() {
        return this.members;
    }

    public void setMembers(final List<DriveMemberDTO> members) {
        this.members = members;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof QueryDocMemberResponseDTO)) {
            return false;
        } else {
            QueryDocMemberResponseDTO other = (QueryDocMemberResponseDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$members = this.getMembers();
                Object other$members = other.getMembers();
                if (this$members == null) {
                    if (other$members != null) {
                        return false;
                    }
                } else if (!this$members.equals(other$members)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof QueryDocMemberResponseDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $members = this.getMembers();
        result = result * 59 + ($members == null ? 43 : $members.hashCode());
        return result;
    }

    public String toString() {
        return "QueryDocMemberResponseDTO(members=" + this.getMembers() + ")";
    }
}
