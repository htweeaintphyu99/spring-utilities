package com.tutorial.querydsl.mapper;

import com.tutorial.querydsl.model.dto.CategoryDto;
import com.tutorial.querydsl.model.entity.Category;
import com.tutorial.querydsl.model.request.CategoryRequest;
import com.tutorial.querydsl.model.response.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements  CategoryMapper{

    @Override
    public CategoryDto toDto(CategoryRequest categoryRequest) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(categoryRequest.getName());
        return  categoryDto;
    }

    @Override
    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    @Override
    public CategoryResponse toResponse(CategoryDto categoryDto) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName(categoryDto.getName());
        return categoryResponse;
    }
}
