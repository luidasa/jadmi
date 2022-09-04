package mx.admino.models.entities;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "usuarios")
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	public Usuario() {
		this.condominios = new ArrayList<>();
	}
	
	public Usuario(
			@NotNull @NotEmpty @NotBlank @Size(min = 5) String username,
			@NotNull @NotEmpty @NotBlank @Size(min = 5) String password, 
			@NotNull @NotEmpty @NotBlank @Size(min = 10) String name,
			@NotNull @NotEmpty @NotBlank @Size(min = 10, max = 10) String phone, 
			String email, 
			Boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.enabled = enabled;
	}

	@Id
	private String id;
	
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 5)
	@Indexed(unique = true)
	private String username;
	
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 5)
	private String password;

	@Transient
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 5)
	private String confirmation;

	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 10)
	private String name;
	
	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 10, max = 10)
	private String phone;
	
	private String email;
	
	private Boolean enabled;

	private List<Condominio> condominios;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {

		return username;
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
		return this.enabled;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public Set<GrantedAuthority> getAuthorities() {
		return null;
		/*
		return this.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.toString()))
				.collect(Collectors.toSet());

		 */
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public List<Condominio> getCondominios() {
		return condominios;
	}

	public void setCondominios(List<Condominio> condominios) {
		this.condominios = condominios;
	}

	@Override
	public String toString() {
		return "Usuario [username=" + username + ", name=" + name + ", phone=" + phone + ", email=" + email
				+ ", enabled=" + enabled + "]";
	}
	
	
}
