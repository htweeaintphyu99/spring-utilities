package com.tutorial.awss3.repository;

import com.tutorial.awss3.model.entity.Image;
import com.tutorial.awss3.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByItem(Item item);
}
