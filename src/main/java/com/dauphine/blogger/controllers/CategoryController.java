package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.dto.CategoryResponse;
import com.dauphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
@Tag(name = "Category API", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Returns the list of all categories, optionally filtered by name")
    public ResponseEntity<List<CategoryResponse>> getAll(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(service.getAll(name));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Returns a single category by its unique identifier")
    public ResponseEntity<CategoryResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a category", description = "Creates a new category with the provided name")
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
        CategoryResponse response = service.create(request);
        return ResponseEntity.created(URI.create("/categories/" + response.getId())).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category", description = "Updates an existing category by ID")
    public ResponseEntity<CategoryResponse> update(@PathVariable UUID id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Deletes a category by ID")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
