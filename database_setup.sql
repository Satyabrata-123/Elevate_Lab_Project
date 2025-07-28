-- Job Portal Database Setup Script
-- This script creates the database and initial schema for the Job Portal System

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS job_portal;
USE job_portal;

-- Drop existing tables if they exist (for clean setup)
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role ENUM('EMPLOYER', 'APPLICANT', 'ADMIN') NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    company_name VARCHAR(100),
    resume VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE
);

-- Create jobs table
CREATE TABLE jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    requirements TEXT NOT NULL,
    location VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    job_type VARCHAR(50),
    experience_level VARCHAR(50),
    category VARCHAR(50),
    employer_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (employer_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create applications table
CREATE TABLE applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT NOT NULL,
    applicant_id BIGINT NOT NULL,
    cover_letter TEXT NOT NULL,
    resume VARCHAR(255),
    status ENUM('PENDING', 'REVIEWING', 'SHORTLISTED', 'INTERVIEW_SCHEDULED', 'REJECTED', 'ACCEPTED', 'WITHDRAWN') DEFAULT 'PENDING',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    employer_notes TEXT,
    applicant_notes TEXT,
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    FOREIGN KEY (applicant_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_application (job_id, applicant_id)
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_jobs_employer ON jobs(employer_id);
CREATE INDEX idx_jobs_active ON jobs(active);
CREATE INDEX idx_jobs_category ON jobs(category);
CREATE INDEX idx_jobs_location ON jobs(location);
CREATE INDEX idx_applications_job ON applications(job_id);
CREATE INDEX idx_applications_applicant ON applications(applicant_id);
CREATE INDEX idx_applications_status ON applications(status);

-- Insert sample data

-- Sample Users (passwords are BCrypt encoded - all use 'password123')
INSERT INTO users (username, email, password, first_name, last_name, role, phone, address, company_name, resume) VALUES
('admin', 'admin@jobportal.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'Admin', 'User', 'ADMIN', '555-0001', '123 Admin St', NULL, NULL),
('techcorp', 'hr@techcorp.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'John', 'Smith', 'EMPLOYER', '555-0002', '456 Business Ave', 'TechCorp Inc', NULL),
('innovateco', 'careers@innovateco.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'Sarah', 'Johnson', 'EMPLOYER', '555-0003', '789 Innovation Blvd', 'InnovateCo', NULL),
('john_doe', 'john.doe@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'John', 'Doe', 'APPLICANT', '555-0004', '321 Main St', NULL, 'https://example.com/resume1.pdf'),
('jane_smith', 'jane.smith@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'Jane', 'Smith', 'APPLICANT', '555-0005', '654 Oak Ave', NULL, 'https://example.com/resume2.pdf'),
('mike_wilson', 'mike.wilson@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'Mike', 'Wilson', 'APPLICANT', '555-0006', '987 Pine Rd', NULL, 'https://example.com/resume3.pdf');

-- Sample Jobs
INSERT INTO jobs (title, description, requirements, location, salary, job_type, experience_level, category, employer_id, expires_at) VALUES
('Senior Java Developer', 'We are looking for an experienced Java developer to join our team. You will be responsible for developing and maintaining web applications using Spring Boot and related technologies.', '5+ years of Java development experience\nSpring Boot framework experience\nMySQL database experience\nRESTful API development\nGit version control', 'New York, NY', 120000.00, 'Full-time', 'Senior', 'IT', 2, DATE_ADD(NOW(), INTERVAL 3 MONTH)),
('Frontend Developer', 'Join our frontend team to create beautiful and responsive user interfaces. We use modern JavaScript frameworks and tools.', '3+ years of frontend development\nReact.js or Vue.js experience\nHTML5, CSS3, JavaScript\nResponsive design principles\nGit experience', 'San Francisco, CA', 95000.00, 'Full-time', 'Mid', 'IT', 2, DATE_ADD(NOW(), INTERVAL 3 MONTH)),
('Marketing Manager', 'Lead our marketing initiatives and drive brand awareness. You will develop and execute marketing strategies across multiple channels.', '5+ years of marketing experience\nDigital marketing expertise\nTeam leadership experience\nAnalytics and reporting skills\nCreative thinking', 'Chicago, IL', 85000.00, 'Full-time', 'Senior', 'Marketing', 3, DATE_ADD(NOW(), INTERVAL 3 MONTH)),
('Data Analyst', 'Analyze data to provide insights and support business decisions. You will work with large datasets and create reports.', '2+ years of data analysis experience\nSQL and Excel proficiency\nStatistical analysis skills\nData visualization tools\nAttention to detail', 'Remote', 75000.00, 'Full-time', 'Entry', 'Analytics', 3, DATE_ADD(NOW(), INTERVAL 3 MONTH)),
('UX Designer', 'Design user experiences that delight our customers. You will create wireframes, prototypes, and conduct user research.', '3+ years of UX design experience\nFigma or Sketch proficiency\nUser research methods\nPrototyping skills\nPortfolio of work', 'Austin, TX', 90000.00, 'Full-time', 'Mid', 'Design', 2, DATE_ADD(NOW(), INTERVAL 3 MONTH));

-- Sample Applications
INSERT INTO applications (job_id, applicant_id, cover_letter, resume, status) VALUES
(1, 4, 'I am excited to apply for the Senior Java Developer position. With 6 years of experience in Java development and Spring Boot, I believe I would be a great fit for your team.', 'https://example.com/resume1.pdf', 'REVIEWING'),
(1, 5, 'I have extensive experience with Java and Spring Boot frameworks. I am passionate about creating scalable and maintainable code.', 'https://example.com/resume2.pdf', 'SHORTLISTED'),
(2, 4, 'I have been working with React.js for the past 3 years and love creating intuitive user interfaces. I am excited about the opportunity to join your frontend team.', 'https://example.com/resume1.pdf', 'PENDING'),
(3, 6, 'I have successfully led marketing campaigns for multiple companies and have a proven track record of increasing brand awareness and driving sales.', 'https://example.com/resume3.pdf', 'INTERVIEW_SCHEDULED'),
(4, 5, 'I am passionate about data analysis and have experience working with SQL and various analytics tools. I would love to contribute to your data-driven decision making.', 'https://example.com/resume2.pdf', 'PENDING');

-- Display test credentials
SELECT 'Database setup completed successfully!' as message;
SELECT 'Test Credentials:' as credentials;
SELECT 'Admin: admin / password123' as admin_cred;
SELECT 'Employer: techcorp / password123' as employer_cred;
SELECT 'Applicant: john_doe / password123' as applicant_cred; 