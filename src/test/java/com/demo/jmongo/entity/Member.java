package com.demo.jmongo.entity;

import java.util.Date;

import com.lamfire.jmongo.annotations.Entity;
import com.lamfire.jmongo.annotations.Id;
import com.lamfire.jmongo.annotations.Indexed;
import com.lamfire.jmongo.utils.IndexDirection;

@Entity
public class Member{
    @Id
    private Long id;

	@Indexed(value=IndexDirection.ASC,unique=true)
	private String name;
	
	private int age;
	private Date joinDate;
	
	public long getMemberID() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
