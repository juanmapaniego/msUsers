package com.globallogic.jmpaniego.msusers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDTO {
    private String id;
    private String name;
    private String email;
    private List<PhoneDTO> phones;
    private String created;
    private String lastLogin;
    private String token;
    private String isActive;
}
