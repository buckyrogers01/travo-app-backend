package com.application.travo.Repo;
import com.application.travo.Entity.GuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<GuideEntity, Long> {
    boolean existsByUserId(Long userId);
}
