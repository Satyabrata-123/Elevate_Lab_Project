package com.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "job", "applicant" })
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;
    @NotBlank(message = "Cover letter is required")
    @Column(columnDefinition = "TEXT")
    private String coverLetter;
    private String resume;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;
    @Column(name = "applied_at")
    private LocalDateTime appliedAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private String employerNotes;
    private String applicantNotes;

    public enum ApplicationStatus {
        PENDING("Pending"),
        REVIEWING("Under Review"),
        SHORTLISTED("Shortlisted"),
        INTERVIEW_SCHEDULED("Interview Scheduled"),
        REJECTED("Rejected"),
        ACCEPTED("Accepted"),
        WITHDRAWN("Withdrawn");

        private final String displayName;

        ApplicationStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}