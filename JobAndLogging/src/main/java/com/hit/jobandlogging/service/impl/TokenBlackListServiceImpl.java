package com.hit.jobandlogging.service.impl;

import com.hit.jobandlogging.domain.entity.TokenBlackList;
import com.hit.jobandlogging.repository.TokenBlackListRepository;
import com.hit.jobandlogging.service.TokenBlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlackListServiceImpl implements TokenBlackListService {

    private final TokenBlackListRepository tokenBlackListRepository;

    @Override
    public boolean addTokenBlackList(String token) {
        try {
            TokenBlackList tokenBlackList = new TokenBlackList();
            tokenBlackList.setToken(token);
            tokenBlackList.setExpiredToken(LocalDateTime.now().plusDays(1));
            tokenBlackListRepository.save(tokenBlackList);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
