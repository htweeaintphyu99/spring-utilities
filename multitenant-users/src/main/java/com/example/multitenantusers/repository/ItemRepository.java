package com.example.multitenantusers.repository;

import com.example.multitenantusers.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
