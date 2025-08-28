package com.lark.data.dto.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class DocDTO {
    private String objToken;
    private String url;

    public DocDTO() {
    }

    public String getObjToken() {
        return this.objToken;
    }

    public String getUrl() {
        return this.url;
    }

    public void setObjToken(final String objToken) {
        this.objToken = objToken;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DocDTO)) {
            return false;
        } else {
            DocDTO other = (DocDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$objToken = this.getObjToken();
                Object other$objToken = other.getObjToken();
                if (this$objToken == null) {
                    if (other$objToken != null) {
                        return false;
                    }
                } else if (!this$objToken.equals(other$objToken)) {
                    return false;
                }

                Object this$url = this.getUrl();
                Object other$url = other.getUrl();
                if (this$url == null) {
                    if (other$url != null) {
                        return false;
                    }
                } else if (!this$url.equals(other$url)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DocDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $objToken = this.getObjToken();
        result = result * 59 + ($objToken == null ? 43 : $objToken.hashCode());
        Object $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        return result;
    }

    public String toString() {
        return "DocDTO(objToken=" + this.getObjToken() + ", url=" + this.getUrl() + ")";
    }
}
