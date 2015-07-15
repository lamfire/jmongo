package com.demo.jmongo.dao;

import com.demo.jmongo.entity.Member;
import com.lamfire.jmongo.dao.DAOSupport;

public class MemberDAO extends DAOSupport<Member, Long>{

	public MemberDAO() {
		super("default","test",Member.class);
	}

    public MemberDAO(String collection) {
        super("default","test",Member.class,collection);
    }
}
