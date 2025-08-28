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
public class FinishUploadResponseDTO {
    private String fileToken;

    public FinishUploadResponseDTO() {
    }

    public String getFileToken() {
        return this.fileToken;
    }

    public void setFileToken(final String fileToken) {
        this.fileToken = fileToken;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FinishUploadResponseDTO)) {
            return false;
        } else {
            FinishUploadResponseDTO other = (FinishUploadResponseDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$fileToken = this.getFileToken();
                Object other$fileToken = other.getFileToken();
                if (this$fileToken == null) {
                    if (other$fileToken != null) {
                        return false;
                    }
                } else if (!this$fileToken.equals(other$fileToken)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FinishUploadResponseDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $fileToken = this.getFileToken();
        result = result * 59 + ($fileToken == null ? 43 : $fileToken.hashCode());
        return result;
    }

    public String toString() {
        return "FinishUploadResponseDTO(fileToken=" + this.getFileToken() + ")";
    }
}
