package com.ecommerce.controller;

import com.ecommerce.model.AuditLog;
import com.ecommerce.repository.AuditLogRepository;
import com.ecommerce.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;
    private final AuditService auditService;

    public AuditLogController(AuditLogRepository auditLogRepository, AuditService auditService) {
        this.auditLogRepository = auditLogRepository;
        this.auditService = auditService;
    }

    @GetMapping
    public List<AuditLog> getAllInventories() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        auditService.logAction("listed all audit logs", "Audit", null);
        return auditLogs;
    }
}
