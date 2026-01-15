package com.application.travo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "guide_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuideVerificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    private Long verificationId;

    @Column(name = "guide_id", nullable = false)
    private Long guideId;

    @Column(name = "govt_id_type", length = 50)
    private String govtIdType;

    /**
     * Stores S3 keys as JSONB
     * Example:
     * ["guides/12/aadhaar_front.jpg", "guides/12/aadhaar_back.jpg"]
     */
    @Column(name = "documents", columnDefinition = "jsonb")
    private String documents;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private GuideStatus status;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
}
