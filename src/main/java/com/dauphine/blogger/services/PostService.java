package com.dauphine.blogger.services;

import com.dauphine.blogger.dto.PostRequest;
import com.dauphine.blogger.dto.PostResponse;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponse> getAll(String value);

    List<PostResponse> getByCategoryId(UUID categoryId);

    PostResponse getById(UUID id);

    PostResponse create(PostRequest request);

    PostResponse update(UUID id, PostRequest request);

    void delete(UUID id);
}
