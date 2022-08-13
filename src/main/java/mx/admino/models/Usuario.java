package mx.admino.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "usuarios")
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	
	public Usuario() {
		this.roles = new ArrayList<Roles>();
	}

	public Usuario(String username, String password, Set<GrantedAuthority> grantedAuthorities) {
		
		this.username = username;
		this.password = password;
		this.authorities = grantedAuthorities;
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

	private Set<GrantedAuthority> authorities;

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
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
		return this.getEnabled();
	}
}
