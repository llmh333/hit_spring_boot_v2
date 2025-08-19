package com.hit.jobandlogging.service.impl;

import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.constant.Gender;
import com.hit.jobandlogging.domain.dto.request.UpdateUserRequestDto;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;
import com.hit.jobandlogging.domain.entity.User;
import com.hit.jobandlogging.domain.mapper.UserMapper;
import com.hit.jobandlogging.exception.NotFoundException;
import com.hit.jobandlogging.repository.UserRepository;
import com.hit.jobandlogging.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
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

    @Override
    @Transactional
    public UserResponseDto updateUser(String userId, UpdateUserRequestDto requestDto) {
        log.info("Update User with id: {} requestDto: {}", userId, requestDto.toString());
        User newUser = userRepository.updateUser(
                userId,
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getEmail(),
                Gender.FEMALE.name().equals(requestDto.getGender()) ? Gender.FEMALE.name() : Gender.MALE.name()
        );

        if (newUser == null) {
            log.info("User with id: " + userId + " not found");
            throw new NotFoundException(ErrorMessage.User.ID_NOT_FOUND, new String[]{userId});
        }
        log.info("User with id: {} updated", userId);
        return userMapper.toUserResponseDto(newUser);
    }
}
