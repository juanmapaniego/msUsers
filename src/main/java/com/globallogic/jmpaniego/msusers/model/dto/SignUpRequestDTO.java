package com.globallogic.jmpaniego.msusers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {
    private String name;
    @NotBlank
    @Pattern(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$",
            message = "Email must follow this format 'aaaaaaa@undominio.algo'"
    )
    private String email;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?!.*[A-Z].*[A-Z])(?!.*[0-9].*[0-9].*[0-9])(?=.*[0-9].*[0-9])(?=.*[a-z])(?=.{8,12}$).*",
            message = "Password must contain one UpperCase, exactly 2 numbers, and the rest lowercase letter. With a size between 8 asn 12."
    )
    private String password;
    private List<PhoneDTO> phones;
}
