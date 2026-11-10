package com.example.studentgrades.dto;

public record AverageRow(String groupName, String studentName, String subjectName,
                         String teacherName, Long gradesCount, Double averageGrade) {
}
