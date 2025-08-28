package com.lark.data.bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaginationResponse<T> extends ResponseEntity {
    private boolean hasMore;
    private String pageToken;
    private List<T> items;

    public PaginationResponse() {
    }

    public boolean isHasMore() {
        return this.hasMore;
    }

    public String getPageToken() {
        return this.pageToken;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setHasMore(final boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setPageToken(final String pageToken) {
        this.pageToken = pageToken;
    }

    public void setItems(final List<T> items) {
        this.items = items;
    }

    public String toString() {
        return "PaginationResponse(hasMore=" + this.isHasMore() + ", pageToken=" + this.getPageToken() + ", items=" + this.getItems() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PaginationResponse)) {
            return false;
        } else {
            PaginationResponse<?> other = (PaginationResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else if (this.isHasMore() != other.isHasMore()) {
                return false;
            } else {
                Object this$pageToken = this.getPageToken();
                Object other$pageToken = other.getPageToken();
                if (this$pageToken == null) {
                    if (other$pageToken != null) {
                        return false;
                    }
                } else if (!this$pageToken.equals(other$pageToken)) {
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
        return other instanceof PaginationResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + (this.isHasMore() ? 79 : 97);
        Object $pageToken = this.getPageToken();
        result = result * 59 + ($pageToken == null ? 43 : $pageToken.hashCode());
        Object $items = this.getItems();
        result = result * 59 + ($items == null ? 43 : $items.hashCode());
        return result;
    }
}
