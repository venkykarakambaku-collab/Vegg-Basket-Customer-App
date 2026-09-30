package com.veggiebasket.release_manager_backend.repository;

import com.veggiebasket.release_manager_backend.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReleaseRepository extends JpaRepository<Release, Long> {

    Optional<Release> findByVersion(String version);

    List<Release> findAllByOrderByVersionCodeDesc();

    List<Release> findByPublishedTrueOrderByVersionCodeDesc();

    Optional<Release> findFirstByPublishedTrueOrderByVersionCodeDesc();
}