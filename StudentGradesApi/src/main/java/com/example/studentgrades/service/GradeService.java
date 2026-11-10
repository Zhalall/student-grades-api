package com.example.studentgrades.service;

import com.example.studentgrades.dto.GradeRequest;
import com.example.studentgrades.dto.GradeResponse;
import com.example.studentgrades.exception.BadRequestException;
import com.example.studentgrades.exception.NotFoundException;
import com.example.studentgrades.repository.GradeRepository;
import com.example.studentgrades.repository.LookupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    private final GradeRepository repo;
    private final LookupRepository lookup;

    public GradeService(GradeRepository repo, LookupRepository lookup) {
        this.repo = repo;
        this.lookup = lookup;
    }

    public List<GradeResponse> list(Integer studentId) {
        return repo.findAll(studentId);
    }

    public GradeResponse get(int id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Оценка с id " + id + " не найдена"));
    }

    public GradeResponse create(GradeRequest r) {
        check(r);
        return get(repo.insert(r));
    }

    public GradeResponse update(int id, GradeRequest r) {
        get(id);
        check(r);
        repo.update(id, r);
        return get(id);
    }

    public void delete(int id) {
        if (repo.delete(id) == 0) {
            throw new NotFoundException("Оценка с id " + id + " не найдена");
        }
    }

    private void check(GradeRequest r) {
        if (!lookup.exists("students", r.studentId())) {
            throw new BadRequestException("Студента с id " + r.studentId() + " нет");
        }
        if (!lookup.exists("subjects", r.subjectId())) {
            throw new BadRequestException("Предмета с id " + r.subjectId() + " нет");
        }
        if (!lookup.exists("grade_types", r.typeId())) {
            throw new BadRequestException("Типа оценки с id " + r.typeId() + " нет");
        }
    }
}
