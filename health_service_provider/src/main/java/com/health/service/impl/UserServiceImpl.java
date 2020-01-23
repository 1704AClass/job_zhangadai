package com.health.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.mapper.PermissionDao;
import com.health.mapper.RoleDao;
import com.health.mapper.UserDao;
import com.health.pojo.Permission;
import com.health.pojo.Role;
import com.health.pojo.User;
import com.health.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserDao userDao;
	@Autowired
	RoleDao roleDao;
	@Autowired
	PermissionDao permissionDao;
	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		return userDao.list();
	}
	@Override
	public User findByUsername(String username) {
		User user = userDao.findByUsername(username);
		if(user == null){
			return null;
		}
		Integer userId = user.getId();
		Set<Role> roles = roleDao.findByUserId(userId);
		if(roles != null && roles.size() > 0){
			for (Role role : roles) {
				Integer roleId = role.getId();
				Set<Permission> permissions = permissionDao.findByRoleId(roleId);
				if(permissions != null && permissions.size() > 0){
					role.setPermissions(permissions);
				}
			}
			user.setRoles(roles);
		}
		return user;
	}

}
