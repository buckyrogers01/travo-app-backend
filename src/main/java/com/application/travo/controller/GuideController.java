package com.application.travo.controller;
import com.application.travo.Entity.UserEntity;
import com.application.travo.Service.GuideService;
import com.application.travo.dtos.GuideProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guides")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @PostMapping("/register")
    public ResponseEntity<?> registerGuide(@AuthenticationPrincipal UserEntity user) {
        guideService.registerGuide(user.getId());
        return ResponseEntity.ok("Guide profile created");
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody GuideProfileDTO dto) {

        guideService.updateProfile(user.getId(), dto);
        return ResponseEntity.ok("Profile updated");
    }
}
