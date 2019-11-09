
package com.reforgedsrc.app.vue2demo.boot.data.dao;

import com.reforgedsrc.app.vue2demo.boot.data.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    User findByUsername(String username);
    List<User> findAll();
}