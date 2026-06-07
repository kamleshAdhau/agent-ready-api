package com.kamlesh.apifoundation.task.dto;

import com.kamlesh.apifoundation.task.TaskPriority;
import com.kamlesh.apifoundation.task.TaskStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskPriority priority,
        TaskStatus status,
        String assignee,
        LocalDate dueDate,
        Instant createdAt,
        Instant updatedAt
) {
}
