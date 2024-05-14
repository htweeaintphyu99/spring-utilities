package com.tutorial.awss3.model.mapper;

import com.tutorial.awss3.model.dto.ImageDto;
import com.tutorial.awss3.model.entity.Image;

public interface ImageMapper {

    ImageDto toDto(Image image);
}
