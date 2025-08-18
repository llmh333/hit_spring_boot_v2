package com.hit.jobandlogging.domain.entity;

import com.hit.jobandlogging.audit.AuditableEntity;
import com.hit.jobandlogging.audit.DateAudit;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "token_black_list")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenBlackList extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String token;

    @Column(nullable = false)
    LocalDateTime expiredToken;
}
