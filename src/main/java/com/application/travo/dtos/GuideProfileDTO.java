package com.application.travo.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuideProfileDTO {
    private String bio;
    private Integer experienceYears;
    private String baseLocation;
}