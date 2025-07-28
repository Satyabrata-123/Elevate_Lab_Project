package com.jobportal.config;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no users exist
        if (userRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Create sample users
        User admin = createUser("admin", "admin@jobportal.com", "admin123", "Admin", "User",
                User.UserRole.ADMIN, "555-0001", "123 Admin St", null, null);

        User employer1 = createUser("techcorp", "hr@techcorp.com", "password123", "John", "Smith",
                User.UserRole.EMPLOYER, "555-0002", "456 Business Ave", "TechCorp Inc", null);

        User employer2 = createUser("innovateco", "careers@innovateco.com", "password123", "Sarah", "Johnson",
                User.UserRole.EMPLOYER, "555-0003", "789 Innovation Blvd", "InnovateCo", null);

        User applicant1 = createUser("john_doe", "john.doe@email.com", "password123", "John", "Doe",
                User.UserRole.APPLICANT, "555-0004", "321 Main St", null, "https://example.com/resume1.pdf");

        User applicant2 = createUser("jane_smith", "jane.smith@email.com", "password123", "Jane", "Smith",
                User.UserRole.APPLICANT, "555-0005", "654 Oak Ave", null, "https://example.com/resume2.pdf");

        User applicant3 = createUser("mike_wilson", "mike.wilson@email.com", "password123", "Mike", "Wilson",
                User.UserRole.APPLICANT, "555-0006", "987 Pine Rd", null, "https://example.com/resume3.pdf");

        // Create sample jobs
        Job job1 = createJob("Senior Java Developer",
                "We are looking for an experienced Java developer to join our team. You will be responsible for developing and maintaining web applications using Spring Boot and related technologies.",
                "5+ years of Java development experience\nSpring Boot framework experience\nMySQL database experience\nRESTful API development\nGit version control",
                "New York, NY", new BigDecimal("120000"), "Full-time", "Senior", "IT", employer1);

        Job job2 = createJob("Frontend Developer",
                "Join our frontend team to create beautiful and responsive user interfaces. We use modern JavaScript frameworks and tools.",
                "3+ years of frontend development\nReact.js or Vue.js experience\nHTML5, CSS3, JavaScript\nResponsive design principles\nGit experience",
                "San Francisco, CA", new BigDecimal("95000"), "Full-time", "Mid", "IT", employer1);

        Job job3 = createJob("Marketing Manager",
                "Lead our marketing initiatives and drive brand awareness. You will develop and execute marketing strategies across multiple channels.",
                "5+ years of marketing experience\nDigital marketing expertise\nTeam leadership experience\nAnalytics and reporting skills\nCreative thinking",
                "Chicago, IL", new BigDecimal("85000"), "Full-time", "Senior", "Marketing", employer2);

        Job job4 = createJob("Data Analyst",
                "Analyze data to provide insights and support business decisions. You will work with large datasets and create reports.",
                "2+ years of data analysis experience\nSQL and Excel proficiency\nStatistical analysis skills\nData visualization tools\nAttention to detail",
                "Remote", new BigDecimal("75000"), "Full-time", "Entry", "Analytics", employer2);

        Job job5 = createJob("UX Designer",
                "Design user experiences that delight our customers. You will create wireframes, prototypes, and conduct user research.",
                "3+ years of UX design experience\nFigma or Sketch proficiency\nUser research methods\nPrototyping skills\nPortfolio of work",
                "Austin, TX", new BigDecimal("90000"), "Full-time", "Mid", "Design", employer1);

        // Create sample applications
        createApplication(job1, applicant1,
                "I am excited to apply for the Senior Java Developer position. With 6 years of experience in Java development and Spring Boot, I believe I would be a great fit for your team.",
                "https://example.com/resume1.pdf", Application.ApplicationStatus.REVIEWING);

        createApplication(job1, applicant2,
                "I have extensive experience with Java and Spring Boot frameworks. I am passionate about creating scalable and maintainable code.",
                "https://example.com/resume2.pdf", Application.ApplicationStatus.SHORTLISTED);

        createApplication(job2, applicant1,
                "I have been working with React.js for the past 3 years and love creating intuitive user interfaces. I am excited about the opportunity to join your frontend team.",
                "https://example.com/resume1.pdf", Application.ApplicationStatus.PENDING);

        createApplication(job3, applicant3,
                "I have successfully led marketing campaigns for multiple companies and have a proven track record of increasing brand awareness and driving sales.",
                "https://example.com/resume3.pdf", Application.ApplicationStatus.INTERVIEW_SCHEDULED);

        createApplication(job4, applicant2,
                "I am passionate about data analysis and have experience working with SQL and various analytics tools. I would love to contribute to your data-driven decision making.",
                "https://example.com/resume2.pdf", Application.ApplicationStatus.PENDING);

        System.out.println("Sample data initialized successfully!");
        System.out.println("Test Credentials:");
        System.out.println("Admin: admin / admin123");
        System.out.println("Employer: techcorp / password123");
        System.out.println("Applicant: john_doe / password123");
    }

    private User createUser(String username, String email, String password, String firstName, String lastName,
            User.UserRole role, String phone, String address, String companyName, String resume) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        user.setPhone(phone);
        user.setAddress(address);
        user.setCompanyName(companyName);
        user.setResume(resume);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    private Job createJob(String title, String description, String requirements, String location,
            BigDecimal salary, String jobType, String experienceLevel, String category, User employer) {
        Job job = new Job();
        job.setTitle(title);
        job.setDescription(description);
        job.setRequirements(requirements);
        job.setLocation(location);
        job.setSalary(salary);
        job.setJobType(jobType);
        job.setExperienceLevel(experienceLevel);
        job.setCategory(category);
        job.setEmployer(employer);
        job.setActive(true);
        job.setExpiresAt(LocalDateTime.now().plusMonths(3));
        return jobRepository.save(job);
    }

    private Application createApplication(Job job, User applicant, String coverLetter, String resume,
            Application.ApplicationStatus status) {
        Application application = new Application();
        application.setJob(job);
        application.setApplicant(applicant);
        application.setCoverLetter(coverLetter);
        application.setResume(resume);
        application.setStatus(status);
        return applicationRepository.save(application);
    }
}