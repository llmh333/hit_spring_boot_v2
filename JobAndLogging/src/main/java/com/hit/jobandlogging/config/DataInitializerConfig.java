package com.hit.jobandlogging.config;

import com.hit.jobandlogging.constant.RoleConstant;
import com.hit.jobandlogging.domain.entity.Role;
import com.hit.jobandlogging.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializerConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            log.info("Bắt đầu khởi tạo dữ liệu ROLE...");
            roleRepository.save(Role.builder().name(RoleConstant.USER).description(null).build());
            roleRepository.save(Role.builder().name(RoleConstant.ADMIN).description(null).build());
            log.info("Khởi tạo dữ liệu ROLE thành công");
        }
    }
}
