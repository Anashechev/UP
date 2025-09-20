package com.example.prakt3.config;

import com.example.prakt3.security.AbsoluteTimeoutFilter;
import com.example.prakt3.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
					.requestMatchers("/admin/**", "/manager/**", "/user/**", "/home", "/").authenticated()
					.anyRequest().authenticated()
			)
			.formLogin(form -> form
					.loginPage("/login")
					.defaultSuccessUrl("/home", true)
					.permitAll()
			)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login?logout")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
			)
			.sessionManagement(session -> session
					.maximumSessions(1)
			);

		http.addFilterAfter(new AbsoluteTimeoutFilter(15 * 60_000L), SecurityContextPersistenceFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
