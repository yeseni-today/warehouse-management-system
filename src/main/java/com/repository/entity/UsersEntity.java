package com.repository.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Entity
@Table(name = "item_users")
public class UsersEntity {
    @Size(max = 5,message = "用户标识符为5位")
    private String usersId;
    @Size(max = 15,message = "密码应在10-15位之间")
    private String usersPassword;
    private String usersName;
    private String usersIdentity;
    private String usersPhone;
    private String usersSex;

    @Id
    @Column(name = "users_ID")
    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    @Basic
    @Column(name = "users_password")
    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        this.usersPassword = usersPassword;
    }

    @Basic
    @Column(name = "users_name")
    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    @Basic
    @Column(name = "users_identity")
    public String getUsersIdentity() {
        return usersIdentity;
    }

    public void setUsersIdentity(String usersIdentity) {
        this.usersIdentity = usersIdentity;
    }

    @Basic
    @Column(name = "users_phone")
    public String getUsersPhone() {
        return usersPhone;
    }

    public void setUsersPhone(String usersPhone) {
        this.usersPhone = usersPhone;
    }

    @Basic
    @Column(name = "users_sex")
    public String getUsersSex() {
        return usersSex;
    }

    public void setUsersSex(String usersSex) {
        this.usersSex = usersSex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity entity = (UsersEntity) o;

        if (usersId != null ? !usersId.equals(entity.usersId) : entity.usersId != null)
            return false;
        if (usersPassword != null ? !usersPassword.equals(entity.usersPassword) : entity.usersPassword != null)
            return false;
        if (usersName != null ? !usersName.equals(entity.usersName) : entity.usersName != null)
            return false;
        if (usersIdentity != null ? !usersIdentity.equals(entity.usersIdentity) : entity.usersIdentity != null)
            return false;
        if (usersPhone != null ? !usersPhone.equals(entity.usersPhone) : entity.usersPhone != null)
            return false;
        if (usersSex != null ? !usersSex.equals(entity.usersSex) : entity.usersSex != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usersId != null ? usersId.hashCode() : 0;
        result = 31 * result + (usersPassword != null ? usersPassword.hashCode() : 0);
        result = 31 * result + (usersName != null ? usersName.hashCode() : 0);
        result = 31 * result + (usersIdentity != null ? usersIdentity.hashCode() : 0);
        result = 31 * result + (usersPhone != null ? usersPhone.hashCode() : 0);
        result = 31 * result + (usersSex != null ? usersSex.hashCode() : 0);
        return result;
    }
}
