// ==================== FILE: pom.xml (dependencies to add) ====================

<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
<groupId>io.jsonwebtoken</groupId>
<artifactId>jjwt</artifactId>
<version>0.9.1</version>
</dependency>


// ==================== FILE: SecurityConfig.java ====================

package com.cognizant.springlearn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/authenticate").permitAll()
				.anyRequest().authenticated()
				.and()
				.httpBasic();
	}
}


// ==================== FILE: JwtUtil.java ====================

package com.cognizant.springlearn.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "mySecretKeyForJwtGeneration";
	private static final long EXPIRATION_TIME = 1200000;

	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
}


// ==================== FILE: AuthenticationController.java ====================

package com.cognizant.springlearn.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.util.JwtUtil;

@RestController
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String authHeader) {
		LOGGER.info("Start");

		if (authHeader == null || !authHeader.startsWith("Basic ")) {
			LOGGER.info("End");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String base64Credentials = authHeader.substring("Basic ".length());
		String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
		String[] values = credentials.split(":", 2);
		String username = values[0];
		String password = values[1];

		if (!"user".equals(username) || !"pwd".equals(password)) {
			LOGGER.info("End");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String token = jwtUtil.generateToken(username);

		Map<String, String> response = new HashMap<>();
		response.put("token", token);

		LOGGER.info("End");
		return ResponseEntity.ok(response);
	}
}
