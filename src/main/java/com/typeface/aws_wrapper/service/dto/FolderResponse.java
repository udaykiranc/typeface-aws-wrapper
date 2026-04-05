package com.typeface.aws_wrapper.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class FolderResponse {
    private String name;
    private String path;
    private long sizeBytes;
    private Instant createdAt;
    private Instant updatedAt;
}
