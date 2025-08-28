package com.lark.data.dto.sheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class SheetDTO {
    private String sheetId;
    private Integer rowCount;

    public SheetDTO() {
    }

    public String getSheetId() {
        return this.sheetId;
    }

    public Integer getRowCount() {
        return this.rowCount;
    }

    public void setSheetId(final String sheetId) {
        this.sheetId = sheetId;
    }

    public void setRowCount(final Integer rowCount) {
        this.rowCount = rowCount;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SheetDTO)) {
            return false;
        } else {
            SheetDTO other = (SheetDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$rowCount = this.getRowCount();
                Object other$rowCount = other.getRowCount();
                if (this$rowCount == null) {
                    if (other$rowCount != null) {
                        return false;
                    }
                } else if (!this$rowCount.equals(other$rowCount)) {
                    return false;
                }

                Object this$sheetId = this.getSheetId();
                Object other$sheetId = other.getSheetId();
                if (this$sheetId == null) {
                    if (other$sheetId != null) {
                        return false;
                    }
                } else if (!this$sheetId.equals(other$sheetId)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SheetDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $rowCount = this.getRowCount();
        result = result * 59 + ($rowCount == null ? 43 : $rowCount.hashCode());
        Object $sheetId = this.getSheetId();
        result = result * 59 + ($sheetId == null ? 43 : $sheetId.hashCode());
        return result;
    }

    public String toString() {
        return "SheetDTO(sheetId=" + this.getSheetId() + ", rowCount=" + this.getRowCount() + ")";
    }
}
