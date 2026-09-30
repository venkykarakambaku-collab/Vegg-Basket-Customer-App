package com.veggiebasket.release_manager_backend.repository;

import com.veggiebasket.release_manager_backend.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> findByEmailAndActiveTrue(String email);

}