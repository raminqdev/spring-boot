package com.raminq.security.repository.security;

import com.raminq.security.domain.entity.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, UUID> {
}
