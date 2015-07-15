package com.demo.jmongo.test;

import java.util.ArrayList;
import java.util.List;

import com.demo.jmongo.dao.HotelDAO;
import com.demo.jmongo.entity.Address;
import com.demo.jmongo.entity.Hotel;
import com.lamfire.code.PUID;
import com.lamfire.json.JSON;
import com.lamfire.jmongo.query.Query;
import com.lamfire.utils.RandomUtils;
import com.mongodb.WriteConcern;

public class HotelTest {
	static HotelDAO dao = new HotelDAO();

	public static void insert() {
		Hotel hotel = new Hotel();
		hotel.setStars(RandomUtils.nextInt(5));
		hotel.setName(PUID.puidAsString());
		Address address = new Address();
		address.setCountry("CN");
		address.setCity("GZ");
		address.setPostCode("510000");
		address.setStreet("TianHe");
		
		Address address1 = new Address();
		address1.setCountry("CN");
		address1.setCity("GZ");
		address1.setPostCode("510000");
		address1.setStreet("TianHe");

		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address);
		addresses.add(address1);

		hotel.setAddresses(addresses);
		dao.save(hotel);
	}

	public static Long insertRandom() {
		try {
			Hotel hotel = new Hotel();
			hotel.setStars(RandomUtils.nextInt(5));
			hotel.setName(PUID.puidAsString());
			Address address = new Address();
			address.setCountry(String.valueOf(RandomUtils.nextInt(100)));
			address.setCity(String.valueOf(RandomUtils.nextInt(1000)));
			address.setPostCode(String.valueOf(RandomUtils.nextInt(100)));
			address.setStreet(String.valueOf(RandomUtils.nextInt(100)));

			List<Address> addresses = new ArrayList<Address>();
			addresses.add(address);

			hotel.setAddresses(addresses);

			dao.save(hotel,WriteConcern.MAJORITY);
		} catch (Exception e) {

		}
		return -1l;
	}

	public static List<Hotel> findAgeGreater(int greaterThan, int offset, int limit) {
		Query<Hotel> query = dao.createQuery();
		query.filter("stars >=", greaterThan);
		query.limit(limit);
		query.offset(offset);
		query.asList();
		List<Hotel> result = query.asList();

		return result;
	}

	public static void main(String[] args) {
		//insert();
		
		Query<Hotel> query = dao.createQuery();
		query.field("stars").lessThan(5);
		query.excludeFields("stars","name");
		
		List<Hotel> list = query.asList();
		for(Hotel hotel : list){
			System.out.println(JSON.toJSONString(hotel));
		}
	}
}
