package com.application.travo.Service;

import com.application.travo.Entity.GuideEntity;
import com.application.travo.Entity.GuideStatus;
import com.application.travo.Entity.GuideVerificationEntity;
import com.application.travo.Repo.GuideRepository;
import com.application.travo.dtos.GuideProfileDTO;
import com.application.travo.dtos.VerificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuideService {

    @Autowired
    private GuideRepository guideRepo;

    public void registerGuide(Long userId) {

        if (guideRepo.existsByUserId(userId)) {
            throw new RuntimeException("User already a guide");
        }

        GuideEntity guide = new GuideEntity();
        guide.setUserId(userId);
        guide.setStatus(GuideStatus.PENDING);

        guideRepo.save(guide);
    }

    public void updateProfile(Long userId, GuideProfileDTO dto) {

        GuideEntity guide = guideRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        guide.setBio(dto.getBio());
        guide.setExperienceYears(dto.getExperienceYears());
        guide.setBaseLocation(dto.getBaseLocation());

        guideRepo.save(guide);
    }

}
