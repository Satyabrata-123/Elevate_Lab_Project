@echo off
echo ========================================
echo    Job Portal System - Startup Script
echo ========================================
echo.

echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven 3.6 or higher
    pause
    exit /b 1
)

echo.
echo Starting Job Portal System...
echo.
echo The application will be available at: http://localhost:8080
echo.
echo Test Credentials:
echo - Admin: admin / password123
echo - Employer: techcorp / password123
echo - Applicant: john_doe / password123
echo.
echo Press Ctrl+C to stop the application
echo.

mvn spring-boot:run

pause 