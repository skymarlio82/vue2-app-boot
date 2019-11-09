
package com.reforgedsrc.app.vue2demo.boot.domain.service;

import java.util.List;

import com.reforgedsrc.app.vue2demo.boot.data.dao.UserDao;
import com.reforgedsrc.app.vue2demo.boot.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {

    @Autowired
    private UserDao userDao = null;

    public User getUserByName(String userName) {
        return userDao.findByUsername(userName);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}