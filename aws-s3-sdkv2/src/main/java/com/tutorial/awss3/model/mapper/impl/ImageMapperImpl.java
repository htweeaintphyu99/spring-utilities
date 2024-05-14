package com.tutorial.awss3.model.mapper.impl;

import com.tutorial.awss3.model.dto.ImageDto;
import com.tutorial.awss3.model.entity.Image;
import com.tutorial.awss3.model.mapper.ImageMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageMapperImpl implements ImageMapper {
    @Override
    public ImageDto toDto(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setUrl(image.getUrl());
        return imageDto;
    }
}
