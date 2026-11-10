package com.example.studentgrades.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentRequest(
        @NotBlank(message = "Имя не может быть пустым") String fullName,
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Неверный формат email") String email,
        @NotNull(message = "Нужно указать groupId") Integer groupId) {
}
