
package com.wiz.app.vue2.boot.security.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.wiz.app.vue2.boot.security.entity.Authority;
import com.wiz.app.vue2.boot.security.entity.User;

public final class JwtUserFactory {

	private JwtUserFactory() {
		
	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(),
			user.getUsername(),
			user.getPassword(),
			mapToGrantedAuthorities(user.getAuthorities()),
			user.getEnabled(),
			user.getLastPasswordResetDate());
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
			.collect(Collectors.toList());
	}
}