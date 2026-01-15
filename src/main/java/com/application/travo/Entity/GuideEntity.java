package com.application.travo.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guides")
public class GuideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guideId;

    @Column(nullable = false, unique = true)
    private Long userId;

    private String bio;
    private Integer experienceYears;

    @Enumerated(EnumType.STRING)
    private GuideStatus status; // PENDING, VERIFIED, REJECTED

    private String baseLocation;

    private LocalDateTime createdAt = LocalDateTime.now();
}
