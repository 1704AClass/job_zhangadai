package com.health.service;

import java.util.List;

import com.health.pojo.Member;

public interface MemberService {

	Member findByTelephone(String telephone);

	void add(Member member);
	
	List<Integer> findMemberCountByMonths(List<String> months);
}
