package com.demo.jmongo.entity;

import com.lamfire.jmongo.annotations.Entity;
import com.lamfire.jmongo.annotations.Id;
import com.lamfire.jmongo.annotations.Indexed;
import com.lamfire.jmongo.annotations.Property;
import com.lamfire.jmongo.utils.IndexDirection;

@Entity
public class User{
	
	@Id
	private String id;

	@Property(value="user_name")
	private String username;
	
	private String password;
	
	@Indexed(value=IndexDirection.GEO2D)
	private Double[] postion;
		
	@Indexed
	private int age;

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public Double[] getPostion() {
		return postion;
	}

	public void setPostion(Double[] postion) {
		this.postion = postion;
	}
	
	public void setPostion(Double x , Double y) {
		this.postion = new Double[2];
		this.postion[0] = x;
		this.postion[1] = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
