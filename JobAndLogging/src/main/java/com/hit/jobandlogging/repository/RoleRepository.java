package com.hit.jobandlogging.repository;

import com.hit.jobandlogging.constant.RoleConstant;
import com.hit.jobandlogging.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleConstant name);
}
