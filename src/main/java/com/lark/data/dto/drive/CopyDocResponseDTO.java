package com.lark.data.dto.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class CopyDocResponseDTO {
    private String folderToken;
    private String revision;
    private String token;
    private String type;
    private String url;

    public CopyDocResponseDTO() {
    }

    public String getFolderToken() {
        return this.folderToken;
    }

    public String getRevision() {
        return this.revision;
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setFolderToken(final String folderToken) {
        this.folderToken = folderToken;
    }

    public void setRevision(final String revision) {
        this.revision = revision;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CopyDocResponseDTO)) {
            return false;
        } else {
            CopyDocResponseDTO other = (CopyDocResponseDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$folderToken = this.getFolderToken();
                Object other$folderToken = other.getFolderToken();
                if (this$folderToken == null) {
                    if (other$folderToken != null) {
                        return false;
                    }
                } else if (!this$folderToken.equals(other$folderToken)) {
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

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    if (other$type != null) {
                        return false;
                    }
                } else if (!this$type.equals(other$type)) {
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
        return other instanceof CopyDocResponseDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $folderToken = this.getFolderToken();
        result = result * 59 + ($folderToken == null ? 43 : $folderToken.hashCode());
        Object $revision = this.getRevision();
        result = result * 59 + ($revision == null ? 43 : $revision.hashCode());
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Object $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        return result;
    }

    public String toString() {
        return "CopyDocResponseDTO(folderToken=" + this.getFolderToken() + ", revision=" + this.getRevision() + ", token=" + this.getToken() + ", type=" + this.getType() + ", url=" + this.getUrl() + ")";
    }
}
