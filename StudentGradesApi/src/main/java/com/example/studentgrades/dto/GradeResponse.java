package com.example.studentgrades.dto;

import java.time.LocalDate;

public record GradeResponse(Integer id, Integer studentId, String studentName,
                            Integer subjectId, String subjectName,
                            Integer typeId, String typeName,
                            Integer grade, LocalDate gradeDate) {
}
