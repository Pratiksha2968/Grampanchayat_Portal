# Contributing to Smart Village Platform

Thank you for your interest in contributing to the Smart Village Digital Gram Panchayat Platform!

## How to Contribute

### Reporting Bugs
If you find a bug, please create an issue with:
- A clear title
- Steps to reproduce
- Expected behavior
- Actual behavior
- Screenshots (if applicable)

### Suggesting Features
We welcome feature suggestions! Please create an issue with:
- A clear title
- Detailed description of the feature
- Use case scenarios
- Possible implementation approach

### Pull Requests

1. **Fork the Repository**
   ```bash
   git clone https://github.com/your-username/smart-village-platform.git
   ```

2. **Create a Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Your Changes**
   - Follow the existing code style
   - Add comments where necessary
   - Update documentation if needed

4. **Test Your Changes**
   ```bash
   mvn clean package -DskipTests
   java -jar target/gram-panchayat-platform-1.0.0.war
   ```

5. **Commit Your Changes**
   ```bash
   git add .
   git commit -m "Description of your changes"
   ```

6. **Push to Your Fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create a Pull Request**
   - Go to the original repository
   - Click "New Pull Request"
   - Select your branch
   - Fill in the PR template

## Code Style Guidelines

### Java
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add Javadoc comments for public methods
- Keep methods focused and concise

### JavaScript
- Use ES6+ features
- Follow camelCase naming
- Add comments for complex logic

### JSP/HTML
- Use semantic HTML elements
- Follow Bootstrap conventions
- Keep templates clean and readable

## Development Setup

1. Install Java 25
2. Install MySQL 8.0
3. Install Maven 3.8
4. Clone the repository
5. Configure database in application.properties
6. Run `mvn clean package -DskipTests`
7. Deploy the WAR file

## Questions?

Feel free to open an issue for any questions or clarifications.

Thank you for contributing!
