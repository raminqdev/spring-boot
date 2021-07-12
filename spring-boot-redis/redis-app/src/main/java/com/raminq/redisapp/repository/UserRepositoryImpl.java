package com.raminq.redisapp.repository;

import com.raminq.redisapp.model.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String REDIS_USER_KEY = "redis_user_key";
    private final HashOperations hashOperations;

    public UserRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(User user) {
        hashOperations.put(REDIS_USER_KEY, user.getId(), user);
    }

    @Override
    public Map<String, User> findAll() {
        return hashOperations.entries(REDIS_USER_KEY);
    }

    @Override
    public User findById(String id) {
        return (User) hashOperations.get(REDIS_USER_KEY, id);
    }

    @Override
    public void update(User user) {
        save(user);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(REDIS_USER_KEY, id);
    }
}
