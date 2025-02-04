package com.basic.GADI;

import com.basic.GADI.repository.ResRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Transactional
class GadiApplicationTests {

	@Autowired
	ResRepository resRepository;

	@Test
	void test() {
		Pageable pageable = new Pageable() {
			@Override
			public int getPageNumber() {
				return 1;
			}

			@Override
			public int getPageSize() {
				return 10;
			}

			@Override
			public long getOffset() {
				return 0;
			}

			@Override
			public Sort getSort() {
				return Sort.by("resId");
			}

			@Override
			public Pageable next() {
				return null;
			}

			@Override
			public Pageable previousOrFirst() {
				return null;
			}

			@Override
			public Pageable first() {
				return null;
			}

			@Override
			public Pageable withPage(int pageNumber) {
				return null;
			}

			@Override
			public boolean hasPrevious() {
				return false;
			}
		};

		//Page<Restaurants> page = resRepository.findAllWithRatings();
		//System.out.println("페이지=" + page.getContent());


	}

}
