package com.health.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.Result;
import com.health.pojo.User;
import com.health.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	//获取当前登录用户的用户名
	@RequestMapping("/getUsername")
	public Result getUsername()throws Exception{
		try {
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
		} catch (Exception e) {
			return new Result(false, MessageConstant.GET_USERNAME_FAIL);
		}
	}
}
