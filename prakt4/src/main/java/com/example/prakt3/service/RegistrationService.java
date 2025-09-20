package com.example.prakt3.service;

import com.example.prakt3.domain.Role;
import com.example.prakt3.domain.User;
import com.example.prakt3.repo.UserRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;

@Service
public class RegistrationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final Validator validator;

	public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public User registerUser(String username, String rawPassword, Set<Role> roles) {
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("Username is required");
		}
		if (!isStrongPassword(rawPassword)) {
			throw new IllegalArgumentException("Password must be at least 8 chars, contain a digit and special char");
		}
		if (userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Username already taken");
		}
		User user = new User();
		user.setUsername(username.trim());
		user.setPasswordHash(passwordEncoder.encode(rawPassword));
		user.setRoles(roles == null || roles.isEmpty() ? Collections.singleton(Role.USER) : roles);
		user.setCreatedAtEpochMillis(Instant.now().toEpochMilli());

		// Bean validation for entity constraints
		var violations = validator.validate(user);
		if (!violations.isEmpty()) {
			throw new IllegalArgumentException("Validation failed: " + violations.iterator().next().getMessage());
		}
		return userRepository.save(user);
	}

	private boolean isStrongPassword(String password) {
		if (password == null) return false;
		if (password.length() < 8) return false;
		boolean hasDigit = password.chars().anyMatch(Character::isDigit);
		boolean hasSpecial = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
		return hasDigit && hasSpecial;
	}
}
