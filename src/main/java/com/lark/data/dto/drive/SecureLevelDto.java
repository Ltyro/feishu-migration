package com.lark.data.dto.drive;

import java.util.List;

public class SecureLevelDto {
    private String page_token;
    private boolean has_more;
    private List<SecureLevel> items;

    public String getPage_token() {
        return this.page_token;
    }

    public boolean isHas_more() {
        return this.has_more;
    }

    public List<SecureLevel> getItems() {
        return this.items;
    }

    public void setPage_token(final String page_token) {
        this.page_token = page_token;
    }

    public void setHas_more(final boolean has_more) {
        this.has_more = has_more;
    }

    public void setItems(final List<SecureLevel> items) {
        this.items = items;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SecureLevelDto)) {
            return false;
        } else {
            SecureLevelDto other = (SecureLevelDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isHas_more() != other.isHas_more()) {
                return false;
            } else {
                Object this$page_token = this.getPage_token();
                Object other$page_token = other.getPage_token();
                if (this$page_token == null) {
                    if (other$page_token != null) {
                        return false;
                    }
                } else if (!this$page_token.equals(other$page_token)) {
                    return false;
                }

                Object this$items = this.getItems();
                Object other$items = other.getItems();
                if (this$items == null) {
                    if (other$items != null) {
                        return false;
                    }
                } else if (!this$items.equals(other$items)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SecureLevelDto;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isHas_more() ? 79 : 97);
        Object $page_token = this.getPage_token();
        result = result * 59 + ($page_token == null ? 43 : $page_token.hashCode());
        Object $items = this.getItems();
        result = result * 59 + ($items == null ? 43 : $items.hashCode());
        return result;
    }

    public String toString() {
        return "SecureLevelDto(page_token=" + this.getPage_token() + ", has_more=" + this.isHas_more() + ", items=" + this.getItems() + ")";
    }

    public SecureLevelDto(final String page_token, final boolean has_more, final List<SecureLevel> items) {
        this.page_token = page_token;
        this.has_more = has_more;
        this.items = items;
    }

    public SecureLevelDto() {
    }
}
