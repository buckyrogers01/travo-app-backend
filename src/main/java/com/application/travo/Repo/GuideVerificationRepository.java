package com.application.travo.Repo;

import com.application.travo.Entity.GuideVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuideVerificationRepository
        extends JpaRepository<GuideVerificationEntity, Long> {

    /**
     * Check if verification already exists for a guide
     */
    boolean existsByGuideId(Long guideId);

    /**
     * Fetch verification record by guideId
     */
    Optional<GuideVerificationEntity> findByGuideId(Long guideId);
}

