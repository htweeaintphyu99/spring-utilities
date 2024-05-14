package com.tutorial.criteria.repository;

import com.tutorial.criteria.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaSpecificationExecutor<Item>, JpaRepository<Item, Long>{

}
