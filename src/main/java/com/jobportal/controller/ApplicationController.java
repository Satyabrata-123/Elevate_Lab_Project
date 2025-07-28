package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId,
            @RequestParam String coverLetter,
            @RequestParam(required = false) String resume,
            RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            applicationService.applyForJob(jobId, user.getId(), coverLetter, resume);
            redirectAttributes.addFlashAttribute("success", "Application submitted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/jobs/" + jobId;
    }

    @GetMapping("/my-applications")
    public String myApplications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        List<Application> applications = applicationService.getApplicationsByApplicant(user.getId());
        model.addAttribute("applications", applications);
        model.addAttribute("user", user);

        return "applications/my-applications";
    }

    @GetMapping("/job/{jobId}")
    public String jobApplications(@PathVariable Long jobId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        List<Application> applications = applicationService.getApplicationsByJob(jobId);
        model.addAttribute("applications", applications);
        model.addAttribute("jobId", jobId);
        model.addAttribute("user", user);

        return "applications/job-applications";
    }

    @GetMapping("/{id}")
    public String viewApplication(@PathVariable Long id, Model model) {
        Application application = applicationService.getApplicationById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        // Check if user has permission to view this application
        if (user.getRole() == User.UserRole.APPLICANT &&
                !application.getApplicant().getId().equals(user.getId())) {
            return "redirect:/applications/my-applications";
        }

        if (user.getRole() == User.UserRole.EMPLOYER &&
                !application.getJob().getEmployer().getId().equals(user.getId())) {
            return "redirect:/employer/jobs";
        }

        model.addAttribute("application", application);
        model.addAttribute("user", user);

        return "applications/view";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
            @RequestParam Application.ApplicationStatus status,
            RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            applicationService.updateApplicationStatus(id, status, user.getId());
            redirectAttributes.addFlashAttribute("success", "Application status updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/applications/" + id;
    }

    @PostMapping("/{id}/employer-notes")
    public String addEmployerNotes(@PathVariable Long id,
            @RequestParam String notes,
            RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            applicationService.addEmployerNotes(id, notes, user.getId());
            redirectAttributes.addFlashAttribute("success", "Notes added successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/applications/" + id;
    }

    @PostMapping("/{id}/applicant-notes")
    public String addApplicantNotes(@PathVariable Long id,
            @RequestParam String notes,
            RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            applicationService.addApplicantNotes(id, notes, user.getId());
            redirectAttributes.addFlashAttribute("success", "Notes added successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/applications/" + id;
    }

    @PostMapping("/{id}/withdraw")
    public String withdrawApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            applicationService.withdrawApplication(id, user.getId());
            redirectAttributes.addFlashAttribute("success", "Application withdrawn successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/applications/my-applications";
    }

    @PostMapping("/{id}/delete")
    public String deleteApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(authentication.getName());

            applicationService.deleteApplication(id, user.getId());
            redirectAttributes.addFlashAttribute("success", "Application deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/applications/my-applications";
    }
}