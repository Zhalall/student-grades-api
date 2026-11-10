package com.example.studentgrades.repository;

import com.example.studentgrades.dto.AverageRow;
import com.example.studentgrades.dto.DebtorRow;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepository {
    private final JdbcTemplate jdbc;

    public ReportRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** JOIN 5 таблиц + COUNT + AVG + GROUP BY. */
    public List<AverageRow> averages() {
        return jdbc.query(
                "SELECT g.name AS group_name, s.full_name AS student_name, sub.name AS subject_name, " +
                "t.full_name AS teacher_name, COUNT(gr.id) AS grades_count, " +
                "ROUND(AVG(CAST(gr.grade AS DOUBLE PRECISION)), 1) AS avg_grade " +
                "FROM grades gr " +
                "JOIN students s ON s.id = gr.student_id " +
                "JOIN student_groups g ON g.id = s.group_id " +
                "JOIN subjects sub ON sub.id = gr.subject_id " +
                "JOIN teachers t ON t.id = sub.teacher_id " +
                "GROUP BY g.name, s.id, s.full_name, sub.id, sub.name, t.full_name " +
                "ORDER BY g.name, s.full_name, sub.name",
                (rs, i) -> new AverageRow(rs.getString("group_name"), rs.getString("student_name"),
                        rs.getString("subject_name"), rs.getString("teacher_name"),
                        rs.getLong("grades_count"), rs.getDouble("avg_grade")));
    }

    /** Студенты, у которых средний балл ниже порога (GROUP BY + HAVING). */
    public List<DebtorRow> debtors(double threshold) {
        return jdbc.query(
                "SELECT s.full_name AS student_name, g.name AS group_name, " +
                "COUNT(gr.id) AS grades_count, " +
                "ROUND(AVG(CAST(gr.grade AS DOUBLE PRECISION)), 1) AS avg_grade " +
                "FROM students s " +
                "JOIN student_groups g ON g.id = s.group_id " +
                "JOIN grades gr ON gr.student_id = s.id " +
                "GROUP BY s.id, s.full_name, g.name " +
                "HAVING AVG(CAST(gr.grade AS DOUBLE PRECISION)) < ? " +
                "ORDER BY avg_grade",
                (rs, i) -> new DebtorRow(rs.getString("student_name"), rs.getString("group_name"),
                        rs.getLong("grades_count"), rs.getDouble("avg_grade")),
                threshold);
    }
}
