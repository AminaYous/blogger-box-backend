package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.PostRequest;
import com.dauphine.blogger.dto.PostResponse;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
@Tag(name = "Post API", description = "Endpoints for managing posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all posts", description = "Returns the list of all posts, optionally filtered by title/content or category")
    public ResponseEntity<List<PostResponse>> getAll(
            @RequestParam(required = false) String value,
            @RequestParam(required = false) UUID categoryId) {
        if (categoryId != null) {
            return ResponseEntity.ok(service.getByCategoryId(categoryId));
        }
        return ResponseEntity.ok(service.getAll(value));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get post by ID", description = "Returns a single post by its unique identifier")
    public ResponseEntity<PostResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a post", description = "Creates a new post")
    public ResponseEntity<PostResponse> create(@RequestBody PostRequest request) {
        PostResponse response = service.create(request);
        return ResponseEntity.created(URI.create("/posts/" + response.getId())).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a post", description = "Updates an existing post by ID")
    public ResponseEntity<PostResponse> update(@PathVariable UUID id, @RequestBody PostRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post", description = "Deletes a post by ID")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
