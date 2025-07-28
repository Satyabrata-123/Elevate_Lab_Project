package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private UserService userService;
    
    public Application applyForJob(Long jobId, Long applicantId, String coverLetter, String resume) {
        // Check if already applied
        if (applicationRepository.existsByJobIdAndApplicantId(jobId, applicantId)) {
            throw new RuntimeException("You have already applied for this job");
        }
        
        Job job = jobService.getJobById(jobId);
        User applicant = userService.getUserById(applicantId);
        
        // Check if job is active
        if (!job.isActive()) {
            throw new RuntimeException("This job is not active");
        }
        
        // Check if job is expired
        if (job.isExpired()) {
            throw new RuntimeException("This job has expired");
        }
        
        Application application = new Application();
        application.setJob(job);
        application.setApplicant(applicant);
        application.setCoverLetter(coverLetter);
        application.setResume(resume);
        
        return applicationRepository.save(application);
    }
    
    public Application updateApplicationStatus(Long applicationId, Application.ApplicationStatus status, Long employerId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        // Verify the application belongs to the employer
        if (!application.getJob().getEmployer().getId().equals(employerId)) {
            throw new RuntimeException("You can only update applications for your own jobs");
        }
        
        application.setStatus(status);
        return applicationRepository.save(application);
    }
    
    public Application addEmployerNotes(Long applicationId, String notes, Long employerId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        // Verify the application belongs to the employer
        if (!application.getJob().getEmployer().getId().equals(employerId)) {
            throw new RuntimeException("You can only add notes to applications for your own jobs");
        }
        
        application.setEmployerNotes(notes);
        return applicationRepository.save(application);
    }
    
    public Application addApplicantNotes(Long applicationId, String notes, Long applicantId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        // Verify the application belongs to the applicant
        if (!application.getApplicant().getId().equals(applicantId)) {
            throw new RuntimeException("You can only add notes to your own applications");
        }
        
        application.setApplicantNotes(notes);
        return applicationRepository.save(application);
    }
    
    public Application withdrawApplication(Long applicationId, Long applicantId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        // Verify the application belongs to the applicant
        if (!application.getApplicant().getId().equals(applicantId)) {
            throw new RuntimeException("You can only withdraw your own applications");
        }
        
        application.setStatus(Application.ApplicationStatus.WITHDRAWN);
        return applicationRepository.save(application);
    }
    
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
    
    public List<Application> getApplicationsByApplicant(Long applicantId) {
        return applicationRepository.findByApplicantId(applicantId);
    }
    
    public List<Application> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }
    
    public List<Application> getApplicationsByEmployer(Long employerId) {
        return applicationRepository.findByJobEmployerId(employerId);
    }
    
    public List<Application> getApplicationsByStatus(Application.ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }
    
    public List<Application> getApplicationsByApplicantAndStatus(Long applicantId, Application.ApplicationStatus status) {
        return applicationRepository.findByApplicantIdAndStatus(applicantId, status);
    }
    
    public List<Application> getApplicationsByJobAndStatus(Long jobId, Application.ApplicationStatus status) {
        return applicationRepository.findByJobIdAndStatus(jobId, status);
    }
    
    public List<Application> getApplicationsByEmployerAndStatus(Long employerId, Application.ApplicationStatus status) {
        return applicationRepository.findByEmployerIdAndStatus(employerId, status);
    }
    
    public boolean hasAppliedForJob(Long jobId, Long applicantId) {
        return applicationRepository.existsByJobIdAndApplicantId(jobId, applicantId);
    }
    
    public long getApplicationCountForJob(Long jobId) {
        return applicationRepository.countByJobId(jobId);
    }
    
    public long getApplicationCountForApplicant(Long applicantId) {
        return applicationRepository.countByApplicantId(applicantId);
    }
    
    public long getApplicationCountForEmployer(Long employerId) {
        return applicationRepository.countByEmployerId(employerId);
    }
    
    public void deleteApplication(Long applicationId, Long applicantId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        // Verify the application belongs to the applicant
        if (!application.getApplicant().getId().equals(applicantId)) {
            throw new RuntimeException("You can only delete your own applications");
        }
        
        applicationRepository.delete(application);
    }
} 