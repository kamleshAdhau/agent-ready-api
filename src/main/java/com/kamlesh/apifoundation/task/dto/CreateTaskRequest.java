package com.kamlesh.apifoundation.task.dto;

import com.kamlesh.apifoundation.task.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskRequest(
        @NotBlank @Size(max = 150) String title,
        @Size(max = 2000) String description,
        @NotNull TaskPriority priority,
        @Size(max = 100) String assignee,
        @FutureOrPresent LocalDate dueDate
) {
}
