package com.tutorial.springaudit.mapper;

import com.tutorial.springaudit.entity.User;
import com.tutorial.springaudit.entity.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
