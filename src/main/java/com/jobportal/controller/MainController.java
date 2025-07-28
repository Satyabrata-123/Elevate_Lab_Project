package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("recentJobs", jobService.getAllActiveJobs());
        return "home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        return home(model);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());

        model.addAttribute("user", user);

        if (user.getRole() == User.UserRole.EMPLOYER) {
            model.addAttribute("postedJobs", jobService.getJobsByEmployer(user.getId()));
            return "employer/dashboard";
        } else if (user.getRole() == User.UserRole.APPLICANT) {
            // For applicants, we'll show recent jobs they can apply to
            model.addAttribute("recentJobs", jobService.getAllActiveJobs());
            return "applicant/dashboard";
        } else {
            // Admin dashboard
            model.addAttribute("totalJobs", jobService.getAllActiveJobs().size());
            model.addAttribute("totalEmployers", userService.getUsersByRole(User.UserRole.EMPLOYER).size());
            model.addAttribute("totalApplicants", userService.getUsersByRole(User.UserRole.APPLICANT).size());
            return "admin/dashboard";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "profile";
    }
}