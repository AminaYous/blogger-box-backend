package com.dauphine.blogger.exceptions;

import java.util.UUID;

public class PostNotFoundByIdException extends RuntimeException {
    public PostNotFoundByIdException(UUID id) {
        super("Post with ID " + id + " not found.");
    }
}
