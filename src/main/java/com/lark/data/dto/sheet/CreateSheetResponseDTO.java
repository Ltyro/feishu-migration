package com.lark.data.dto.sheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateSheetResponseDTO {
    private String title;
    private String folderToken;
    private String url;
    @JsonProperty("spreadsheet_token")
    private String spreadSheetToken;

    public CreateSheetResponseDTO() {
    }

    public String getTitle() {
        return this.title;
    }

    public String getFolderToken() {
        return this.folderToken;
    }

    public String getUrl() {
        return this.url;
    }

    public String getSpreadSheetToken() {
        return this.spreadSheetToken;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setFolderToken(final String folderToken) {
        this.folderToken = folderToken;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @JsonProperty("spreadsheet_token")
    public void setSpreadSheetToken(final String spreadSheetToken) {
        this.spreadSheetToken = spreadSheetToken;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CreateSheetResponseDTO)) {
            return false;
        } else {
            CreateSheetResponseDTO other = (CreateSheetResponseDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$title = this.getTitle();
                Object other$title = other.getTitle();
                if (this$title == null) {
                    if (other$title != null) {
                        return false;
                    }
                } else if (!this$title.equals(other$title)) {
                    return false;
                }

                Object this$folderToken = this.getFolderToken();
                Object other$folderToken = other.getFolderToken();
                if (this$folderToken == null) {
                    if (other$folderToken != null) {
                        return false;
                    }
                } else if (!this$folderToken.equals(other$folderToken)) {
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

                Object this$spreadSheetToken = this.getSpreadSheetToken();
                Object other$spreadSheetToken = other.getSpreadSheetToken();
                if (this$spreadSheetToken == null) {
                    if (other$spreadSheetToken != null) {
                        return false;
                    }
                } else if (!this$spreadSheetToken.equals(other$spreadSheetToken)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateSheetResponseDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        Object $folderToken = this.getFolderToken();
        result = result * 59 + ($folderToken == null ? 43 : $folderToken.hashCode());
        Object $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        Object $spreadSheetToken = this.getSpreadSheetToken();
        result = result * 59 + ($spreadSheetToken == null ? 43 : $spreadSheetToken.hashCode());
        return result;
    }

    public String toString() {
        return "CreateSheetResponseDTO(title=" + this.getTitle() + ", folderToken=" + this.getFolderToken() + ", url=" + this.getUrl() + ", spreadSheetToken=" + this.getSpreadSheetToken() + ")";
    }
}
