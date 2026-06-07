package com.kamlesh.apifoundation.task;

import com.kamlesh.apifoundation.common.api.ApiResponse;
import com.kamlesh.apifoundation.common.api.PageResponse;
import com.kamlesh.apifoundation.common.web.RequestContext;
import com.kamlesh.apifoundation.task.dto.CreateTaskRequest;
import com.kamlesh.apifoundation.task.dto.TaskResponse;
import com.kamlesh.apifoundation.task.dto.UpdateTaskRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@Tag(name = "Tasks", description = "Task management APIs")
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @Operation(summary = "Create task")
    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> create(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Task created", response, RequestContext.correlationId()));
    }

    @Operation(summary = "Get task by id")
    @GetMapping("/{id}")
    public ApiResponse<TaskResponse> get(@PathVariable UUID id) {
        return ApiResponse.success("Task fetched", service.get(id), RequestContext.correlationId());
    }

    @Operation(summary = "Search tasks")
    @GetMapping
    public ApiResponse<PageResponse<TaskResponse>> search(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) LocalDate dueBefore,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        String[] sortParts = sort.split(",");
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParts[0]));
        PageResponse<TaskResponse> response = PageResponse.from(service.search(status, priority, assignee, dueBefore, pageable));
        return ApiResponse.success("Tasks fetched", response, RequestContext.correlationId());
    }

    @Operation(summary = "Update task")
    @PutMapping("/{id}")
    public ApiResponse<TaskResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateTaskRequest request) {
        return ApiResponse.success("Task updated", service.update(id, request), RequestContext.correlationId());
    }

    @Operation(summary = "Delete task")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
