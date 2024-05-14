package com.tutorial.awss3.repository;

import com.tutorial.awss3.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
