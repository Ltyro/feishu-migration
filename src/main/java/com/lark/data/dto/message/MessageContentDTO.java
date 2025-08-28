package com.lark.data.dto.message;

public class MessageContentDTO {
    private String text;

    MessageContentDTO(final String text) {
        this.text = text;
    }

    public static MessageContentDTOBuilder builder() {
        return new MessageContentDTOBuilder();
    }

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MessageContentDTO)) {
            return false;
        } else {
            MessageContentDTO other = (MessageContentDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$text = this.getText();
                Object other$text = other.getText();
                if (this$text == null) {
                    if (other$text != null) {
                        return false;
                    }
                } else if (!this$text.equals(other$text)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MessageContentDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        return result;
    }

    public String toString() {
        return "MessageContentDTO(text=" + this.getText() + ")";
    }

    public static class MessageContentDTOBuilder {
        private String text;

        MessageContentDTOBuilder() {
        }

        public MessageContentDTOBuilder text(final String text) {
            this.text = text;
            return this;
        }

        public MessageContentDTO build() {
            return new MessageContentDTO(this.text);
        }

        public String toString() {
            return "MessageContentDTO.MessageContentDTOBuilder(text=" + this.text + ")";
        }
    }
}
