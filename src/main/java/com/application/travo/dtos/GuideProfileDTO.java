package com.application.travo.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuideProfileDTO {
    private String name;
    private String email;
    private String bio;
    private Integer experienceYears;   // 👈 Integer
    private String baseLocation;
    private List<String> selectedLanguages;
    private List<String> expertise;
}