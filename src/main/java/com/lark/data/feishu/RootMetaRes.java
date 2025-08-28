package com.lark.data.feishu;

public class RootMetaRes {
    private String id;
    private String token;
    private String user_id;

    public RootMetaRes() {
    }

    public String getId() {
        return this.id;
    }

    public String getToken() {
        return this.token;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setUser_id(final String user_id) {
        this.user_id = user_id;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RootMetaRes)) {
            return false;
        } else {
            RootMetaRes other = (RootMetaRes)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                Object this$token = this.getToken();
                Object other$token = other.getToken();
                if (this$token == null) {
                    if (other$token != null) {
                        return false;
                    }
                } else if (!this$token.equals(other$token)) {
                    return false;
                }

                Object this$user_id = this.getUser_id();
                Object other$user_id = other.getUser_id();
                if (this$user_id == null) {
                    if (other$user_id != null) {
                        return false;
                    }
                } else if (!this$user_id.equals(other$user_id)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RootMetaRes;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        Object $user_id = this.getUser_id();
        result = result * 59 + ($user_id == null ? 43 : $user_id.hashCode());
        return result;
    }

    public String toString() {
        return "RootMetaRes(id=" + this.getId() + ", token=" + this.getToken() + ", user_id=" + this.getUser_id() + ")";
    }
}
