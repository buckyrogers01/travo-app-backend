package com.application.travo.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationDTO {

    private String govtIdType;
    private List<String> documentKeys;
    private String emergencyContact;
}
