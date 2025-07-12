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
    public List<Inventory> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        auditService.logAction("listed all inventories", "Inventory", null);
        return inventories;
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        Inventory saved = inventoryRepository.save(inventory);
        auditService.logAction("created inventory", "Inventory", saved.getId());
        return saved;
    }

    @GetMapping("/{id}")
    public Inventory getInventory(@PathVariable Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        auditService.logAction("viewed inventory", "Inventory", id);
        return inventory;
    }

    @PutMapping("/{id}")
    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        inventory.setId(id);
        Inventory updated = inventoryRepository.save(inventory);
        auditService.logAction("updated inventory", "Inventory", id);
        return updated;
    }

    // @DeleteMapping("/{id}")
    // public void deleteInventory(@PathVariable Long id) {
    //     inventoryRepository.deleteById(id);
    //     auditService.logAction("deleted inventory", "Inventory", id);
    // }

    @PutMapping("/{id}/deactivate")
    public void deactivateInventory(@PathVariable Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow();
        inventory.setActive(false);
        inventoryRepository.save(inventory);
        auditService.logAction("deactivated inventory", "Inventory", id);
    }

    @GetMapping("/{qty}")
    public Inventory getQtyInventory(@PathVariable Long qty) {
        // Metodo mal SOLO PRUEBA NO USAR
        Inventory inventory = inventoryRepository.findById(qty).orElse(null);
        return inventory;
    }
}
