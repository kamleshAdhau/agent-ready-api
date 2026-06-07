package com.kamlesh.apifoundation.task;

import com.kamlesh.apifoundation.common.exception.ResourceNotFoundException;
import com.kamlesh.apifoundation.task.dto.CreateTaskRequest;
import com.kamlesh.apifoundation.task.dto.TaskResponse;
import com.kamlesh.apifoundation.task.dto.UpdateTaskRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest request) {
        Task task = new Task(request.title(), request.description(), request.priority(), request.assignee(), request.dueDate());
        return TaskMapper.toResponse(repository.save(task));
    }

    @Transactional(readOnly = true)
    public TaskResponse get(UUID id) {
        return TaskMapper.toResponse(findTask(id));
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> search(TaskStatus status, TaskPriority priority, String assignee, LocalDate dueBefore, Pageable pageable) {
        Specification<Task> spec = Specification.allOf(
                TaskSpecifications.hasStatus(status),
                TaskSpecifications.hasPriority(priority),
                TaskSpecifications.assignedTo(assignee),
                TaskSpecifications.dueBefore(dueBefore)
        );
        return repository.findAll(spec, pageable).map(TaskMapper::toResponse);
    }

    @Transactional
    public TaskResponse update(UUID id, UpdateTaskRequest request) {
        Task task = findTask(id);
        task.update(request.title(), request.description(), request.priority(), request.status(), request.assignee(), request.dueDate());
        return TaskMapper.toResponse(task);
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findTask(id));
    }

    private Task findTask(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
    }
}
