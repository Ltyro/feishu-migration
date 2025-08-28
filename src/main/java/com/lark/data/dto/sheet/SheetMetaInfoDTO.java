package com.lark.data.dto.sheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SheetMetaInfoDTO {
    @JsonProperty
    private List<SheetDTO> sheets;
    @JsonProperty("spreadsheetToken")
    private String spreadSheetToken;

    public SheetMetaInfoDTO() {
    }

    public List<SheetDTO> getSheets() {
        return this.sheets;
    }

    public String getSpreadSheetToken() {
        return this.spreadSheetToken;
    }

    @JsonProperty
    public void setSheets(final List<SheetDTO> sheets) {
        this.sheets = sheets;
    }

    @JsonProperty("spreadsheetToken")
    public void setSpreadSheetToken(final String spreadSheetToken) {
        this.spreadSheetToken = spreadSheetToken;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SheetMetaInfoDTO)) {
            return false;
        } else {
            SheetMetaInfoDTO other = (SheetMetaInfoDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$sheets = this.getSheets();
                Object other$sheets = other.getSheets();
                if (this$sheets == null) {
                    if (other$sheets != null) {
                        return false;
                    }
                } else if (!this$sheets.equals(other$sheets)) {
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
        return other instanceof SheetMetaInfoDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $sheets = this.getSheets();
        result = result * 59 + ($sheets == null ? 43 : $sheets.hashCode());
        Object $spreadSheetToken = this.getSpreadSheetToken();
        result = result * 59 + ($spreadSheetToken == null ? 43 : $spreadSheetToken.hashCode());
        return result;
    }

    public String toString() {
        return "SheetMetaInfoDTO(sheets=" + this.getSheets() + ", spreadSheetToken=" + this.getSpreadSheetToken() + ")";
    }
}
