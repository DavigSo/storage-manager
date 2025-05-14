package com.example.storage_manager.controller;

import com.example.storage_manager.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ReportService reportService;

    public DashboardController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {

        return ResponseEntity.ok(reportService.getStats());
    }

    @GetMapping("/reports/csv")
    public ResponseEntity<byte[]> getCsvReport() {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=report.csv")
                .body(reportService.generateCsvReport());
    }

    @GetMapping("/reports/pdf")
    public ResponseEntity<byte[]> getPdfReport() {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=report.pdf")
                .body(reportService.generatePdfReport());
    }
}