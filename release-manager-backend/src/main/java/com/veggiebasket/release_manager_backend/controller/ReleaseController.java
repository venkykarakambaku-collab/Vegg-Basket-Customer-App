package com.veggiebasket.release_manager_backend.controller;

import com.veggiebasket.release_manager_backend.dto.ReleaseRequest;
import com.veggiebasket.release_manager_backend.dto.ReleaseResponse;
import com.veggiebasket.release_manager_backend.service.ReleaseService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/releases")
public class ReleaseController {

    private final ReleaseService releaseService;

    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    // Public endpoint - GitHub Pages can use this
    @GetMapping("/latest")
    public ResponseEntity<ReleaseResponse> getLatestRelease() {
        return ResponseEntity.ok(
                releaseService.getLatestPublishedRelease()
        );
    }

    // Public endpoint - release history
    @GetMapping
    public ResponseEntity<List<ReleaseResponse>> getAllReleases() {
        return ResponseEntity.ok(
                releaseService.getAllReleases()
        );
    }

    // Developer only
    @PreAuthorize("hasRole('DEVELOPER')")
    @PostMapping
    public ResponseEntity<ReleaseResponse> createRelease(
            @Valid @RequestBody ReleaseRequest request
    ) {
        return ResponseEntity.ok(
                releaseService.createRelease(request)
        );
    }

    // Developer only
    @PreAuthorize("hasRole('DEVELOPER')")
    @PutMapping("/{id}")
    public ResponseEntity<ReleaseResponse> updateRelease(
            @PathVariable Long id,
            @Valid @RequestBody ReleaseRequest request
    ) {
        return ResponseEntity.ok(
                releaseService.updateRelease(id, request)
        );
    }

    // Developer only
    @PreAuthorize("hasRole('DEVELOPER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelease(
            @PathVariable Long id
    ) {
        releaseService.deleteRelease(id);

        return ResponseEntity.noContent().build();
    }

    // Developer only
    @PreAuthorize("hasRole('DEVELOPER')")
    @PatchMapping("/{id}/publish")
    public ResponseEntity<ReleaseResponse> publishRelease(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                releaseService.publishRelease(id)
        );
    }
}