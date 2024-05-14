package com.tutorial.querydsl.repository;

import com.tutorial.querydsl.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaSpecificationExecutor<Item>, JpaRepository<Item, Long>{

}
