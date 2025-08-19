package com.hit.jobandlogging.domain.entity;

import com.hit.jobandlogging.audit.DateAudit;
import com.hit.jobandlogging.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@ToString
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, unique = true, columnDefinition = "varchar(100)")
    String username;

    @Column(nullable = false, columnDefinition = "varchar(250)")
    String password;

    @Column(columnDefinition = "varchar(100)")
    String firstName;

    @Column(columnDefinition = "varchar(100)")
    String lastName;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;
}
