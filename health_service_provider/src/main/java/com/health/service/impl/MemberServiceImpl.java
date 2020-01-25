package com.health.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.mapper.MemberDao;
import com.health.pojo.Member;
import com.health.service.MemberService;
import com.health.utils.MD5Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员服务
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    //保存会员信息
    public void add(Member member) {
        String password = member.getPassword();
        if(password != null){
            //使用md5将明文密码进行加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }

	@Override
	public List<Integer> findMemberCountByMonths(List<String> months) {
		List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = month + ".31";//2018.05.31
            Integer count = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(count);
        }
        return memberCount;
	}
}
