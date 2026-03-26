package com.dauphine.blogger.services;

import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.dto.CategoryResponse;
import com.dauphine.blogger.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponse> getAll(String name);

    CategoryResponse getById(UUID id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(UUID id, CategoryRequest request);

    void delete(UUID id);

    Category getCategoryById(UUID id); // Helper for internal use if needed
}
