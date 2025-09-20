package com.example.prakt3.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(unique = true, nullable = false)
	private String username;

	@NotBlank
	@Size(min = 8)
	private String passwordHash;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private Set<Role> roles;

	@Column(nullable = false)
	private Long createdAtEpochMillis;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

	public Set<Role> getRoles() { return roles; }
	public void setRoles(Set<Role> roles) { this.roles = roles; }

	public Long getCreatedAtEpochMillis() { return createdAtEpochMillis; }
	public void setCreatedAtEpochMillis(Long createdAtEpochMillis) { this.createdAtEpochMillis = createdAtEpochMillis; }
}
