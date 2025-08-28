package com.lark.data.dto.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class CreateFolderResponseDTO {
    private String url;
    private String revision;
    private String token;

    public CreateFolderResponseDTO() {
    }

    public String getUrl() {
        return this.url;
    }

    public String getRevision() {
        return this.revision;
    }

    public String getToken() {
        return this.token;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setRevision(final String revision) {
        this.revision = revision;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CreateFolderResponseDTO)) {
            return false;
        } else {
            CreateFolderResponseDTO other = (CreateFolderResponseDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$url = this.getUrl();
                Object other$url = other.getUrl();
                if (this$url == null) {
                    if (other$url != null) {
                        return false;
                    }
                } else if (!this$url.equals(other$url)) {
                    return false;
                }

                Object this$revision = this.getRevision();
                Object other$revision = other.getRevision();
                if (this$revision == null) {
                    if (other$revision != null) {
                        return false;
                    }
                } else if (!this$revision.equals(other$revision)) {
                    return false;
                }

                Object this$token = this.getToken();
                Object other$token = other.getToken();
                if (this$token == null) {
                    if (other$token != null) {
                        return false;
                    }
                } else if (!this$token.equals(other$token)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateFolderResponseDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        Object $revision = this.getRevision();
        result = result * 59 + ($revision == null ? 43 : $revision.hashCode());
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        return result;
    }

    public String toString() {
        return "CreateFolderResponseDTO(url=" + this.getUrl() + ", revision=" + this.getRevision() + ", token=" + this.getToken() + ")";
    }
}
