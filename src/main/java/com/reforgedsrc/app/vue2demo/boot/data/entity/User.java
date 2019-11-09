
package com.reforgedsrc.app.vue2demo.boot.data.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class User {

    private Long id = 0L;

    private String username = null;

    private String password = null;

    private Boolean enabled = false;

    private Date lastPasswordResetDate = null;

    private List<Authority> authorities = null;

    private String menu = null;

    public User() {

    }
}