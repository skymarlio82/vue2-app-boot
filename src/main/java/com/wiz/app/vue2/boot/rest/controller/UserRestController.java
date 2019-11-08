
package com.wiz.app.vue2.boot.rest.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wiz.app.vue2.boot.constant.WebConstant;
import com.wiz.app.vue2.boot.domain.service.UserDetailService;
import com.wiz.app.vue2.boot.rest.model.UserDetail;
import com.wiz.app.vue2.boot.rest.model.UserProfile;
import com.wiz.app.vue2.boot.security.entity.User;
import com.wiz.app.vue2.boot.security.model.JwtTokenUtil;

@RestController
public class UserRestController {

	private String tokenHeader = WebConstant.JWT_HTTP_HEAD_AUTHOR;

	@Autowired
	private JwtTokenUtil jwtTokenUtil = null;

	@Autowired
	private UserDetailService userDetailService = null;

	@RequestMapping(value="/getInfo", method=RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public UserProfile getUserInformation(HttpServletRequest request) {
		// skip 'Bearer ' in header's attribute - Authorization
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = userDetailService.getUserByName(username);
		UserProfile userDetail = new UserProfile(user.getId().toString(),
			user.getUsername(),
			user.getAuthorities().get(0).getName().toString(),
			Arrays.asList(user.getMenu().split(",")));
		return userDetail;
	}

	@RequestMapping(value="/getallusers", method=RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<UserDetail> fetchAllUsers(HttpSession session) {
		Integer val = (Integer)session.getAttribute("kAdd");
		System.out.println("kAdd.val = " + val);
		if (val != null) {
			session.setAttribute("kAdd", val + 1);
		} else {
			session.setAttribute("kAdd", 0);
		}
		List<User> users = userDetailService.getAllUsers();
		List<UserDetail> result = users.stream().map((user) ->
			new UserDetail(user.getId(),
				user.getUsername(),
				user.getEnabled(),
				user.getLastPasswordResetDate(),
				user.getAuthorities().get(0).getName().toString()))
			.collect(Collectors.toList());
		return result;
	}
}