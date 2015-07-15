package com.demo.jmongo.entity;

import java.util.List;

import org.bson.types.ObjectId;

import com.lamfire.jmongo.annotations.Entity;
import com.lamfire.jmongo.annotations.Id;


@Entity
public class Hotel {
	@Id 
	private ObjectId id;
    private String name;
    private Integer stars;
    private List<Address> addresses;


	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}
