package com.raminq.redisapp.api;


import com.raminq.redisapp.model.User;
import com.raminq.redisapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserResource {

    private final UserRepository userRepository;

    @PostMapping("/add")
    public void add(@RequestBody User user) {
        userRepository.save(user);
    }

    @GetMapping("/findAll")
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll().values());
    }

    @GetMapping("/findById/{id}")
    public User findById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @PostMapping("/update")
    public void update(@RequestBody User user) {
        userRepository.update(user);
    }

    @PostMapping("/delete/{id}")
    public void Delete(@PathVariable String id) {
        userRepository.delete(id);
    }
}
