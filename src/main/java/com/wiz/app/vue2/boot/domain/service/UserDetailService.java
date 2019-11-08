
package com.wiz.app.vue2.boot.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiz.app.vue2.boot.security.entity.User;
import com.wiz.app.vue2.boot.security.repository.UserRepository;

@Service
public class UserDetailService {

	@Autowired
	private UserRepository userRepository = null;

	public User getUserByName(String userName) {
		return userRepository.findByUsername(userName);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}