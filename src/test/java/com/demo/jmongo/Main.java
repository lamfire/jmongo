package com.demo.jmongo;

import com.demo.jmongo.dao.MemberDAO;
import com.demo.jmongo.dao.UserDAO;
import com.demo.jmongo.entity.Member;
import com.demo.jmongo.entity.User;
import com.lamfire.utils.RandomUtils;

import java.util.Date;

public class Main {

	public static void main(String[] args) {
		MemberDAO dao = new MemberDAO("Member1");
		System.out.println(dao.count());


        Member member = new Member();
        member.setId(RandomUtils.nextLong());
        member.setAge(RandomUtils.nextInt(90));
        member.setName(RandomUtils.randomText(30));
        member.setJoinDate(new Date());
        dao.save(member);

		System.out.println(dao.count());



	}
}
