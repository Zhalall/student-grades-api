package com.example.studentgrades.controller;

import com.example.studentgrades.dto.GradeRequest;
import com.example.studentgrades.dto.GradeResponse;
import com.example.studentgrades.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@Tag(name = "Оценки", description = "CRUD оценок")
public class GradeController {
    private final GradeService service;

    public GradeController(GradeService service) {
        this.service = service;
    }

    @Operation(summary = "Список оценок (можно отфильтровать по studentId)")
    @GetMapping
    public List<GradeResponse> list(@RequestParam(value = "studentId", required = false) Integer studentId) {
        return service.list(studentId);
    }

    @Operation(summary = "Оценка по id")
    @GetMapping("/{id}")
    public GradeResponse get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @Operation(summary = "Добавить оценку")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GradeResponse create(@Valid @RequestBody GradeRequest request) {
        return service.create(request);
    }

    @Operation(summary = "Изменить оценку")
    @PutMapping("/{id}")
    public GradeResponse update(@PathVariable("id") int id, @Valid @RequestBody GradeRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Удалить оценку")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
