package com.example.studentgrades.repository;

import com.example.studentgrades.dto.GradeRequest;
import com.example.studentgrades.dto.GradeResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GradeRepository {
    private static final String BASE =
            "SELECT gr.id, s.id AS student_id, s.full_name AS student_name, " +
            "sub.id AS subject_id, sub.name AS subject_name, " +
            "t.id AS type_id, t.name AS type_name, gr.grade, gr.grade_date " +
            "FROM grades gr " +
            "JOIN students s ON s.id = gr.student_id " +
            "JOIN subjects sub ON sub.id = gr.subject_id " +
            "JOIN grade_types t ON t.id = gr.type_id ";

    private static final RowMapper<GradeResponse> MAPPER = (rs, i) -> new GradeResponse(
            rs.getInt("id"), rs.getInt("student_id"), rs.getString("student_name"),
            rs.getInt("subject_id"), rs.getString("subject_name"),
            rs.getInt("type_id"), rs.getString("type_name"),
            rs.getInt("grade"), rs.getDate("grade_date").toLocalDate());

    private final JdbcTemplate jdbc;

    public GradeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<GradeResponse> findAll(Integer studentId) {
        if (studentId == null) {
            return jdbc.query(BASE + "ORDER BY gr.id", MAPPER);
        }
        return jdbc.query(BASE + "WHERE gr.student_id = ? ORDER BY gr.id", MAPPER, studentId);
    }

    public Optional<GradeResponse> findById(int id) {
        return jdbc.query(BASE + "WHERE gr.id = ?", MAPPER, id).stream().findFirst();
    }

    public int insert(GradeRequest r) {
        KeyHolder keys = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO grades(student_id, subject_id, type_id, grade, grade_date) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, r.studentId());
            ps.setInt(2, r.subjectId());
            ps.setInt(3, r.typeId());
            ps.setInt(4, r.grade());
            ps.setDate(5, Date.valueOf(r.gradeDate()));
            return ps;
        }, keys);
        return keys.getKey().intValue();
    }

    public int update(int id, GradeRequest r) {
        return jdbc.update(
                "UPDATE grades SET student_id = ?, subject_id = ?, type_id = ?, grade = ?, grade_date = ? WHERE id = ?",
                r.studentId(), r.subjectId(), r.typeId(), r.grade(), Date.valueOf(r.gradeDate()), id);
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM grades WHERE id = ?", id);
    }
}
