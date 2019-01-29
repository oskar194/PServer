package com.admin.inz.server.budgetrook.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.inz.server.budgetrook.model.Privilege;
import com.admin.inz.server.budgetrook.model.Role;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.UserRepository;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	public UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		try {
			final User user = userRepository.findByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException("Can't find user with email" + email);
			}
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					true, true, true, true, getAuthorities(user.getRoles()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		System.out.println(roles);
		
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String name : privileges) {
			authorities.add(new SimpleGrantedAuthority(name));
		}
		return authorities;
	}

	private List<String> getPrivileges(Collection<Role> roles) {
		List<String> names = new ArrayList<String>();
		List<Privilege> privileges = new ArrayList<Privilege>();
		for (Role role : roles) {
			names.add(role.getName());
			privileges.addAll(role.getPrivileges());
		}
		for (Privilege privilege : privileges) {
			names.add(privilege.getName());
		}
		return names;

	}

}
