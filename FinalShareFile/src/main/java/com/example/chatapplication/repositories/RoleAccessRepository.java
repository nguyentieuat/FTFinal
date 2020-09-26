package com.example.chatapplication.repositories;

import com.example.chatapplication.domain.RoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAccessRepository extends JpaRepository<RoleAccess, Integer> {
   }
