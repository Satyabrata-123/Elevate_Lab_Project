package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public String listJobs(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<Job> jobsPage = jobService.getActiveJobsPaginated(page, size);
        model.addAttribute("jobs", jobsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobsPage.getTotalPages());
        model.addAttribute("totalItems", jobsPage.getTotalElements());

        // Add filter options
        model.addAttribute("categories", jobService.getAllCategories());
        model.addAttribute("locations", jobService.getAllLocations());
        model.addAttribute("jobTypes", jobService.getAllJobTypes());
        model.addAttribute("experienceLevels", jobService.getAllExperienceLevels());

        return "jobs/list";
    }

    @GetMapping("/search")
    public String searchJobs(@RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<Job> jobsPage = jobService.searchActiveJobs(keyword, page, size);
        model.addAttribute("jobs", jobsPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobsPage.getTotalPages());
        model.addAttribute("totalItems", jobsPage.getTotalElements());

        return "jobs/search";
    }

    @GetMapping("/filter")
    public String filterJobs(@RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<Job> jobsPage = jobService.getJobsWithFilters(category, location, jobType,
                experienceLevel, minSalary, maxSalary, page, size);
        model.addAttribute("jobs", jobsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobsPage.getTotalPages());
        model.addAttribute("totalItems", jobsPage.getTotalElements());

        // Add filter values back to model
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedLocation", location);
        model.addAttribute("selectedJobType", jobType);
        model.addAttribute("selectedExperienceLevel", experienceLevel);
        model.addAttribute("minSalary", minSalary);
        model.addAttribute("maxSalary", maxSalary);

        // Add filter options
        model.addAttribute("categories", jobService.getAllCategories());
        model.addAttribute("locations", jobService.getAllLocations());
        model.addAttribute("jobTypes", jobService.getAllJobTypes());
        model.addAttribute("experienceLevels", jobService.getAllExperienceLevels());

        return "jobs/filter";
    }

    @GetMapping("/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        model.addAttribute("job", job);

        // Check if current user has applied for this job
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            User user = userService.getUserByUsername(authentication.getName());
            if (user.getRole() == User.UserRole.APPLICANT) {
                boolean hasApplied = applicationService.hasAppliedForJob(id, user.getId());
                model.addAttribute("hasApplied", hasApplied);
            }
        }

        return "jobs/view";
    }

    @GetMapping("/post")
    public String postJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "jobs/post";
    }

    @PostMapping("/post")
    public String postJob(@Valid @ModelAttribute("job") Job job,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "jobs/post";
        }

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            jobService.createJob(job, user.getId());
            redirectAttributes.addFlashAttribute("success", "Job posted successfully!");
            return "redirect:/employer/jobs";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/jobs/post";
        }
    }

    @GetMapping("/{id}/edit")
    public String editJobForm(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        model.addAttribute("job", job);
        return "jobs/edit";
    }

    @PostMapping("/{id}/edit")
    public String editJob(@PathVariable Long id,
            @Valid @ModelAttribute("job") Job job,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "jobs/edit";
        }

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            job.setId(id);
            jobService.updateJob(job, user.getId());
            redirectAttributes.addFlashAttribute("success", "Job updated successfully!");
            return "redirect:/employer/jobs";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/jobs/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            jobService.deleteJob(id, user.getId());
            redirectAttributes.addFlashAttribute("success", "Job deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/employer/jobs";
    }

    @PostMapping("/{id}/toggle")
    public String toggleJobStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            jobService.toggleJobStatus(id, user.getId());
            redirectAttributes.addFlashAttribute("success", "Job status updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/employer/jobs";
    }
}