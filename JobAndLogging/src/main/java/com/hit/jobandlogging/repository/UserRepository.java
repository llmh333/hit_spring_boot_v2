package com.hit.jobandlogging.repository;

import com.hit.jobandlogging.constant.Gender;
import com.hit.jobandlogging.domain.dto.request.UpdateUserRequestDto;
import com.hit.jobandlogging.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    User findByEmail(String email);

    @Procedure(procedureName = "sp_get_user_by_id")
    User getUserById(String id);

    @Procedure(procedureName = "sp_update_user")
    User updateUser(String userId, String firstName, String lastName, String email, String gender);
}
