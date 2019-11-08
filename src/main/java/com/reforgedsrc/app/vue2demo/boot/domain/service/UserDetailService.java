
package com.reforgedsrc.app.vue2demo.boot.domain.service;

import java.util.List;

import com.reforgedsrc.app.vue2demo.boot.security.entity.User;
import com.reforgedsrc.app.vue2demo.boot.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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