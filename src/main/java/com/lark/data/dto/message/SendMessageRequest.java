package com.lark.data.dto.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendMessageRequest {
    private String receiveId;
    private String content;
    private String msgType;

    private static String $default$msgType() {
        return "text";
    }

    SendMessageRequest(final String receiveId, final String content, final String msgType) {
        this.receiveId = receiveId;
        this.content = content;
        this.msgType = msgType;
    }

    public static SendMessageRequestBuilder builder() {
        return new SendMessageRequestBuilder();
    }

    public String getReceiveId() {
        return this.receiveId;
    }

    public String getContent() {
        return this.content;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public void setReceiveId(final String receiveId) {
        this.receiveId = receiveId;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setMsgType(final String msgType) {
        this.msgType = msgType;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SendMessageRequest)) {
            return false;
        } else {
            SendMessageRequest other = (SendMessageRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$receiveId = this.getReceiveId();
                Object other$receiveId = other.getReceiveId();
                if (this$receiveId == null) {
                    if (other$receiveId != null) {
                        return false;
                    }
                } else if (!this$receiveId.equals(other$receiveId)) {
                    return false;
                }

                Object this$content = this.getContent();
                Object other$content = other.getContent();
                if (this$content == null) {
                    if (other$content != null) {
                        return false;
                    }
                } else if (!this$content.equals(other$content)) {
                    return false;
                }

                Object this$msgType = this.getMsgType();
                Object other$msgType = other.getMsgType();
                if (this$msgType == null) {
                    if (other$msgType != null) {
                        return false;
                    }
                } else if (!this$msgType.equals(other$msgType)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SendMessageRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $receiveId = this.getReceiveId();
        result = result * 59 + ($receiveId == null ? 43 : $receiveId.hashCode());
        Object $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        Object $msgType = this.getMsgType();
        result = result * 59 + ($msgType == null ? 43 : $msgType.hashCode());
        return result;
    }

    public String toString() {
        return "SendMessageRequest(receiveId=" + this.getReceiveId() + ", content=" + this.getContent() + ", msgType=" + this.getMsgType() + ")";
    }

    public static class SendMessageRequestBuilder {
        private String receiveId;
        private String content;
        private boolean msgType$set;
        private String msgType$value;

        SendMessageRequestBuilder() {
        }

        public SendMessageRequestBuilder receiveId(final String receiveId) {
            this.receiveId = receiveId;
            return this;
        }

        public SendMessageRequestBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public SendMessageRequestBuilder msgType(final String msgType) {
            this.msgType$value = msgType;
            this.msgType$set = true;
            return this;
        }

        public SendMessageRequest build() {
            String msgType$value = this.msgType$value;
            if (!this.msgType$set) {
                msgType$value = SendMessageRequest.$default$msgType();
            }

            return new SendMessageRequest(this.receiveId, this.content, msgType$value);
        }

        public String toString() {
            return "SendMessageRequest.SendMessageRequestBuilder(receiveId=" + this.receiveId + ", content=" + this.content + ", msgType$value=" + this.msgType$value + ")";
        }
    }
}
