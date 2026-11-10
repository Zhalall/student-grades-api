package com.example.studentgrades.repository;

import com.example.studentgrades.dto.LookupItem;
import com.example.studentgrades.dto.SubjectResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LookupRepository {
    private final JdbcTemplate jdbc;

    public LookupRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<LookupItem> groups() {
        return jdbc.query("SELECT id, name FROM student_groups ORDER BY id",
                (rs, i) -> new LookupItem(rs.getInt("id"), rs.getString("name")));
    }

    public List<LookupItem> gradeTypes() {
        return jdbc.query("SELECT id, name FROM grade_types ORDER BY id",
                (rs, i) -> new LookupItem(rs.getInt("id"), rs.getString("name")));
    }

    public List<SubjectResponse> subjects() {
        return jdbc.query(
                "SELECT s.id, s.name, s.credits, t.full_name AS teacher_name " +
                "FROM subjects s JOIN teachers t ON t.id = s.teacher_id ORDER BY s.id",
                (rs, i) -> new SubjectResponse(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("credits"), rs.getString("teacher_name")));
    }

    /** Таблица передаётся только из кода сервисов (константы), не от пользователя. */
    public boolean exists(String table, int id) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM " + table + " WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }
}
