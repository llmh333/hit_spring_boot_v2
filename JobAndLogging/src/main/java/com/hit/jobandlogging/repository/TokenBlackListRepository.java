package com.hit.jobandlogging.repository;

import com.hit.jobandlogging.domain.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
}
