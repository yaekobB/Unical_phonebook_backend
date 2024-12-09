package com.api.user_management.io.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.UserEntity;

@Repository
public interface ChatUserRepository extends JpaRepository<UserEntity, Long> {

}
