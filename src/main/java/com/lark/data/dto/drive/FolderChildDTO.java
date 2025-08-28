package com.lark.data.dto.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class FolderChildDTO {
    private String token;
    private String name;
    private String type;
    private String shortcut_token;
    private boolean is_shortcut;

    public FolderChildDTO() {
    }

    public String getToken() {
        return this.token;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getShortcut_token() {
        return this.shortcut_token;
    }

    public boolean is_shortcut() {
        return this.is_shortcut;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setShortcut_token(final String shortcut_token) {
        this.shortcut_token = shortcut_token;
    }

    public void set_shortcut(final boolean is_shortcut) {
        this.is_shortcut = is_shortcut;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FolderChildDTO)) {
            return false;
        } else {
            FolderChildDTO other = (FolderChildDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.is_shortcut() != other.is_shortcut()) {
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

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    if (other$type != null) {
                        return false;
                    }
                } else if (!this$type.equals(other$type)) {
                    return false;
                }

                Object this$shortcut_token = this.getShortcut_token();
                Object other$shortcut_token = other.getShortcut_token();
                if (this$shortcut_token == null) {
                    if (other$shortcut_token != null) {
                        return false;
                    }
                } else if (!this$shortcut_token.equals(other$shortcut_token)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FolderChildDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.is_shortcut() ? 79 : 97);
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Object $shortcut_token = this.getShortcut_token();
        result = result * 59 + ($shortcut_token == null ? 43 : $shortcut_token.hashCode());
        return result;
    }

    public String toString() {
        return "FolderChildDTO(token=" + this.getToken() + ", name=" + this.getName() + ", type=" + this.getType() + ", shortcut_token=" + this.getShortcut_token() + ", is_shortcut=" + this.is_shortcut() + ")";
    }
}
