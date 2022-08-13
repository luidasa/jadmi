package mx.admino.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public Usuario() {
		this.roles = new ArrayList<Roles>();
		this.authorities = new ArrayList<>();
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
	
	@NotNull
	@NotEmpty
	@NotBlank
	private List<Roles> roles;
	
	private Boolean enabled;

	private List<String> authorities;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
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

	public List<Roles> getRole() {
		return roles;
	}

	public void setRole(List<Roles> role) {
		this.roles = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
}
