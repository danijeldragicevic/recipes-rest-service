package ddane.recipes.security.impl;

import ddane.recipes.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ddane.recipes.security.IUserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements IUserDetails {
	private final String username;
	private final String password;
	private final List<GrantedAuthority> rolesAndAuthorities;

	public UserDetailsImpl(User user) {
		username = user.getUsername();
		password = user.getPassword();
		rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return rolesAndAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
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
		return true;
	}
}
