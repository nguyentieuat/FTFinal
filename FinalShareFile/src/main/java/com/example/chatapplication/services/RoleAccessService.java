package com.example.chatapplication.services;

import com.example.chatapplication.domain.RoleAccess;

import java.util.List;

public interface RoleAccessService {
    /**
     * Get all role access file
     * @return
     */
    List<RoleAccess> getAllRole();
}
