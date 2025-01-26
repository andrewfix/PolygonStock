package com.stock.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistryDTO {
    @NotBlank(message = "Name can not be empty")
    String name;
    @NotBlank(message = "EMail can not be empty")
    @Email(message = "Incorrect email")
    String email;
    @NotBlank(message = "Password can not be empty")
    String password;
}
