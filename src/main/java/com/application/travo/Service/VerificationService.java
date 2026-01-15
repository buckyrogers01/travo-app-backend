package com.application.travo.Service;

import com.application.travo.Entity.GuideEntity;
import com.application.travo.Entity.GuideVerificationEntity;
import com.application.travo.Entity.GuideStatus;
import com.application.travo.Repo.GuideRepository;
import com.application.travo.Repo.GuideVerificationRepository;
import com.application.travo.dtos.VerificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final GuideRepository guideRepository;
    private final GuideVerificationRepository verificationRepository;

    /**
     * Guide submits verification
     */
    @Transactional
    public void submit(Long userId, VerificationDTO dto) {

        GuideEntity guide = guideRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        if (verificationRepository.existsByGuideId(guide.getGuideId())) {
            throw new RuntimeException("Verification already submitted");
        }

        GuideVerificationEntity verification = new GuideVerificationEntity();
        verification.setGuideId(guide.getGuideId());
        verification.setGovtIdType(dto.getGovtIdType());
        verification.setDocuments(dto.getDocumentKeys().toString()); // JSON as string
        verification.setStatus(GuideStatus.PENDING);

        verificationRepository.save(verification);

        guide.setStatus(GuideStatus.PENDING);
        guideRepository.save(guide);
    }

    /**
     * Admin approves verification
     */
    @Transactional
    public void approve(Long guideId) {

        GuideVerificationEntity verification = verificationRepository
                .findByGuideId(guideId)
                .orElseThrow(() -> new RuntimeException("Verification not found"));

        verification.setStatus(GuideStatus.VERIFIED);
        verification.setVerifiedAt(LocalDateTime.now());
        verificationRepository.save(verification);

        GuideEntity guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        guide.setStatus(GuideStatus.VERIFIED);
        guideRepository.save(guide);
    }

    /**
     * Admin rejects verification
     */
    @Transactional
    public void reject(Long guideId) {

        GuideVerificationEntity verification = verificationRepository
                .findByGuideId(guideId)
                .orElseThrow(() -> new RuntimeException("Verification not found"));

        verification.setStatus(GuideStatus.REJECTED);
        verification.setVerifiedAt(LocalDateTime.now());
        verificationRepository.save(verification);

        GuideEntity guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        guide.setStatus(GuideStatus.REJECTED);
        guideRepository.save(guide);
    }
}
