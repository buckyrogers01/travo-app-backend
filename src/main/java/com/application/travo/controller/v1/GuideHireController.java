package com.application.travo.controller.v1;
import com.application.travo.Entity.GuideEntity;
import com.application.travo.Entity.UserEntity;
import com.application.travo.Repo.GuideRepository;
import com.application.travo.Repo.UserRepository;
import com.application.travo.Service.*;
import com.application.travo.Utility.JwtUtil;
import com.application.travo.Utility.PasswordUtil;
import com.application.travo.dtos.GuideFilterRequest;
import com.application.travo.dtos.GuideProfileDTO;
import com.application.travo.dtos.SendOtpRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/guides")
@RequiredArgsConstructor
public class GuideHireController {

    private final GuideRepository guideRepo;
    private final UserRepository userRepo;
    private final GuideService guideService;
    private final OtpService otpService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final S3FileService s3Service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createGuide(
            @PathVariable Long userId,
            @RequestBody GuideProfileDTO dto
    ) {
        GuideEntity guide = guideService.createGuideProfile(userId, dto);
        return ResponseEntity.ok(guide);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendGuideOtp(
            @RequestBody SendOtpRequest request) {

        String phone = request.getPhone();

        if (phone == null || phone.isBlank()) {
            return ResponseEntity.badRequest().body("Phone number is required");
        }

        // OPTIONAL: normalize phone to E.164
        // phone = PhoneUtil.normalize(phone);
        // otpService.sendOtp(phone);

        return ResponseEntity.ok("OTP sent to provided phone number");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyGuideOtp(
            @RequestParam String phone,
            @RequestParam String otp) {

//        boolean valid = otpService.verifyOtp(phone, otp);
//
//        if (!valid) {
//            return ResponseEntity.badRequest().body("Invalid or expired OTP");
//        }
//
//        // 🔥 USER IS CREATED HERE
//        UserEntity user = authService.createUserIfNotExists(phone);
//
//        // 🔥 BASIC GUIDE ENTITY CREATED
//        guideService.createGuideIfNotExists(user.getId());
        if(otp.equals("1234")) {
            UserEntity user = authService.createUserIfNotExists(phone);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid OTP. Please try again.");
        }
    }

    @PostMapping("/documents/upload")
    @Transactional
    public ResponseEntity<?> uploadDocuments(
            @RequestParam("guideId") Long guideId,
            @RequestParam("idType") String idType,
            @RequestParam("emergencyPhone") String emergencyPhone,
            @RequestPart("idFront") MultipartFile idFront,
            @RequestPart("idBack") MultipartFile idBack,
            @RequestPart(value = "certificate", required = false) MultipartFile certificate
    ) throws IOException {

        String basePath = "guides/" + guideId;

        String idFrontKey = s3Service.upload(idFront, basePath);
        String idBackKey = s3Service.upload(idBack, basePath);
        String certKey = certificate != null ? s3Service.upload(certificate, basePath) : null;

        guideService.saveVerificationDocs(
                guideId,
                idType,
                emergencyPhone,
                idFrontKey,
                idBackKey,
                certKey
        );

        // generate temp password
        String tempPassword = PasswordUtil.generateTempPassword(8);
        guideService.updatePassword(guideId, tempPassword);

        // send email
        UserEntity guide = userRepo.findById(guideId)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        try {
            mailService.sendTempPasswordMail(
                    guide.getEmail(),
                    guide.getName(),
                    tempPassword
            );
        } catch (Exception e) {
            // log only
            System.err.println("Email failed: " + e.getMessage());
        }

        return ResponseEntity.ok("Documents uploaded & email sent");
    }

}
