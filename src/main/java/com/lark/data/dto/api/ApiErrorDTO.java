package com.lark.data.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(Include.NON_NULL)
public class ApiErrorDTO {
    private Integer code;
    private String msg;
    private Map<String, Object> error;

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Map<String, Object> getError() {
        return this.error;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setError(final Map<String, Object> error) {
        this.error = error;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApiErrorDTO)) {
            return false;
        } else {
            ApiErrorDTO other = (ApiErrorDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$error = this.getError();
                Object other$error = other.getError();
                if (this$error == null) {
                    if (other$error != null) {
                        return false;
                    }
                } else if (!this$error.equals(other$error)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ApiErrorDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $error = this.getError();
        result = result * 59 + ($error == null ? 43 : $error.hashCode());
        return result;
    }

    public String toString() {
        return "ApiErrorDTO(code=" + this.getCode() + ", msg=" + this.getMsg() + ", error=" + this.getError() + ")";
    }

    public ApiErrorDTO() {
    }

    public ApiErrorDTO(final Integer code, final String msg, final Map<String, Object> error) {
        this.code = code;
        this.msg = msg;
        this.error = error;
    }
}
