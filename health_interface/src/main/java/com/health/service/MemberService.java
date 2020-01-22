package com.health.service;

import com.health.pojo.Member;

public interface MemberService {

	Member findByTelephone(String telephone);

	void add(Member member);
}
