package com.example.studentgrades.controller;

import com.example.studentgrades.dto.StudentRequest;
import com.example.studentgrades.dto.StudentResponse;
import com.example.studentgrades.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Студенты", description = "CRUD и поиск студентов")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @Operation(summary = "Список всех студентов")
    @GetMapping
    public List<StudentResponse> list() {
        return service.list();
    }

    @Operation(summary = "Поиск по имени, email или группе")
    @GetMapping("/search")
    public List<StudentResponse> search(@RequestParam("q") String q) {
        return service.search(q);
    }

    @Operation(summary = "Студент по id")
    @GetMapping("/{id}")
    public StudentResponse get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @Operation(summary = "Добавить студента")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse create(@Valid @RequestBody StudentRequest request) {
        return service.create(request);
    }

    @Operation(summary = "Изменить студента")
    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable("id") int id, @Valid @RequestBody StudentRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Удалить студента (его оценки удаляются каскадом)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
