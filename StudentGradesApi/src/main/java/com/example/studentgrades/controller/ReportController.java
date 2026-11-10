package com.example.studentgrades.controller;

import com.example.studentgrades.dto.AverageRow;
import com.example.studentgrades.dto.DebtorRow;
import com.example.studentgrades.exception.BadRequestException;
import com.example.studentgrades.repository.ReportRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Отчёты", description = "JOIN + агрегаты")
public class ReportController {
    private final ReportRepository reports;

    public ReportController(ReportRepository reports) {
        this.reports = reports;
    }

    @Operation(summary = "Средний балл по студентам и предметам (JOIN 5 таблиц, COUNT, AVG, GROUP BY)")
    @GetMapping("/averages")
    public List<AverageRow> averages() {
        return reports.averages();
    }

    @Operation(summary = "Должники: средний балл ниже порога (HAVING)")
    @GetMapping("/debtors")
    public List<DebtorRow> debtors(@RequestParam(value = "threshold", defaultValue = "50") double threshold) {
        if (threshold < 0 || threshold > 100) {
            throw new BadRequestException("threshold должен быть от 0 до 100");
        }
        return reports.debtors(threshold);
    }
}
