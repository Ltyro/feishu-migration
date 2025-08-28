package com.lark.exception;

public class CustomException extends RuntimeException {
    private Exception originException;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Exception originException) {
        this(message);
        this.originException = originException;
    }

    public Exception getOriginException() {
        return this.originException;
    }

    public void setOriginException(final Exception originException) {
        this.originException = originException;
    }

    public String toString() {
        return "CustomException(originException=" + this.getOriginException() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CustomException)) {
            return false;
        } else {
            CustomException other = (CustomException)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$originException = this.getOriginException();
                Object other$originException = other.getOriginException();
                if (this$originException == null) {
                    if (other$originException != null) {
                        return false;
                    }
                } else if (!this$originException.equals(other$originException)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CustomException;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $originException = this.getOriginException();
        result = result * 59 + ($originException == null ? 43 : $originException.hashCode());
        return result;
    }
}
