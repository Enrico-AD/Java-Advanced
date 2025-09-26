package com.fiap.enrico_andrade.security.service;

import com.fiap.enrico_andrade.security.entity.AppUser;

import java.util.List;

public interface UserService {
    public List<AppUser> findAll();
    public void deleteById(Integer id);
    public void promoteToAdmin(Integer id);
}
