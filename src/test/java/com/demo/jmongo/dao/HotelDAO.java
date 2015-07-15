package com.demo.jmongo.dao;

import org.bson.types.ObjectId;

import com.demo.jmongo.entity.Hotel;
import com.lamfire.jmongo.dao.DAOSupport;


public class HotelDAO extends DAOSupport<Hotel, ObjectId>{

	public HotelDAO() {
		super("default","test",Hotel.class);
	}

}
