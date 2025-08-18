package com.hit.jobandlogging.service.impl;

import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;
import com.hit.jobandlogging.domain.entity.User;
import com.hit.jobandlogging.domain.mapper.UserMapper;
import com.hit.jobandlogging.exception.NotFoundException;
import com.hit.jobandlogging.repository.UserRepository;
import com.hit.jobandlogging.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto getUserById(String userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException(ErrorMessage.User.ID_NOT_FOUND, new String[]{userId});
        }
        return userMapper.toUserResponseDto(user);
    }
}
