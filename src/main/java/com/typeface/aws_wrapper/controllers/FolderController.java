package com.typeface.aws_wrapper.controllers;

import com.typeface.aws_wrapper.service.dto.FolderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/typeface/v1/team/{teamId}/folders")
public class FolderController {

    @GetMapping
    public ResponseEntity<List<FolderResponse>> listFolders(
            @PathVariable Long teamId,
            @RequestParam(required = false) String rootFolderPath) {

        List<FolderResponse> folders = List.of(
                FolderResponse.builder()
                        .name("assets")
                        .path("team-" + teamId + "/assets")
                        .sizeBytes(204800)
                        .createdAt(Instant.parse("2024-01-01T00:00:00Z"))
                        .updatedAt(Instant.parse("2024-06-01T00:00:00Z"))
                        .build(),
                FolderResponse.builder()
                        .name("images")
                        .path("team-" + teamId + "/images")
                        .sizeBytes(1048576)
                        .createdAt(Instant.parse("2024-02-15T00:00:00Z"))
                        .updatedAt(Instant.parse("2024-07-10T00:00:00Z"))
                        .build(),
                FolderResponse.builder()
                        .name("documents")
                        .path("team-" + teamId + "/documents")
                        .sizeBytes(512000)
                        .createdAt(Instant.parse("2024-03-20T00:00:00Z"))
                        .updatedAt(Instant.parse("2024-08-05T00:00:00Z"))
                        .build()
        );

        return ResponseEntity.ok(folders);
    }
}
