package com.health.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.health.pojo.User;

public interface UserDao {
	@Select("select * from t_user")
	List<User> list();

	@Select("select * from t_user where username = #{username}")
	User findByUsername(@Param("username")String username);

}
