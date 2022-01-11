package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.entity.User;

import java.util.List;

public interface UserService {
    long count();

    User findByUsername(String username);

    User save (User user);

    List<User> saveAll(List<User> users);
}
