
package com.reforgedsrc.app.vue2demo.boot.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reforgedsrc.app.vue2demo.boot.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	List<User> findAll();
}