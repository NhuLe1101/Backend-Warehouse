package com.backend.warehouse.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.warehouse.entity.User;
import com.backend.warehouse.payload.request.LoginRequest;
import com.backend.warehouse.payload.request.SignupRequest;
import com.backend.warehouse.payload.response.JwtResponse;
import com.backend.warehouse.payload.response.MessageResponse;
import com.backend.warehouse.repository.UserRepository;
import com.backend.warehouse.security.jwt.JwtUtils;
import com.backend.warehouse.service.UserDetailsImpl;

import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginSignupController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Optional<User> checkLogin = userRepository.findByUser(loginRequest.getUsername());
		if (checkLogin.isPresent()) {
			User user = checkLogin.get();
			if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserid(), userDetails.getUsername(),
						userDetails.getProfile_name(), userDetails.getEmail()));
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Mật khẩu không đúng!"));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Tài khoản không tồn tại!"));
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		Optional<User> userCheck = userRepository.existsByUser(signUpRequest.getUsername());
		if (!userCheck.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Tài khoản này đã tồn tại!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getProfileName(), signUpRequest.getEmail());


		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Đăng ký thành công!"));
	}
}
