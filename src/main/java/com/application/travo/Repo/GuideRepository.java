package com.application.travo.Repo;
import com.application.travo.Entity.GuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<GuideEntity, Long> {
    Optional<GuideEntity> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
