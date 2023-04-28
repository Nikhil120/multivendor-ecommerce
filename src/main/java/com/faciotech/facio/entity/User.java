package com.faciotech.facio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.faciotech.facio.enums.AppRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, length = 20)
	private String firstname;

	@Column(nullable = false, length = 20)
	private String lastname;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 64)
	private String password;

	@Column
	private String phoneNumber;

	@Column
	private Boolean isEmailVerified;

	@Column
	private Boolean isPhoneVerified;

	@Column
	private String verificationCode;

	@Column(nullable = false)
	private Boolean isVerified;

	@Column
	private Boolean isActive;

	@ManyToOne
	@JoinColumn(name = "business_id")
	private Business business;

	@ManyToMany
	@JoinTable(name = "business_users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Enumerated(EnumType.STRING)
	private AppRole appRole;

	@OneToMany(mappedBy = "user")
	private List<Token> tokens;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(appRole.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isVerified;
	}

	public String getFullName() {
		return this.firstname + " " + this.lastname;
	}
}
