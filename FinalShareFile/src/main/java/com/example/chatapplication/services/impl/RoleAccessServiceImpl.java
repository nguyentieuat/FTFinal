package com.example.chatapplication.services.impl;

import com.example.chatapplication.domain.RoleAccess;
import com.example.chatapplication.repositories.RoleAccessRepository;
import com.example.chatapplication.services.RoleAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleAccessServiceImpl implements RoleAccessService {
    @Autowired
    private RoleAccessRepository roleAccessRepository;

    @Override
    public List<RoleAccess> getAllRole() {
        return roleAccessRepository.findAll();
    }
}
