package com.health.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.pojo.Permission;
import com.health.pojo.Role;
import com.health.service.UserService;

@Component
public class SpringSecurityUserService implements UserDetailsService{
	@Reference //注意：此处要通过dubbo远程调用用户服务 
	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		//远程调用用户服务，根据用户名查询用户信息 
		com.health.pojo.User user = userService.findByUsername(username);
		if(user == null){
			//用户名不存在
			return null;
		}
		List<GrantedAuthority> list = new ArrayList<>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			//授予角色
			list.add(new SimpleGrantedAuthority(role.getKeyword()));
			Set<Permission> permissions = role.getPermissions();
			for (Permission permission : permissions) {
				//授权
				list.add(new SimpleGrantedAuthority(permission.getKeyword()));
			}
		}
		UserDetails userDetails = new User(username,user.getPassword(),list);
		return userDetails;
	}

}
