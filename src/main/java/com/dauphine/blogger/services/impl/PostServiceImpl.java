package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.dto.PostRequest;
import com.dauphine.blogger.dto.PostResponse;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repositories.CategoryRepository;
import com.dauphine.blogger.repositories.PostRepository;
import com.dauphine.blogger.services.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<PostResponse> getAll(String value) {
        List<Post> posts = (value == null || value.isEmpty())
                ? repository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"))
                : repository.findAllByTitleOrContent(value);
        return posts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getByCategoryId(UUID categoryId) {
        return repository.findAll().stream()
                .filter(post -> post.getCategory() != null && post.getCategory().getId().equals(categoryId))
                .sorted((p1, p2) -> p2.getCreatedDate().compareTo(p1.getCreatedDate()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse getById(UUID id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new com.dauphine.blogger.exceptions.PostNotFoundByIdException(id));
    }

    @Override
    public PostResponse create(PostRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new com.dauphine.blogger.exceptions.CategoryNotFoundByIdException(
                        request.getCategoryId()));
        Post post = new Post(
                null,
                request.getTitle(),
                request.getContent(),
                category);
        post = repository.save(post);
        return mapToResponse(post);
    }

    @Override
    public PostResponse update(UUID id, PostRequest request) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new com.dauphine.blogger.exceptions.PostNotFoundByIdException(id));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new com.dauphine.blogger.exceptions.CategoryNotFoundByIdException(
                        request.getCategoryId()));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(category);
        post = repository.save(post);
        return mapToResponse(post);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new com.dauphine.blogger.exceptions.PostNotFoundByIdException(id);
        }
        repository.deleteById(id);
    }

    private PostResponse mapToResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedDate(),
                post.getCategory());
    }
}
