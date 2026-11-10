package com.example.studentgrades.service;

import com.example.studentgrades.dto.StudentRequest;
import com.example.studentgrades.dto.StudentResponse;
import com.example.studentgrades.exception.BadRequestException;
import com.example.studentgrades.exception.NotFoundException;
import com.example.studentgrades.repository.LookupRepository;
import com.example.studentgrades.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    private final LookupRepository lookup;

    public StudentService(StudentRepository repo, LookupRepository lookup) {
        this.repo = repo;
        this.lookup = lookup;
    }

    public List<StudentResponse> list() {
        return repo.findAll();
    }

    public List<StudentResponse> search(String q) {
        if (q == null || q.isBlank()) {
            throw new BadRequestException("Параметр q не может быть пустым");
        }
        return repo.search(q);
    }

    public StudentResponse get(int id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Студент с id " + id + " не найден"));
    }

    public StudentResponse create(StudentRequest r) {
        checkGroup(r);
        return get(repo.insert(r));
    }

    public StudentResponse update(int id, StudentRequest r) {
        get(id);
        checkGroup(r);
        repo.update(id, r);
        return get(id);
    }

    public void delete(int id) {
        if (repo.delete(id) == 0) {
            throw new NotFoundException("Студент с id " + id + " не найден");
        }
    }

    private void checkGroup(StudentRequest r) {
        if (!lookup.exists("student_groups", r.groupId())) {
            throw new BadRequestException("Группы с id " + r.groupId() + " нет");
        }
    }
}
