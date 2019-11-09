
package com.reforgedsrc.app.vue2demo.boot.web.model;

import java.util.Date;

public class UserDetail {

    private long userId = 0L;
    private String userName = null;
    private boolean enabled = false;
    private Date lastPasswordResetDate = null;
    private String roleName = null;

    public UserDetail() {

    }

    public UserDetail(long userId, String userName, boolean enabled, Date lastPasswordResetDate, String roleName) {
        this.userId = userId;
        this.userName = userName;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.roleName = roleName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}