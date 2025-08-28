package com.lark.data.dto.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class SendMessageResponse {
    @JsonProperty("StatusCode")
    private Integer StatusCode;
    @JsonProperty("StatusMessage")
    private String StatusMessage;

    public SendMessageResponse() {
    }

    public Integer getStatusCode() {
        return this.StatusCode;
    }

    public String getStatusMessage() {
        return this.StatusMessage;
    }

    @JsonProperty("StatusCode")
    public void setStatusCode(final Integer StatusCode) {
        this.StatusCode = StatusCode;
    }

    @JsonProperty("StatusMessage")
    public void setStatusMessage(final String StatusMessage) {
        this.StatusMessage = StatusMessage;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SendMessageResponse)) {
            return false;
        } else {
            SendMessageResponse other = (SendMessageResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$StatusCode = this.getStatusCode();
                Object other$StatusCode = other.getStatusCode();
                if (this$StatusCode == null) {
                    if (other$StatusCode != null) {
                        return false;
                    }
                } else if (!this$StatusCode.equals(other$StatusCode)) {
                    return false;
                }

                Object this$StatusMessage = this.getStatusMessage();
                Object other$StatusMessage = other.getStatusMessage();
                if (this$StatusMessage == null) {
                    if (other$StatusMessage != null) {
                        return false;
                    }
                } else if (!this$StatusMessage.equals(other$StatusMessage)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SendMessageResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $StatusCode = this.getStatusCode();
        result = result * 59 + ($StatusCode == null ? 43 : $StatusCode.hashCode());
        Object $StatusMessage = this.getStatusMessage();
        result = result * 59 + ($StatusMessage == null ? 43 : $StatusMessage.hashCode());
        return result;
    }

    public String toString() {
        return "SendMessageResponse(StatusCode=" + this.getStatusCode() + ", StatusMessage=" + this.getStatusMessage() + ")";
    }
}
