package com.basic.GADI;

import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.repository.ResRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class GadiApplicationTests {

	@Autowired
	ResRepository resRepository;

	@Test
	void test() {
		List<Restaurants> list = resRepository.findAll();
/*		for(Restaurants res : list) {
			System.out.println(res.getResName());
		}*/
	}

}
