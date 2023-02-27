package com.edgar.transferapi.models.user;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.edgar.transferapi.models.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Entity
@Builder
@Table(name ="funds_account_user",
uniqueConstraints = {
	       
        @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@Size(max =20, message ="20 characters max")
	private String firstname;
	
	@Column(nullable = false)
	@Size(max =20, message ="20 characters max")
	private String lastname;
	
	@Column(nullable = false, unique = true)
	@Size(max = 50, message ="50 characters max")
	@Email
	private String email;
	
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Token> tokens;
	
	public User() {
		
	}
	
	
	public User(Long id, @Size(max = 20, message = "20 characters max") String firstname,
			@Size(max = 20, message = "20 characters max") String lastname,
			@Size(max = 50, message = "50 characters max") @Email String email,
			@Size(min = 6, max = 20, message = "password must be greater than 8 but lass than 25 characters") @NotBlank(message = "cannot be left blank") String password,
			Role role, List<Token> tokens) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.tokens = tokens;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}



	public List<Token> getTokens() {
		return tokens;
	}


	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	
	
	

	/* user details methods to override */

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	/* Set Everything to true  else we can't access it */

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
		return true;
	}

	

}
