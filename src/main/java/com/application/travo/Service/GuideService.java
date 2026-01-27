package com.application.travo.Service;

import com.application.travo.Entity.GuideEntity;
import com.application.travo.Entity.UserEntity;
import com.application.travo.dtos.GuideProfileDTO;
import com.application.travo.Repo.GuideRepository;
import com.application.travo.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final UserRepository userRepository;
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
}
