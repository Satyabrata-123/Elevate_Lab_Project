# Job Portal System

A comprehensive job portal web application built with Java, Spring Boot, MySQL, and Thymeleaf. This system allows employers to post jobs and applicants to apply for positions with full application tracking capabilities.

## Features

### User Management
- **User Roles**: Employer, Applicant, and Admin roles with different permissions
- **Registration**: User registration with role selection
- **Authentication**: Spring Security-based login/logout system
- **Profile Management**: Users can update their profiles and information

### Job Management
- **Job Posting**: Employers can create and post job listings
- **Job Search**: Advanced search functionality with filters
- **Job Categories**: Jobs organized by categories (IT, Marketing, Sales, etc.)
- **Job Status**: Active/Inactive job management
- **Job Expiry**: Automatic job expiration after 3 months

### Application System
- **Job Applications**: Applicants can apply for jobs with cover letters
- **Application Tracking**: Real-time status tracking for applications
- **Status Management**: Multiple application statuses (Pending, Reviewing, Shortlisted, etc.)
- **Notes System**: Employers and applicants can add notes to applications

### Search and Filtering
- **Keyword Search**: Search jobs by title, description, requirements, or location
- **Advanced Filters**: Filter by category, location, job type, experience level, and salary range
- **Pagination**: Efficient pagination for large job listings

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.2.0
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome
- **Security**: Spring Security with BCrypt password encoding
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Installation and Setup

### 1. Database Setup

1. Install MySQL if not already installed
2. Create a MySQL user or use the root user
3. Update the database configuration in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 2. Build and Run

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd job-portal
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application at: `http://localhost:8080`

### 3. Database Initialization

The application will automatically:
- Create the database schema on first run
- Populate sample data including users, jobs, and applications
- Display test credentials in the console

## Test Credentials

The system comes with pre-configured test accounts:

### Admin Account
- **Username**: admin
- **Password**: admin123
- **Role**: Administrator

### Employer Accounts
- **Username**: techcorp
- **Password**: password123
- **Role**: Employer (TechCorp Inc)

- **Username**: innovateco
- **Password**: password123
- **Role**: Employer (InnovateCo)

### Applicant Accounts
- **Username**: john_doe
- **Password**: password123
- **Role**: Applicant

- **Username**: jane_smith
- **Password**: password123
- **Role**: Applicant

- **Username**: mike_wilson
- **Password**: password123
- **Role**: Applicant

## Usage Guide

### For Employers

1. **Login** with employer credentials
2. **Post Jobs**: Navigate to "Post Job" to create new job listings
3. **Manage Jobs**: View, edit, and manage your posted jobs
4. **Review Applications**: View and manage applications for your jobs
5. **Update Status**: Change application status and add notes

### For Applicants

1. **Register** as an applicant or login with existing credentials
2. **Browse Jobs**: Search and filter available job opportunities
3. **Apply for Jobs**: Submit applications with cover letters
4. **Track Applications**: Monitor the status of your applications
5. **Update Profile**: Keep your profile and resume information current

### For Administrators

1. **Login** with admin credentials
2. **Dashboard**: View system statistics and overview
3. **User Management**: Monitor and manage user accounts
4. **System Overview**: Access comprehensive system information

## Database Schema

### Users Table
- User authentication and profile information
- Role-based access control
- Company information for employers
- Resume links for applicants

### Jobs Table
- Job posting details
- Employer relationship
- Status and expiration management
- Category and filtering information

### Applications Table
- Job application records
- Status tracking
- Cover letters and resumes
- Notes and communication

## API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /jobs` - List all jobs
- `GET /jobs/search` - Search jobs
- `GET /jobs/filter` - Filter jobs
- `GET /jobs/{id}` - View job details
- `GET /login` - Login page
- `GET /register` - Registration page

### Protected Endpoints
- `GET /dashboard` - User dashboard
- `GET /profile` - User profile
- `POST /jobs/post` - Post new job (Employers)
- `POST /applications/apply/{jobId}` - Apply for job (Applicants)
- `GET /applications/my-applications` - View applications (Applicants)
- `GET /applications/job/{jobId}` - View job applications (Employers)

## Configuration

### Application Properties

Key configuration options in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
spring.datasource.username=root
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8080

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
```

## Security Features

- **Password Encryption**: BCrypt password hashing
- **Role-Based Access**: Different permissions for different user roles
- **Session Management**: Secure session handling
- **CSRF Protection**: Cross-site request forgery protection
- **Input Validation**: Server-side validation for all forms

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions, please contact:
- Email: info@jobportal.com
- Phone: +1 (555) 123-4567

## Screenshots

The application features a modern, responsive design with:
- Clean and intuitive user interface
- Mobile-responsive design
- Professional color scheme
- Easy navigation and user experience

## Future Enhancements

- Email notifications for application status changes
- Resume upload functionality
- Advanced analytics and reporting
- Job recommendations based on user profile
- Interview scheduling system
- Company profile pages
- Job alerts and notifications 