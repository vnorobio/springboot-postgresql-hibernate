package com.example.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
