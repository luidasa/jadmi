package mx.admino.models.entities;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	public Usuario() {
		this.condominios = new ArrayList<>();
	}
	
	public Usuario(
			@NotBlank @Size(min = 5) String username,
			@NotBlank @Size(min = 5) String password,
			@NotBlank @Size(min = 10) String name,
			@NotBlank @Size(min = 10, max = 10) String phone,
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
	private Long id;
	
	@NotBlank
	@Size(min = 5)
	private String username;
	
	@NotBlank
	@Size(min = 5)
	private String password;

	@Transient
	@NotBlank
	@Size(min = 5)
	private String confirmation;

	@NotBlank
	@Size(min = 10)
	private String name;
	
	@NotBlank
	@Size(min = 10, max = 10)
	private String phone;
	
	private String email;
	
	private Boolean enabled;

	private List<Condominio> condominios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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


	public Usuario merge(Usuario usuario) {

		this.name = usuario.getName();
		this.username = usuario.getUsername();
		this.email = usuario.getEmail();
		this.phone = usuario.getPhone();
		this.password = usuario.getPassword();

		return this;
	}
}
