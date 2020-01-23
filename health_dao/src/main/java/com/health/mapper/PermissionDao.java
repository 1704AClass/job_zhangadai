package com.health.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.health.pojo.Permission;

public interface PermissionDao {

	@Select("select p.* from t_permission p ,t_role_permission rp where p.id = rp.permission_id and rp.role_id = #{roleId}")
	Set<Permission> findByRoleId(@Param("roleId")Integer roleId);

}
