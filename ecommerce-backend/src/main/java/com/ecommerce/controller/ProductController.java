package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final AuditService auditService;

    public ProductController(ProductRepository productRepository, AuditService auditService) {
        this.productRepository = productRepository;
        this.auditService = auditService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        auditService.logAction("listed all products", "Product", null);
        return products;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        Product saved = productRepository.save(product);
        auditService.logAction("created product", "Product", saved.getId());
        return saved;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        auditService.logAction("viewed product", "Product", id);
        return product;
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        Product updated = productRepository.save(product);
        auditService.logAction("updated product", "Product", id);
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        auditService.logAction("deleted product", "Product", id);
    }

    @GetMapping("/active")
    public List<Product> getActiveProducts() {
        List<Product> products = productRepository.findByActiveTrue();
        auditService.logAction("listed active products", "Product", null);
        return products;
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(required = false) String query,
                                        @RequestParam(required = false) Boolean active) {
        List<Product> products;

        if (query != null && active != null) {
            products = productRepository.findByNameContainingIgnoreCaseAndActive(query, active);
        } else if (query != null) {
            products = productRepository.findByNameContainingIgnoreCase(query);
        } else if (active != null) {
            products = productRepository.findByActive(active);
        } else {
            products = productRepository.findAll();
        }

        auditService.logAction("searched products with query: " + query + " and active: " + active, "Product", null);
        return products;
    }
}
