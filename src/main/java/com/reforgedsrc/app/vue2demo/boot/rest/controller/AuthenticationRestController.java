
package com.reforgedsrc.app.vue2demo.boot.rest.controller;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

import com.reforgedsrc.app.vue2demo.boot.security.model.JwtAuthenticationRequest;
import com.reforgedsrc.app.vue2demo.boot.security.model.JwtAuthenticationResponse;
import com.reforgedsrc.app.vue2demo.boot.security.model.JwtTokenUtil;
import com.reforgedsrc.app.vue2demo.boot.security.model.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reforgedsrc.app.vue2demo.boot.constant.WebConstant;

@RestController
public class AuthenticationRestController {

	private String tokenHeader = WebConstant.JWT_HTTP_HEAD_AUTHOR;

	@Autowired
	private AuthenticationManager authenticationManager = null;

	@Autowired
	private JwtTokenUtil jwtTokenUtil = null;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService = null;

	@RequestMapping(value=WebConstant.JWT_ROUTE_AUTHEN_PATH, method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
		throws RuntimeException {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		// Reload password post-security so we can generate the token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		// Return the token
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@RequestMapping(value=WebConstant.JWT_ROUTE_AUTHEN_REFRESH, method=RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new RuntimeException("User is disabled!", e);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Bad credentials!", e);
		}
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<String> handleAuthenticationException(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
}