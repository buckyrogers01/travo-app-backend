package com.application.travo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "guide_verification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuideVerificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long guideId;

    @Column(nullable = false)
    private String idType;

    @Column(nullable = false)
    private String emergencyPhone;

    @Column(nullable = false)
    private String idFrontKey;

    @Column(nullable = false)
    private String idBackKey;

    private String certificateKey;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
