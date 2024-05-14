package com.tutorial.criteria;

import com.tutorial.criteria.entity.Item;
import com.tutorial.criteria.repository.ItemRepository;
import com.tutorial.criteria.service.specification.ItemSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestItemSpecification {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void findItemByNameCookie() {
        List<Item> items = this.itemRepository.findAll(ItemSpecification.getItemByName("cookie"));
        System.out.println("Item size " + items.size());
        items.forEach(System.err:: println);
    }

    @Test
    public void findItemByKeyword() {
        List<Item> items = this.itemRepository.findAll(ItemSpecification.getItemByNameLike("Cookie"));
        System.out.println("Item size " + items.size());
        items.forEach(System.err:: println);
    }

    @Test
    public void findByItemNotNameLike() {
        List<Item> items = this.itemRepository.findAll(ItemSpecification.getItemNotNameLike("Choco"));
        items.forEach(System.err:: println);
    }

    @Test
    public void findByItemInNames() {
        List<String> names = new ArrayList<>();
        names.add("cookie");
        names.add("chocolate cookie");
        Specification<Item> spec = ItemSpecification.getItemInName(names);
        List<Item> items = this.itemRepository.findAll(spec);
        items.forEach(System.err:: println);
    }
}
