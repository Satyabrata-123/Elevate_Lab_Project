package com.jobportal.repository;

import com.jobportal.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByEmployerId(Long employerId);

    List<Job> findByActiveTrue();

    Page<Job> findByActiveTrue(Pageable pageable);

    List<Job> findByCategory(String category);

    List<Job> findByLocation(String location);

    List<Job> findByJobType(String jobType);

    List<Job> findByExperienceLevel(String experienceLevel);

    List<Job> findBySalaryBetween(BigDecimal minSalary, BigDecimal maxSalary);

    @Query("SELECT j FROM Job j WHERE j.active = true AND (j.title LIKE %:keyword% OR j.description LIKE %:keyword% OR j.requirements LIKE %:keyword% OR j.location LIKE %:keyword%)")
    List<Job> searchJobs(@Param("keyword") String keyword);

    @Query("SELECT j FROM Job j WHERE j.active = true AND j.expiresAt > :now")
    List<Job> findActiveJobsNotExpired(@Param("now") LocalDateTime now);

    @Query("SELECT j FROM Job j WHERE j.active = true AND j.expiresAt > :now AND (j.title LIKE %:keyword% OR j.description LIKE %:keyword% OR j.requirements LIKE %:keyword% OR j.location LIKE %:keyword%)")
    Page<Job> searchActiveJobs(@Param("keyword") String keyword, @Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.active = true AND j.expiresAt > :now AND (:category IS NULL OR j.category = :category) AND (:location IS NULL OR j.location = :location) AND (:jobType IS NULL OR j.jobType = :jobType) AND (:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel) AND (:minSalary IS NULL OR j.salary >= :minSalary) AND (:maxSalary IS NULL OR j.salary <= :maxSalary)")
    Page<Job> findJobsWithFilters(@Param("now") LocalDateTime now, @Param("category") String category,
            @Param("location") String location, @Param("jobType") String jobType,
            @Param("experienceLevel") String experienceLevel, @Param("minSalary") BigDecimal minSalary,
            @Param("maxSalary") BigDecimal maxSalary, Pageable pageable);

    @Query("SELECT DISTINCT j.category FROM Job j WHERE j.active = true")
    List<String> findAllCategories();

    @Query("SELECT DISTINCT j.location FROM Job j WHERE j.active = true")
    List<String> findAllLocations();

    @Query("SELECT DISTINCT j.jobType FROM Job j WHERE j.active = true")
    List<String> findAllJobTypes();

    @Query("SELECT DISTINCT j.experienceLevel FROM Job j WHERE j.active = true")
    List<String> findAllExperienceLevels();
}