package com.stock.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuthDTO {
    @Email(message = "Incorrect email")
    String email;
    @NotBlank(message = "Password can not be empty")
    String password;
}
