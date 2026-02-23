package com.application.travo.Repo;

import com.application.travo.Entity.GuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuideRepository extends JpaRepository<GuideEntity, Long>,
        JpaSpecificationExecutor<GuideEntity> {

    boolean existsByUserId(Long userId);
}