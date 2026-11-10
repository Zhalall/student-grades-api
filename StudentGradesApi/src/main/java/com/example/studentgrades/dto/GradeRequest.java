package com.example.studentgrades.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GradeRequest(
        @NotNull(message = "Нужно указать studentId") Integer studentId,
        @NotNull(message = "Нужно указать subjectId") Integer subjectId,
        @NotNull(message = "Нужно указать typeId") Integer typeId,
        @NotNull(message = "Нужно указать оценку")
        @Min(value = 0, message = "Оценка не может быть меньше 0")
        @Max(value = 100, message = "Оценка не может быть больше 100") Integer grade,
        @NotNull(message = "Нужна дата в формате yyyy-MM-dd") LocalDate gradeDate) {
}
