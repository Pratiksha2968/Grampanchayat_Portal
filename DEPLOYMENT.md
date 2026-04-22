# Deployment Guide

## Prerequisites

- Java 25 or higher
- MySQL 8.0 or higher
- Maven 3.8 or higher (for building)
- Docker (optional, for containerized deployment)

## Option 1: Traditional Deployment

### Step 1: Database Setup
```sql
-- Login to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE gram_panchayat_db;

-- Create user (optional)
CREATE USER 'grampanchayat'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON gram_panchayat_db.* TO 'grampanchayat'@'localhost';
FLUSH PRIVILEGES;
```

### Step 2: Configure Application
Copy the example properties file and update with your settings:
```bash
cp application-example.properties src/main/resources/application.properties
```

Edit `application.properties` with your database credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_secure_jwt_secret_key
```

### Step 3: Build the Application
```bash
mvn clean package -DskipTests
```

### Step 4: Run the Application
```bash
java -jar target/gram-panchayat-platform-1.0.0.war
```

### Step 5: Access the Application
Open your browser and navigate to:
```
http://localhost:8080/gram-panchayat
```

Default admin credentials:
- Username: `admin`
- Password: `admin123`

---

## Option 2: Docker Deployment

### Step 1: Build and Run with Docker Compose
```bash
docker-compose up -d
```

This will:
- Start a MySQL container
- Build and start the application container
- Create necessary volumes for data persistence

### Step 2: Check Container Status
```bash
docker-compose ps
```

### Step 3: View Logs
```bash
docker-compose logs -f app
```

### Step 4: Stop Containers
```bash
docker-compose down
```

---

## Option 3: Cloud Deployment

### Deploy to AWS EC2

1. **Launch EC2 Instance**
   - Choose Amazon Linux 2 or Ubuntu
   - t2.medium or larger recommended
   - Open ports 22 (SSH) and 8080 (HTTP)

2. **Install Java**
   ```bash
   sudo yum install java-25-openjdk -y
   # or on Ubuntu
   sudo apt install openjdk-25-jdk -y
   ```

3. **Install MySQL**
   ```bash
   sudo yum install mysql-server -y
   sudo systemctl start mysqld
   sudo mysql_secure_installation
   ```

4. **Create Database**
   ```sql
   mysql -u root -p
   CREATE DATABASE gram_panchayat_db;
   ```

5. **Deploy Application**
   ```bash
   # Upload WAR file to server
   scp target/gram-panchayat-platform-1.0.0.war ec2-user@your-instance-ip:/home/ec2-user/
   
   # SSH into server and run
   ssh ec2-user@your-instance-ip
   java -jar gram-panchayat-platform-1.0.0.war
   ```

### Deploy to Heroku

1. **Install Heroku CLI**
   ```bash
   brew install heroku/brew/heroku
   ```

2. **Login to Heroku**
   ```bash
   heroku login
   ```

3. **Create Heroku App**
   ```bash
   heroku create your-app-name
   ```

4. **Add MySQL Addon**
   ```bash
   heroku addons:create cleardb:ignite
   ```

5. **Deploy**
   ```bash
   git push heroku main
   ```

---

## Production Checklist

### Security
- [ ] Change default admin password
- [ ] Update JWT secret key
- [ ] Enable HTTPS
- [ ] Configure firewall rules
- [ ] Set up SSL certificate

### Database
- [ ] Create dedicated database user
- [ ] Configure database backups
- [ ] Set up connection pooling

### Monitoring
- [ ] Set up application logging
- [ ] Configure error alerts
- [ ] Monitor server resources

### Performance
- [ ] Configure JVM memory settings
- [ ] Enable database connection pooling
- [ ] Set up CDN for static assets

---

## Troubleshooting

### Application won't start
1. Check Java version: `java -version`
2. Check database connection
3. Review application logs

### Database connection failed
1. Verify MySQL is running: `sudo systemctl status mysql`
2. Check credentials in application.properties
3. Verify database exists

### Port 8080 already in use
```bash
# Find process using port
lsof -i :8080
# Kill process
kill -9 <PID>
```

---

## Support

For issues and support, please create an issue on GitHub:
https://github.com/your-username/smart-village-platform/issues
