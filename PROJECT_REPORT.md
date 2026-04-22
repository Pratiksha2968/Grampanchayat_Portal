# Smart Village Digital Gram Panchayat Platform

## Project Report

---

## Table of Contents
1. [Introduction](#1-introduction)
2. [Problem Statement](#2-problem-statement)
3. [Objectives](#3-objectives)
4. [Technology Stack](#4-technology-stack)
5. [System Architecture](#5-system-architecture)
6. [Database Design](#6-database-design)
7. [Module Description](#7-module-description)
8. [Features and Functionality](#8-features-and-functionality)
9. [API Documentation](#9-api-documentation)
10. [Security Implementation](#10-security-implementation)
11. [User Interface](#11-user-interface)
12. [Testing](#12-testing)
13. [Deployment](#13-deployment)
14. [Future Enhancements](#14-future-enhancements)
15. [Conclusion](#15-conclusion)

---

## 1. Introduction

The Smart Village Digital Gram Panchayat Platform is a comprehensive web-based e-governance solution designed to digitize and streamline the operations of Gram Panchayats (village councils) in India. This platform bridges the gap between rural citizens and local government administration by providing online access to essential services.

### 1.1 Background

In rural India, Gram Panchayats serve as the foundation of local self-governance. Citizens often face challenges in accessing government services due to:
- Physical distance from Panchayat offices
- Limited operating hours
- Lack of transparency in administrative processes
- Paper-based record keeping
- Time-consuming manual processes

### 1.2 Purpose

This platform aims to:
- Digitize Gram Panchayat services
- Provide 24/7 access to government services
- Ensure transparency in governance
- Reduce paperwork and processing time
- Enable efficient tracking of applications and complaints

---

## 2. Problem Statement

Traditional Gram Panchayat operations face several challenges:

1. **Accessibility Issues**: Citizens must visit Panchayat offices during working hours
2. **Lack of Transparency**: No visibility into application status or fund utilization
3. **Manual Processes**: Paper-based applications leading to delays and errors
4. **No Tracking Mechanism**: Citizens cannot track their applications or complaints
5. **Limited Communication**: No direct channel for grievances
6. **Inefficient Record Keeping**: Physical records prone to damage and loss

---

## 3. Objectives

### Primary Objectives
1. Develop a digital platform for Gram Panchayat services
2. Enable online application submission and tracking
3. Implement transparent fund management
4. Provide citizen grievance redressal system
5. Generate digital certificates with QR code verification

### Secondary Objectives
1. Role-based access control for security
2. Real-time notifications for status updates
3. Analytics dashboard for administrators
4. Mobile-responsive design for accessibility
5. Integration with payment gateways

---

## 4. Technology Stack

### 4.1 Backend Technologies
| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 25 | Programming Language |
| Spring Boot | 3.3.5 | Application Framework |
| Spring Security | 6.x | Authentication & Authorization |
| Spring Data JPA | 3.x | Database Operations |
| Hibernate | 6.x | ORM Framework |
| JWT (io.jsonwebtoken) | 0.12.3 | Token-based Authentication |
| iText | 8.x | PDF Generation |
| ZXing | 3.x | QR Code Generation |

### 4.2 Frontend Technologies
| Technology | Version | Purpose |
|------------|---------|---------|
| JSP | 3.1 | Server-side Rendering |
| Bootstrap | 5.1.3 | CSS Framework |
| JavaScript | ES6+ | Client-side Scripting |
| Chart.js | 3.x | Data Visualization |
| Font Awesome | 6.0 | Icons |

### 4.3 Database
| Technology | Purpose |
|------------|---------|
| MySQL | Relational Database |

### 4.4 Build & Deployment
| Tool | Purpose |
|------|---------|
| Maven | Build Automation |
| Apache Tomcat | Web Server (Embedded) |

---

## 5. System Architecture

### 5.1 Architecture Pattern
The application follows a **Layered Architecture** pattern:

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                     │
│              (JSP Views, Static Resources)                │
├─────────────────────────────────────────────────────────┤
│                    Controller Layer                       │
│         (REST Controllers, Web Controllers)               │
├─────────────────────────────────────────────────────────┤
│                     Service Layer                         │
│              (Business Logic Services)                    │
├─────────────────────────────────────────────────────────┤
│                   Repository Layer                        │
│           (Spring Data JPA Repositories)                  │
├─────────────────────────────────────────────────────────┤
│                     Database Layer                        │
│                    (MySQL Database)                       │
└─────────────────────────────────────────────────────────┘
```

### 5.2 Project Structure

```
smart-village-platform/
├── src/
│   ├── main/
│   │   ├── java/com/smartvillage/
│   │   │   ├── config/                    # Configuration Classes
│   │   │   │   └── DataInitializer.java   # Initial Data Setup
│   │   │   ├── controller/                # REST & Web Controllers
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── CitizenController.java
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── TransparencyController.java
│   │   │   │   └── WebController.java
│   │   │   ├── dto/                       # Data Transfer Objects
│   │   │   │   ├── ApplicationDTO.java
│   │   │   │   ├── ComplaintDTO.java
│   │   │   │   ├── JwtResponse.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── MessageResponse.java
│   │   │   │   └── SignupRequest.java
│   │   │   ├── entity/                    # JPA Entities
│   │   │   │   ├── Application.java
│   │   │   │   ├── Certificate.java
│   │   │   │   ├── Complaint.java
│   │   │   │   ├── Notification.java
│   │   │   │   ├── Payment.java
│   │   │   │   ├── Role.java
│   │   │   │   ├── Scheme.java
│   │   │   │   ├── TransparencyRecord.java
│   │   │   │   └── User.java
│   │   │   ├── repository/                # Data Repositories
│   │   │   ├── security/                  # Security Components
│   │   │   │   ├── AuthTokenFilter.java
│   │   │   │   ├── JwtUtils.java
│   │   │   │   ├── UserDetailsImpl.java
│   │   │   │   ├── UserDetailsServiceImpl.java
│   │   │   │   └── WebSecurityConfig.java
│   │   │   └── service/                   # Business Services
│   │   │       ├── AnalyticsService.java
│   │   │       ├── ApplicationService.java
│   │   │       ├── AuthService.java
│   │   │       ├── CertificateService.java
│   │   │       ├── ComplaintService.java
│   │   │       ├── NotificationService.java
│   │   │       ├── PaymentService.java
│   │   │       ├── SchemeService.java
│   │   │       └── TransparencyService.java
│   │   ├── resources/
│   │   │   └── application.properties     # Application Configuration
│   │   └── webapp/
│   │       ├── WEB-INF/views/             # JSP Views
│   │       │   ├── admin/
│   │       │   ├── auth/
│   │       │   └── citizen/
│   │       └── static/                    # Static Resources
│   │           ├── css/
│   │           └── js/
│   └── test/
├── pom.xml                                # Maven Configuration
└── target/                                # Build Output
```

---

## 6. Database Design

### 6.1 Entity-Relationship Diagram

```
┌─────────────┐       ┌─────────────┐
│    User     │       │    Role     │
├─────────────┤       ├─────────────┤
│ id (PK)     │       │ id (PK)     │
│ username    │──────▶│ name        │
│ email       │       │ description │
│ password    │       └─────────────┘
│ full_name   │
│ phone       │
│ address     │
│ role_id(FK) │
└─────────────┘
       │
       │
       ▼
┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│ Application │  │  Complaint  │  │ Certificate │
├─────────────┤  ├─────────────┤  ├─────────────┤
│ id (PK)     │  │ id (PK)     │  │ id (PK)     │
│ app_number  │  │ category    │  │ cert_number │
│ type        │  │ subject     │  │ type        │
│ status      │  │ status      │  │ pdf_path    │
│ user_id(FK) │  │ user_id(FK) │  │ qr_path     │
│ details     │  │ description │  │ user_id(FK) │
└─────────────┘  └─────────────┘  └─────────────┘

┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│   Payment   │  │   Scheme    │  │Notification │
├─────────────┤  ├─────────────┤  ├─────────────┤
│ id (PK)     │  │ id (PK)     │  │ id (PK)     │
│ txn_id      │  │ name        │  │ subject     │
│ type        │  │ category    │  │ message     │
│ amount      │  │ eligibility │  │ type        │
│ status      │  │ active      │  │ is_read     │
│ user_id(FK) │  └─────────────┘  │ user_id(FK) │
└─────────────┘                   └─────────────┘
```

### 6.2 Database Tables

#### Users Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key |
| username | VARCHAR(50) | Unique username |
| email | VARCHAR(100) | Email address |
| password | VARCHAR(255) | BCrypt hashed password |
| full_name | VARCHAR(100) | Full name |
| phone_number | VARCHAR(15) | Phone number |
| address | VARCHAR(500) | Address |
| aadhar_number | VARCHAR(12) | Aadhar number |
| age | INT | Age |
| occupation | VARCHAR(100) | Occupation |
| income | DOUBLE | Annual income |
| role_id | BIGINT | Foreign key to roles |
| enabled | BOOLEAN | Account status |
| created_at | DATETIME | Creation timestamp |
| updated_at | DATETIME | Update timestamp |

#### Applications Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key |
| application_number | VARCHAR(50) | Unique application number |
| application_type | VARCHAR(50) | Type of application |
| status | VARCHAR(20) | PENDING/APPROVED/REJECTED |
| details | TEXT | Application details |
| remarks | TEXT | Admin remarks |
| document_path | VARCHAR(255) | Uploaded document path |
| priority | INT | Priority level |
| user_id | BIGINT | Foreign key to users |
| processed_by | BIGINT | Admin who processed |
| applied_date | DATETIME | Application date |
| processed_date | DATETIME | Processing date |

#### Certificates Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key |
| certificate_number | VARCHAR(50) | Unique certificate number |
| certificate_type | VARCHAR(50) | Type of certificate |
| pdf_path | VARCHAR(255) | PDF file path |
| qr_code_path | VARCHAR(255) | QR code image path |
| user_id | BIGINT | Foreign key to users |
| application_id | BIGINT | Foreign key to applications |
| issued_by | BIGINT | Admin who issued |
| issued_date | DATETIME | Issue date |

---

## 7. Module Description

### 7.1 Authentication Module
**Purpose**: Handle user authentication and authorization

**Components**:
- `AuthController`: REST endpoints for login/signup
- `AuthService`: Business logic for authentication
- `JwtUtils`: JWT token generation and validation
- `AuthTokenFilter`: Request filtering for token validation
- `WebSecurityConfig`: Security configuration

**Flow**:
1. User submits credentials
2. Credentials validated against database
3. JWT token generated on success
4. Token returned to client
5. Client includes token in subsequent requests

### 7.2 Application Management Module
**Purpose**: Handle certificate applications

**Components**:
- `ApplicationController`: REST endpoints
- `ApplicationService`: Business logic
- `ApplicationRepository`: Data access

**Features**:
- Submit new applications
- Track application status
- Process applications (Admin)
- Auto-generate certificates on approval

### 7.3 Complaint Management Module
**Purpose**: Handle citizen complaints

**Categories**:
- Water Supply
- Road
- Electricity
- Sanitation
- Other

**Features**:
- Submit complaints
- Track complaint status
- Resolve complaints (Admin)

### 7.4 Certificate Module
**Purpose**: Generate and manage certificates

**Certificate Types**:
- Birth Certificate
- Death Certificate
- Income Certificate
- Caste Certificate
- Residence Certificate

**Features**:
- Auto-generation on application approval
- PDF generation with iText
- QR code for verification
- Download functionality

### 7.5 Payment Module
**Purpose**: Handle online payments

**Payment Types**:
- Property Tax
- Water Tax
- Certificate Fee
- Other

**Features**:
- Create payment records
- Track payment history
- Payment status management

### 7.6 Scheme Module
**Purpose**: Display government schemes

**Scheme Categories**:
- Agriculture (PM-KISAN)
- Health (Ayushman Bharat)
- Employment (MGNREGA)
- Education (Beti Bachao Beti Padhao)

### 7.7 Transparency Module
**Purpose**: Display financial transparency data

**Features**:
- Fund records
- Expense tracking
- Project status

### 7.8 Notification Module
**Purpose**: Send notifications to users

**Types**:
- Email notifications
- In-app notifications

---

## 8. Features and Functionality

### 8.1 Citizen Features

#### Dashboard
- View application count
- View complaint count
- View certificate count
- View payment history
- Recommended schemes

#### Applications
- Submit new applications
- Select application type
- Add details
- Track status
- View processed applications

#### Complaints
- Submit complaints
- Select category
- Add description
- Track status

#### Certificates
- View issued certificates
- Download certificates
- Verify certificates

#### Payments
- Make payments
- Select payment type
- Enter amount
- View payment history

#### Schemes
- View all schemes
- Check eligibility
- View scheme details

### 8.2 Admin Features

#### Dashboard
- Total applications count
- Pending applications count
- Open complaints count
- Monthly collection amount
- Application status chart
- Complaint category chart

#### Application Management
- View all applications
- View pending applications
- Process applications
- Approve/Reject with remarks
- Auto-generate certificates

#### Complaint Management
- View all complaints
- View open complaints
- Update complaint status
- Add resolution

#### Certificate Management
- View all certificates
- Manual certificate generation

#### Payment Management
- View all payments
- Track payment status

#### Transparency Management
- Add fund records
- Add expense records
- View transparency stats

#### Analytics
- Applications by type
- Complaints by category
- Payment analytics

---

## 9. API Documentation

### 9.1 Authentication APIs

#### Login
```
POST /api/auth/login
Content-Type: application/json

Request Body:
{
  "username": "admin",
  "password": "admin123"
}

Response:
{
  "token": "eyJhbGciOiJIUzM4NCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@grampanchayat.com",
  "role": "ADMIN"
}
```

#### Signup
```
POST /api/auth/signup
Content-Type: application/json

Request Body:
{
  "username": "citizen1",
  "email": "citizen@email.com",
  "password": "password123",
  "fullName": "Citizen Name",
  "phoneNumber": "9876543210",
  "address": "Village Address"
}

Response:
{
  "message": "User registered successfully!"
}
```

### 9.2 Citizen APIs

#### Get My Applications
```
GET /api/citizen/applications
Authorization: Bearer {token}

Response:
[
  {
    "id": 1,
    "applicationNumber": "APP1234567890",
    "applicationType": "INCOME_CERTIFICATE",
    "status": "APPROVED",
    "details": "Need income certificate",
    "appliedDate": "2024-01-15"
  }
]
```

#### Submit Application
```
POST /api/citizen/applications
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "applicationType": "INCOME_CERTIFICATE",
  "details": "Need income certificate for loan"
}

Response:
{
  "id": 1,
  "applicationNumber": "APP1234567890",
  "status": "PENDING"
}
```

#### Get My Complaints
```
GET /api/citizen/complaints
Authorization: Bearer {token}
```

#### Submit Complaint
```
POST /api/citizen/complaints
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "category": "WATER",
  "subject": "No water supply",
  "description": "No water supply for 3 days"
}
```

#### Get My Certificates
```
GET /api/citizen/certificates
Authorization: Bearer {token}
```

#### Get Schemes
```
GET /api/citizen/schemes
Authorization: Bearer {token}
```

#### Create Payment
```
POST /api/citizen/payments?paymentType=PROPERTY_TAX&amount=500
Authorization: Bearer {token}
```

### 9.3 Admin APIs

#### Get Dashboard Stats
```
GET /api/admin/stats
Authorization: Bearer {token}

Response:
{
  "totalApplications": 10,
  "pendingApplications": 5,
  "approvedApplications": 4,
  "rejectedApplications": 1,
  "totalComplaints": 3,
  "openComplaints": 2,
  "resolvedComplaints": 1,
  "totalPayments": 8,
  "monthlyCollection": 15000.00
}
```

#### Get All Applications
```
GET /api/admin/applications
Authorization: Bearer {token}
```

#### Process Application
```
PUT /api/admin/applications/{id}/process
Authorization: Bearer {token}
Content-Type: application/json

Request Body:
{
  "status": "APPROVED",
  "remarks": "All documents verified"
}
```

#### Get All Complaints
```
GET /api/admin/complaints
Authorization: Bearer {token}
```

#### Update Complaint Status
```
PUT /api/admin/complaints/{id}/status?status=RESOLVED&resolution=Issue fixed
Authorization: Bearer {token}
```

#### Get All Payments
```
GET /api/admin/payments
Authorization: Bearer {token}
```

---

## 10. Security Implementation

### 10.1 Authentication Flow

```
┌──────────┐     ┌──────────┐     ┌──────────┐     ┌──────────┐
│  Client  │────▶│  Login   │────▶│   JWT    │────▶│ Database │
│          │     │ Endpoint │     │  Token   │     │          │
└──────────┘     └──────────┘     └──────────┘     └──────────┘
      │                                                  │
      │                                                  │
      ▼                                                  ▼
┌──────────┐                                       ┌──────────┐
│ Protected│◀─────── Token in Header ──────────────│  User    │
│ Resource │                                       │  Data    │
└──────────┘                                       └──────────┘
```

### 10.2 Security Configuration

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(...))
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/citizen/**").hasAnyRole("CITIZEN", "ADMIN")
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}
```

### 10.3 JWT Token Structure

```
Header:
{
  "alg": "HS384"
}

Payload:
{
  "sub": "username",
  "iat": 1234567890,
  "exp": 1234654290
}

Signature:
HMACSHA384(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
```

### 10.4 Password Security
- BCrypt hashing algorithm
- Salt automatically generated
- Work factor: 10 rounds

---

## 11. User Interface

### 11.1 Pages

#### Public Pages
- **Home Page**: Landing page with platform overview
- **Login Page**: User authentication
- **Signup Page**: User registration

#### Citizen Pages
- **Dashboard**: Overview of citizen's activities
- **Applications**: Submit and track applications
- **Complaints**: Submit and track complaints
- **Certificates**: View and download certificates
- **Payments**: Make and track payments
- **Schemes**: View government schemes

#### Admin Pages
- **Dashboard**: Statistics and charts
- **Applications**: Process applications
- **Complaints**: Manage complaints
- **Certificates**: View certificates
- **Payments**: View all payments
- **Schemes**: Manage schemes
- **Transparency**: Financial records
- **Analytics**: Reports and analytics

### 11.2 UI Components

#### Navigation
- Responsive sidebar navigation
- Role-based menu items
- User info display
- Logout button

#### Forms
- Bootstrap-styled forms
- Client-side validation
- Error message display
- Success notifications

#### Tables
- Sortable columns
- Status badges
- Action buttons
- Pagination ready

#### Charts
- Doughnut chart for application status
- Bar chart for complaint categories

---

## 12. Testing

### 12.1 API Testing

#### Login Test
```bash
curl -X POST http://localhost:8080/gram-panchayat/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

#### Get Applications Test
```bash
curl -X GET http://localhost:8080/gram-panchayat/api/citizen/applications \
  -H "Authorization: Bearer {token}"
```

### 12.2 Test Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |

---

## 13. Deployment

### 13.1 Prerequisites
- Java 25 or higher
- MySQL 8.0 or higher
- Maven 3.8 or higher

### 13.2 Database Setup
```sql
CREATE DATABASE gram_panchayat_db;
```

### 13.3 Configuration
Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gram_panchayat_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 13.4 Build
```bash
mvn clean package -DskipTests
```

### 13.5 Run
```bash
java -jar target/gram-panchayat-platform-1.0.0.war
```

### 13.6 Access
```
URL: http://localhost:8080/gram-panchayat
```

---

## 14. Future Enhancements

### 14.1 Short-term
1. SMS notifications integration
2. Email notifications
3. File upload for applications
4. Payment gateway integration (Razorpay)
5. Mobile application

### 14.2 Medium-term
1. Multi-language support
2. Offline mode capability
3. Digital signature integration
4. Biometric authentication
5. Integration with state portals

### 14.3 Long-term
1. AI-based scheme recommendations
2. Chatbot for citizen support
3. Blockchain for certificate verification
4. IoT integration for smart village
5. Data analytics dashboard

---

## 15. Conclusion

The Smart Village Digital Gram Panchayat Platform successfully addresses the challenges faced by rural citizens in accessing government services. By digitizing the operations of Gram Panchayats, the platform:

1. **Improves Accessibility**: Citizens can access services 24/7 from anywhere
2. **Ensures Transparency**: All processes are trackable and visible
3. **Reduces Processing Time**: Digital workflows are faster than manual processes
4. **Enhances Accountability**: Admin actions are logged and traceable
5. **Promotes Digital Literacy**: Encourages citizens to use digital services

The platform is built using modern technologies and follows best practices in software development. It is scalable, maintainable, and can be extended with additional features as needed.

---

## Appendix

### A. Glossary

| Term | Definition |
|------|------------|
| Gram Panchayat | Village-level local self-government in India |
| JWT | JSON Web Token for secure authentication |
| JPA | Java Persistence API for database operations |
| DTO | Data Transfer Object for data exchange |
| REST | Representational State Transfer API architecture |

### B. References

1. Spring Boot Documentation: https://spring.io/projects/spring-boot
2. Spring Security Reference: https://docs.spring.io/spring-security/reference/
3. JWT.io: https://jwt.io/
4. Bootstrap Documentation: https://getbootstrap.com/docs/

### C. License

This project is developed for educational purposes as a mini-project demonstration.

---

**Project Developed By**: [Your Name]  
**Institution**: [Your Institution]  
**Date**: April 2026
