package com.example.studentgrades.controller;

import com.example.studentgrades.dto.LookupItem;
import com.example.studentgrades.dto.SubjectResponse;
import com.example.studentgrades.repository.LookupRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Справочники", description = "Группы, предметы, типы оценок (нужны для id при создании)")
public class LookupController {
    private final LookupRepository lookup;

    public LookupController(LookupRepository lookup) {
        this.lookup = lookup;
    }

    @Operation(summary = "Список групп")
    @GetMapping("/groups")
    public List<LookupItem> groups() {
        return lookup.groups();
    }

    @Operation(summary = "Список предметов с преподавателями")
    @GetMapping("/subjects")
    public List<SubjectResponse> subjects() {
        return lookup.subjects();
    }

    @Operation(summary = "Типы оценок")
    @GetMapping("/grade-types")
    public List<LookupItem> gradeTypes() {
        return lookup.gradeTypes();
    }
}
