package com.fiap.enrico_andrade.security.service;

import com.fiap.enrico_andrade.security.entity.AppUser;
import com.fiap.enrico_andrade.security.entity.Role;
import com.fiap.enrico_andrade.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void promoteToAdmin(Integer id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);
        });
    }
}
