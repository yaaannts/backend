package com.customs.controller;

import com.customs.dto.SubmitResponse;
import com.customs.model.Declaration;
import com.customs.service.DeclarationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DeclarationController {

    private static final Logger log = LoggerFactory.getLogger(DeclarationController.class);

    private final DeclarationService declarationService;

    public DeclarationController(DeclarationService declarationService) {
        this.declarationService = declarationService;
    }

    @PostMapping("/submit")
    public ResponseEntity<SubmitResponse> submitDeclaration(@Valid @RequestBody Declaration declaration) {
        log.info("Received declaration submission request");

        SubmitResponse response = declarationService.submitDeclaration(declaration);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/declaration/{declNo}")
    public ResponseEntity<?> getDeclaration(@PathVariable String declNo) {
        return declarationService.findByDeclNo(declNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Global exception handler for validation errors
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", errorMessage
        ));
    }
}