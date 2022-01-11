package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.entity.User;
import com.barisertakus.toyotamanport.repository.UserRepository;
import com.barisertakus.toyotamanport.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }
        log.error("User not found! username : {}", username);
        throw new IllegalArgumentException("User not found!");
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> saveAll(List<User> users) {
        return userRepository.saveAll(users);
    }
}
