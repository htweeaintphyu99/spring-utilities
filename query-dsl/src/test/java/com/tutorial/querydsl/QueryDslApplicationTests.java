package com.tutorial.querydsl;

import com.tutorial.querydsl.dao.ItemDao;
import com.tutorial.querydsl.model.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class QueryDslApplicationTests {

	@Autowired
	ItemDao itemDao;

	@Test
	void contextLoads() {
	}

	@Test
	void findItemsByPriceGreaterThan() {
		List<Item> items = itemDao.getItemsGreaterThanPrice(BigDecimal.valueOf(10000));
		items.forEach(System.err::println);
	}

	@Test
	void findItemsBySku() {
		List<Item> items = itemDao.getItemBySku(111111L);
		items.forEach(System.err::println);
	}

}
