package com.tutorial.querydsl.mapper;

import com.tutorial.querydsl.model.dto.CategoryDto;
import com.tutorial.querydsl.model.entity.Category;
import com.tutorial.querydsl.model.request.CategoryRequest;
import com.tutorial.querydsl.model.response.CategoryResponse;
import org.springframework.stereotype.Component;

public interface CategoryMapper {

    CategoryDto toDto(CategoryRequest categoryRequest);

    CategoryDto toDto(Category category);

    CategoryResponse toResponse(CategoryDto categoryDto);
}
