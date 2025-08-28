package com.lark.data.dto.contact;

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
public class UserDTO {
    private String userId;
    private String name;
    private String openId;

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public String getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOpenId(final String openId) {
        this.openId = openId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof UserDTO)) {
            return false;
        } else {
            UserDTO other = (UserDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$userId = this.getUserId();
                Object other$userId = other.getUserId();
                if (this$userId == null) {
                    if (other$userId != null) {
                        return false;
                    }
                } else if (!this$userId.equals(other$userId)) {
                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                Object this$openId = this.getOpenId();
                Object other$openId = other.getOpenId();
                if (this$openId == null) {
                    if (other$openId != null) {
                        return false;
                    }
                } else if (!this$openId.equals(other$openId)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $openId = this.getOpenId();
        result = result * 59 + ($openId == null ? 43 : $openId.hashCode());
        return result;
    }

    public String toString() {
        return "UserDTO(userId=" + this.getUserId() + ", name=" + this.getName() + ", openId=" + this.getOpenId() + ")";
    }

    public UserDTO() {
    }

    public UserDTO(final String userId, final String name, final String openId) {
        this.userId = userId;
        this.name = name;
        this.openId = openId;
    }

    public static class UserDTOBuilder {
        private String userId;
        private String name;
        private String openId;

        UserDTOBuilder() {
        }

        public UserDTOBuilder userId(final String userId) {
            this.userId = userId;
            return this;
        }

        public UserDTOBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public UserDTOBuilder openId(final String openId) {
            this.openId = openId;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this.userId, this.name, this.openId);
        }

        public String toString() {
            return "UserDTO.UserDTOBuilder(userId=" + this.userId + ", name=" + this.name + ", openId=" + this.openId + ")";
        }
    }
}
