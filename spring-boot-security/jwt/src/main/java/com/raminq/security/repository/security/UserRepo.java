package com.raminq.security.repository.security;

import com.raminq.security.domain.entity.security.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.raminq.security.configuration.CacheConfig.Users_Cache;

@Repository
@CacheConfig(cacheNames = Users_Cache)
public interface UserRepo extends JpaRepository<User, Long> {

    @Cacheable
    Optional<User> findByUsername(String username);

    @Cacheable
    Optional<User> findById(Long id);

    @Caching(evict = {
            @CacheEvict(key = "#entity.id"),
            @CacheEvict(key = "#entity.username")
    })
    <S extends User> S save(S entity);


    @Query("SELECT u FROM User u WHERE " +
            "(:id IS NULL OR u.id = :id) AND " +
            "(:username IS NULL OR u.username LIKE %:username%) AND " +
            "(:fullName IS NULL OR u.fullName LIKE %:username%) AND" +
            "(:enabled IS NULL OR u.enabled = :enabled)")
    Page<User> search(@Param("id") Long id, @Param("username") String username,
                      @Param("fullName") String fullName, @Param("enabled") Boolean enabled,
                      Pageable pageable);
}
