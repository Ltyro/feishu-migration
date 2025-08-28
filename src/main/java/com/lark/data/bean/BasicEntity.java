package com.lark.data.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.util.Date;

public class BasicEntity implements Serializable {
    @TableId(
            type = IdType.AUTO
    )
    private Long id;
    @TableLogic
    @TableField(
            select = false
    )
    private short disable;
    @TableField(
            value = "create_date",
            fill = FieldFill.INSERT
    )
    private Date createDate;
    @TableField(
            value = "update_date",
            fill = FieldFill.UPDATE
    )
    private Date updateDate;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            BasicEntity that = (BasicEntity)o;
            return this.disable == that.disable;
        } else {
            return false;
        }
    }

    private static Date $default$createDate() {
        return new Date();
    }

    private static Date $default$updateDate() {
        return new Date();
    }

    protected BasicEntity(final BasicEntityBuilder<?, ?> b) {
        this.id = b.id;
        this.disable = b.disable;
        if (b.createDate$set) {
            this.createDate = b.createDate$value;
        } else {
            this.createDate = $default$createDate();
        }

        if (b.updateDate$set) {
            this.updateDate = b.updateDate$value;
        } else {
            this.updateDate = $default$updateDate();
        }

    }

    public static BasicEntityBuilder<?, ?> builder() {
        return new BasicEntityBuilderImpl();
    }

    public Long getId() {
        return this.id;
    }

    public short getDisable() {
        return this.disable;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setDisable(final short disable) {
        this.disable = disable;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    public String toString() {
        return "BasicEntity(id=" + this.getId() + ", disable=" + this.getDisable() + ", createDate=" + this.getCreateDate() + ", updateDate=" + this.getUpdateDate() + ")";
    }

    public BasicEntity() {
        this.createDate = $default$createDate();
        this.updateDate = $default$updateDate();
    }

    public BasicEntity(final Long id, final short disable, final Date createDate, final Date updateDate) {
        this.id = id;
        this.disable = disable;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public abstract static class BasicEntityBuilder<C extends BasicEntity, B extends BasicEntityBuilder<C, B>> {
        private Long id;
        private short disable;
        private boolean createDate$set;
        private Date createDate$value;
        private boolean updateDate$set;
        private Date updateDate$value;

        public BasicEntityBuilder() {
        }

        protected abstract B self();

        public abstract C build();

        public B id(final Long id) {
            this.id = id;
            return (B)this.self();
        }

        public B disable(final short disable) {
            this.disable = disable;
            return (B)this.self();
        }

        public B createDate(final Date createDate) {
            this.createDate$value = createDate;
            this.createDate$set = true;
            return (B)this.self();
        }

        public B updateDate(final Date updateDate) {
            this.updateDate$value = updateDate;
            this.updateDate$set = true;
            return (B)this.self();
        }

        public String toString() {
            return "BasicEntity.BasicEntityBuilder(id=" + this.id + ", disable=" + this.disable + ", createDate$value=" + this.createDate$value + ", updateDate$value=" + this.updateDate$value + ")";
        }
    }

    private static final class BasicEntityBuilderImpl extends BasicEntityBuilder<BasicEntity, BasicEntityBuilderImpl> {
        private BasicEntityBuilderImpl() {
        }

        protected BasicEntityBuilderImpl self() {
            return this;
        }

        public BasicEntity build() {
            return new BasicEntity(this);
        }
    }
}
