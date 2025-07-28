package com.jobportal.repository;

import com.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByApplicantId(Long applicantId);

    List<Application> findByJobId(Long jobId);

    List<Application> findByJobEmployerId(Long employerId);

    List<Application> findByStatus(Application.ApplicationStatus status);

    List<Application> findByApplicantIdAndStatus(Long applicantId, Application.ApplicationStatus status);

    List<Application> findByJobIdAndStatus(Long jobId, Application.ApplicationStatus status);

    @Query("SELECT a FROM Application a WHERE a.job.employer.id = :employerId AND a.status = :status")
    List<Application> findByEmployerIdAndStatus(@Param("employerId") Long employerId,
            @Param("status") Application.ApplicationStatus status);

    Optional<Application> findByJobIdAndApplicantId(Long jobId, Long applicantId);

    boolean existsByJobIdAndApplicantId(Long jobId, Long applicantId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.id = :jobId")
    long countByJobId(@Param("jobId") Long jobId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.applicant.id = :applicantId")
    long countByApplicantId(@Param("applicantId") Long applicantId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.employer.id = :employerId")
    long countByEmployerId(@Param("employerId") Long employerId);
}