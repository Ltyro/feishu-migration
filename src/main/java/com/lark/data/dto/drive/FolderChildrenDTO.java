package com.lark.data.dto.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class FolderChildrenDTO {
    Map<String, FolderChildDTO> children;
    private String parentToken;

    public FolderChildrenDTO() {
    }

    public Map<String, FolderChildDTO> getChildren() {
        return this.children;
    }

    public String getParentToken() {
        return this.parentToken;
    }

    public void setChildren(final Map<String, FolderChildDTO> children) {
        this.children = children;
    }

    public void setParentToken(final String parentToken) {
        this.parentToken = parentToken;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FolderChildrenDTO)) {
            return false;
        } else {
            FolderChildrenDTO other = (FolderChildrenDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$children = this.getChildren();
                Object other$children = other.getChildren();
                if (this$children == null) {
                    if (other$children != null) {
                        return false;
                    }
                } else if (!this$children.equals(other$children)) {
                    return false;
                }

                Object this$parentToken = this.getParentToken();
                Object other$parentToken = other.getParentToken();
                if (this$parentToken == null) {
                    if (other$parentToken != null) {
                        return false;
                    }
                } else if (!this$parentToken.equals(other$parentToken)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FolderChildrenDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $children = this.getChildren();
        result = result * 59 + ($children == null ? 43 : $children.hashCode());
        Object $parentToken = this.getParentToken();
        result = result * 59 + ($parentToken == null ? 43 : $parentToken.hashCode());
        return result;
    }

    public String toString() {
        return "FolderChildrenDTO(children=" + this.getChildren() + ", parentToken=" + this.getParentToken() + ")";
    }
}
