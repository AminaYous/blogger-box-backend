package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.dto.CategoryResponse;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repositories.CategoryRepository;
import com.dauphine.blogger.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryResponse> getAll() {
        return getAll(null);
    }

    @Override
    public List<CategoryResponse> getAll(String name) {
        List<Category> categories = (name == null || name.isEmpty())
                ? repository.findAll()
                : repository.findAllByName(name);
        return categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(UUID id) {
        Category category = getCategoryById(id);
        return new CategoryResponse(category.getId(), category.getName());
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = new Category(request.getName());
        category = repository.save(category);
        return new CategoryResponse(category.getId(), category.getName());
    }

    @Override
    public CategoryResponse update(UUID id, CategoryRequest request) {
        Category category = getCategoryById(id);
        if (category != null) {
            category.setName(request.getName());
            category = repository.save(category);
            return new CategoryResponse(category.getId(), category.getName());
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Category getCategoryById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new com.dauphine.blogger.exceptions.CategoryNotFoundByIdException(id));
    }
}
