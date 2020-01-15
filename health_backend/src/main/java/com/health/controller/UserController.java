package com.health.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.pojo.User;
import com.health.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Reference
	UserService userService;
	@RequestMapping("/list")
	public List<User> list(){
		List<User> list=userService.list();
		return list;
	}
	
}
