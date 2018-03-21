package com.kchmielewski.sda.java6.angular.player.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface TeamRepository extends JpaRepository<TeamEntity, Integer> {
    Optional<TeamEntity> findByName(String name);
}
