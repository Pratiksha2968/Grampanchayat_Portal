# Smart Village Digital Gram Panchayat Platform

A comprehensive web-based e-governance solution for Gram Panchayats (village councils) that enables citizens to access government services online.

## Features

- **User Authentication**: Secure login with JWT tokens
- **Application Management**: Submit and track certificate applications
- **Complaint System**: Register and track complaints
- **Certificate Generation**: Auto-generated PDF certificates with QR codes
- **Payment Integration**: Online payment for taxes and fees
- **Government Schemes**: View eligible government schemes
- **Transparency Portal**: Financial transparency and RTI compliance
- **Admin Dashboard**: Complete administrative control panel

## Technology Stack

| Category | Technology |
|----------|------------|
| Backend | Java 25, Spring Boot 3.3.5 |
| Security | Spring Security, JWT |
| Database | MySQL |
| Frontend | JSP, Bootstrap 5, JavaScript |
| Build Tool | Maven |

## Prerequisites

- Java 25 or higher
- MySQL 8.0 or higher
- Maven 3.8 or higher

## Installation

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/smart-village-platform.git
cd smart-village-platform
```

### 2. Database Setup
```sql
CREATE DATABASE gram_panchayat_db;
```

### 3. Configure Application
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gram_panchayat_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build the Project
```bash
mvn clean package -DskipTests
```

### 5. Run the Application
```bash
java -jar target/gram-panchayat-platform-1.0.0.war
```

### 6. Access the Application
Open your browser and navigate to:
```
http://localhost:8080/gram-panchayat
```

## Default Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |

## Project Structure

```
smart-village-platform/
├── src/main/java/com/smartvillage/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST Controllers
│   ├── dto/             # Data Transfer Objects
│   ├── entity/          # JPA Entities
│   ├── repository/      # Data Repositories
│   ├── security/        # Security Components
│   └── service/         # Business Logic
├── src/main/resources/
│   └── application.properties
├── src/main/webapp/
│   ├── WEB-INF/views/   # JSP Pages
│   └── static/          # CSS, JS files
├── pom.xml
└── README.md
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/signup` - User registration

### Citizen APIs
- `GET /api/citizen/applications` - Get user applications
- `POST /api/citizen/applications` - Submit application
- `GET /api/citizen/complaints` - Get user complaints
- `POST /api/citizen/complaints` - Submit complaint
- `GET /api/citizen/certificates` - Get certificates
- `GET /api/citizen/payments` - Get payments
- `POST /api/citizen/payments` - Create payment
- `GET /api/citizen/schemes` - Get schemes

### Admin APIs
- `GET /api/admin/stats` - Dashboard statistics
- `GET /api/admin/applications` - All applications
- `PUT /api/admin/applications/{id}/process` - Process application
- `GET /api/admin/complaints` - All complaints
- `PUT /api/admin/complaints/{id}/status` - Update complaint
- `GET /api/admin/payments` - All payments

## Screenshots

### Home Page
Landing page with platform overview and quick links.

### Login Page
Secure authentication with username and password.

### Citizen Dashboard
Personal dashboard showing applications, complaints, certificates, and payments.

### Admin Dashboard
Administrative panel with statistics, charts, and management features.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Boot Documentation
- Bootstrap Framework
- Chart.js Library
- iText PDF Library
- ZXing QR Code Library

## Contact

For any queries or support, please contact:
- Email: your-email@example.com
- GitHub Issues: [Project Issues](https://github.com/your-username/smart-village-platform/issues)
