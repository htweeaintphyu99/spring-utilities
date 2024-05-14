package com.tutorial.querydsl.repository;

import com.tutorial.querydsl.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
