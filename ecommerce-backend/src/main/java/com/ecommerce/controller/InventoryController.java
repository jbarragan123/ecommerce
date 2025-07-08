package com.ecommerce.controller;

import com.ecommerce.model.Inventory;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryRepository inventoryRepository;
    private final AuditService auditService;

    public InventoryController(InventoryRepository inventoryRepository, AuditService auditService) {
        this.inventoryRepository = inventoryRepository;
        this.auditService = auditService;
    }

    @GetMapping
    public List<Inventory> getAllInventories(@RequestHeader("X-User-Id") Long userId) {
        List<Inventory> inventories = inventoryRepository.findAll();
        auditService.logAction("User " + userId + " listed all inventories", "Inventory", null);
        return inventories;
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory, @RequestHeader("X-User-Id") Long userId) {
        Inventory saved = inventoryRepository.save(inventory);
        auditService.logAction("User " + userId + " created inventory", "Inventory", saved.getId());
        return saved;
    }

    @GetMapping("/{id}")
    public Inventory getInventory(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        auditService.logAction("User " + userId + " viewed inventory", "Inventory", id);
        return inventory;
    }

    @PutMapping("/{id}")
    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventory, @RequestHeader("X-User-Id") Long userId) {
        inventory.setId(id);
        Inventory updated = inventoryRepository.save(inventory);
        auditService.logAction("User " + userId + " updated inventory", "Inventory", id);
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        inventoryRepository.deleteById(id);
        auditService.logAction("User " + userId + " deleted inventory", "Inventory", id);
    }
}