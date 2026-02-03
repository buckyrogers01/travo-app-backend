package com.application.travo.Repo;

import com.application.travo.Entity.GuideVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuideVerificationRepository
        extends JpaRepository<GuideVerificationEntity, Long> {

    Optional<GuideVerificationEntity> findByGuideId(Long guideId);
}
