package com.raminq.security.repository.security;

import com.raminq.security.domain.entity.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission, Long> {
}
