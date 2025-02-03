package com.basic.GADI.service;

import com.basic.GADI.entity.User;
import com.basic.GADI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public List<User> findUserList() {
        return userRepository.findAll();
    }
}
