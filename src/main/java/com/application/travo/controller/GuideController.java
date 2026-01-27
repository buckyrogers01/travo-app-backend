package com.application.travo.controller;
import com.application.travo.Entity.GuideEntity;
import com.application.travo.Entity.UserEntity;
import com.application.travo.Repo.GuideRepository;
import com.application.travo.Repo.UserRepository;
import com.application.travo.Service.AuthService;
import com.application.travo.Service.GuideService;
import com.application.travo.Service.OtpService;
import com.application.travo.Utility.JwtUtil;
import com.application.travo.dtos.GuideProfileDTO;
import com.application.travo.dtos.SendOtpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guides")
@RequiredArgsConstructor
public class GuideController {

    private final GuideRepository guideRepo;
    private final UserRepository userRepo;
    private final GuideService guideService;
    private final OtpService otpService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

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

}
