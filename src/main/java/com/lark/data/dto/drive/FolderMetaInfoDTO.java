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
public class FolderMetaInfoDTO {
    private String token;
    private String id;
    private String userId;

    public FolderMetaInfoDTO() {
    }

    public String getToken() {
        return this.token;
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FolderMetaInfoDTO)) {
            return false;
        } else {
            FolderMetaInfoDTO other = (FolderMetaInfoDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$token = this.getToken();
                Object other$token = other.getToken();
                if (this$token == null) {
                    if (other$token != null) {
                        return false;
                    }
                } else if (!this$token.equals(other$token)) {
                    return false;
                }

                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
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

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FolderMetaInfoDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        return result;
    }

    public String toString() {
        return "FolderMetaInfoDTO(token=" + this.getToken() + ", id=" + this.getId() + ", userId=" + this.getUserId() + ")";
    }
}
