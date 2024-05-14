package com.tutorial.springaudit.mapper;

import com.tutorial.springaudit.entity.User;
import com.tutorial.springaudit.entity.dto.UserDto;

public interface UserMapper {
    UserDto toDto(User user);
}
