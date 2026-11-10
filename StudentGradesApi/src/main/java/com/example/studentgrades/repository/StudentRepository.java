package com.example.studentgrades.repository;

import com.example.studentgrades.dto.StudentRequest;
import com.example.studentgrades.dto.StudentResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    private static final String BASE =
            "SELECT s.id, s.full_name, s.email, s.group_id, g.name AS group_name " +
            "FROM students s JOIN student_groups g ON g.id = s.group_id ";

    private static final RowMapper<StudentResponse> MAPPER = (rs, i) -> new StudentResponse(
            rs.getInt("id"), rs.getString("full_name"), rs.getString("email"),
            rs.getInt("group_id"), rs.getString("group_name"));

    private final JdbcTemplate jdbc;

    public StudentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<StudentResponse> findAll() {
        return jdbc.query(BASE + "ORDER BY s.id", MAPPER);
    }

    public List<StudentResponse> search(String q) {
        String like = "%" + q.trim().toLowerCase() + "%";
        return jdbc.query(BASE +
                        "WHERE LOWER(s.full_name) LIKE ? OR LOWER(s.email) LIKE ? OR LOWER(g.name) LIKE ? " +
                        "ORDER BY s.id",
                MAPPER, like, like, like);
    }

    public Optional<StudentResponse> findById(int id) {
        return jdbc.query(BASE + "WHERE s.id = ?", MAPPER, id).stream().findFirst();
    }

    public int insert(StudentRequest r) {
        KeyHolder keys = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students(full_name, email, group_id) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, r.fullName().trim());
            ps.setString(2, r.email().trim());
            ps.setInt(3, r.groupId());
            return ps;
        }, keys);
        return keys.getKey().intValue();
    }

    public int update(int id, StudentRequest r) {
        return jdbc.update("UPDATE students SET full_name = ?, email = ?, group_id = ? WHERE id = ?",
                r.fullName().trim(), r.email().trim(), r.groupId(), id);
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM students WHERE id = ?", id);
    }
}
