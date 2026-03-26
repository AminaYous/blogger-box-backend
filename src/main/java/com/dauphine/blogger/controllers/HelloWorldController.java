package com.dauphine.blogger.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello World API", description = "Endpoints for Hello World greetings")
public class HelloWorldController {

    @GetMapping("/hello-world")
    @Operation(summary = "Get Hello World", description = "Returns 'Hello world!' string")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/hello")
    @Operation(summary = "Get Hello with Param", description = "Returns a greeting with the provided name query parameter")
    public String hello(@Parameter(description = "Name to greet") @RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping("/hello/{name}")
    @Operation(summary = "Get Hello with Path Param", description = "Returns a greeting with the provided name path variable")
    public String helloByName(@Parameter(description = "Name to greet") @PathVariable String name) {
        return "Hello " + name;
    }
}
