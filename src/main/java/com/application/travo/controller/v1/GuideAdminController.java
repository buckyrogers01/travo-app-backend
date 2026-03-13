package com.application.travo.controller.v1;

import com.application.travo.Entity.GuideEntity;
import com.application.travo.Entity.GuideStatus;
import com.application.travo.Service.GuideService;
import com.application.travo.dtos.GuideFilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/guides")
@RequiredArgsConstructor
public class GuideAdminController {
    private final GuideService guideService;

    @PostMapping("/all")
    public ResponseEntity<?> getAllGuides(
            @RequestBody GuideFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<GuideEntity> guides = guideService.getAllGuides(filter, page, size);

        return ResponseEntity.ok(guides);
    }

    @GetMapping("/{guideId}")
    public ResponseEntity<?> getGuideById(@PathVariable Long guideId) {

        Map<String, Object> guide = guideService.getGuideById(guideId);

        return ResponseEntity.ok(guide);
    }

    @PostMapping("/{guideId}/approve")
    public ResponseEntity<?> approveGuide(@PathVariable Long guideId){

        guideService.updateGuideStatus(guideId, GuideStatus.VERIFIED);

        return ResponseEntity.ok("Guide approved");
    }

    @PostMapping("/{guideId}/reject")
    public ResponseEntity<?> rejectGuide(@PathVariable Long guideId){

        guideService.updateGuideStatus(guideId, GuideStatus.REJECTED);

        return ResponseEntity.ok("Guide rejected");
    }
}
