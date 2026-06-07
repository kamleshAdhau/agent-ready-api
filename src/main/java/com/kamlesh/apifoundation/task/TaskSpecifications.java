package com.kamlesh.apifoundation.task;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class TaskSpecifications {
    private TaskSpecifications() {
    }

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, cb) -> priority == null ? cb.conjunction() : cb.equal(root.get("priority"), priority);
    }

    public static Specification<Task> assignedTo(String assignee) {
        return (root, query, cb) -> assignee == null || assignee.isBlank()
                ? cb.conjunction()
                : cb.equal(cb.lower(root.get("assignee")), assignee.toLowerCase());
    }

    public static Specification<Task> dueBefore(LocalDate dueBefore) {
        return (root, query, cb) -> dueBefore == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("dueDate"), dueBefore);
    }
}
