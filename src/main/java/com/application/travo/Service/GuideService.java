package com.application.travo.Service;

import com.application.travo.Entity.GuideEntity;
import com.application.travo.Entity.GuideVerificationEntity;
import com.application.travo.Entity.UserEntity;
import com.application.travo.Repo.GuideVerificationRepository;
import com.application.travo.dtos.GuideProfileDTO;
import com.application.travo.Repo.GuideRepository;
import com.application.travo.Repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final UserRepository userRepository;
    private final GuideVerificationRepository verificationRepo;

    @Autowired
    private ObjectMapper objectMapper;


    public GuideEntity createGuideProfile(Long userId, GuideProfileDTO dto) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (guideRepository.existsByUserId(userId)) {
            throw new RuntimeException("Guide profile already exists");
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        userRepository.save(user);

        String languagesJson =
                objectMapper.writeValueAsString(dto.getSelectedLanguages());
        String expertiseTags =  objectMapper.writeValueAsString(dto.getExpertise());

        GuideEntity guide = GuideEntity.builder()
                .bio(dto.getBio())
                .experienceYears(dto.getExperienceYears())
                .baseLocation(dto.getBaseLocation())
                .expertise_tags(expertiseTags)
                .languages(languagesJson)
                .user(user)
                .build();

        return guideRepository.save(guide);
    }

    @Transactional
    public void saveVerificationDocs(
            Long guideId,
            String idType,
            String emergencyPhone,
            String idFrontKey,
            String idBackKey,
            String certificateKey
    ) {
        GuideVerificationEntity verification =
                verificationRepo.findByGuideId(guideId)
                        .orElseGet(() -> GuideVerificationEntity.builder()
                                .guideId(guideId)
                                .createdAt(LocalDateTime.now())
                                .build()
                        );

        verification.setIdType(idType);
        verification.setEmergencyPhone(emergencyPhone);
        verification.setIdFrontKey(idFrontKey);
        verification.setIdBackKey(idBackKey);
        verification.setCertificateKey(certificateKey);
        verification.setUpdatedAt(LocalDateTime.now());

        verificationRepo.save(verification);
    }
}
