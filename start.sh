#!/bin/bash

echo "========================================"
echo "   Job Portal System - Startup Script"
echo "========================================"
echo

echo "Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

echo "Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven 3.6 or higher"
    exit 1
fi

echo
echo "Starting Job Portal System..."
echo
echo "The application will be available at: http://localhost:8080"
echo
echo "Test Credentials:"
echo "- Admin: admin / password123"
echo "- Employer: techcorp / password123"
echo "- Applicant: john_doe / password123"
echo
echo "Press Ctrl+C to stop the application"
echo

mvn spring-boot:run 