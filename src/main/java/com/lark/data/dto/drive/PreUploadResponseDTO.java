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
public class PreUploadResponseDTO {
    private String uploadId;
    private String blockSize;
    private Integer blockNum;

    public PreUploadResponseDTO() {
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public String getBlockSize() {
        return this.blockSize;
    }

    public Integer getBlockNum() {
        return this.blockNum;
    }

    public void setUploadId(final String uploadId) {
        this.uploadId = uploadId;
    }

    public void setBlockSize(final String blockSize) {
        this.blockSize = blockSize;
    }

    public void setBlockNum(final Integer blockNum) {
        this.blockNum = blockNum;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PreUploadResponseDTO)) {
            return false;
        } else {
            PreUploadResponseDTO other = (PreUploadResponseDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$blockNum = this.getBlockNum();
                Object other$blockNum = other.getBlockNum();
                if (this$blockNum == null) {
                    if (other$blockNum != null) {
                        return false;
                    }
                } else if (!this$blockNum.equals(other$blockNum)) {
                    return false;
                }

                Object this$uploadId = this.getUploadId();
                Object other$uploadId = other.getUploadId();
                if (this$uploadId == null) {
                    if (other$uploadId != null) {
                        return false;
                    }
                } else if (!this$uploadId.equals(other$uploadId)) {
                    return false;
                }

                Object this$blockSize = this.getBlockSize();
                Object other$blockSize = other.getBlockSize();
                if (this$blockSize == null) {
                    if (other$blockSize != null) {
                        return false;
                    }
                } else if (!this$blockSize.equals(other$blockSize)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PreUploadResponseDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $blockNum = this.getBlockNum();
        result = result * 59 + ($blockNum == null ? 43 : $blockNum.hashCode());
        Object $uploadId = this.getUploadId();
        result = result * 59 + ($uploadId == null ? 43 : $uploadId.hashCode());
        Object $blockSize = this.getBlockSize();
        result = result * 59 + ($blockSize == null ? 43 : $blockSize.hashCode());
        return result;
    }

    public String toString() {
        return "PreUploadResponseDTO(uploadId=" + this.getUploadId() + ", blockSize=" + this.getBlockSize() + ", blockNum=" + this.getBlockNum() + ")";
    }
}
