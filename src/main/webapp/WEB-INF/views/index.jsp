<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Smart Village Digital Gram Panchayat Platform</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="fas fa-building"></i> Smart Village Platform
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/transparency">Transparency</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/certificate/verify">Verify Certificate</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/signup">Register</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero-section bg-light py-5">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <h1 class="display-4 fw-bold text-primary">Digital Gram Panchayat</h1>
                    <p class="lead">Empowering rural communities through digital governance and transparent administration.</p>
                    <div class="mt-4">
                        <a href="${pageContext.request.contextPath}/signup" class="btn btn-primary btn-lg me-3">Get Started</a>
                        <a href="${pageContext.request.contextPath}/transparency" class="btn btn-outline-primary btn-lg">View Transparency</a>
                    </div>
                </div>
                <div class="col-lg-6">
                    <img src="${pageContext.request.contextPath}/images/village-hero.jpg" alt="Smart Village" class="img-fluid rounded">
                </div>
            </div>
        </div>
    </section>

    <!-- Services Section -->
    <section class="py-5">
        <div class="container">
            <h2 class="text-center mb-5">Our Services</h2>
            <div class="row g-4">
                <div class="col-md-4">
                    <div class="card h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-certificate fa-3x text-primary mb-3"></i>
                            <h5 class="card-title">Digital Certificates</h5>
                            <p class="card-text">Apply for birth, death, and income certificates online with QR verification.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-exclamation-triangle fa-3x text-warning mb-3"></i>
                            <h5 class="card-title">Complaint Management</h5>
                            <p class="card-text">Register and track complaints about water, roads, electricity, and more.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-credit-card fa-3x text-success mb-3"></i>
                            <h5 class="card-title">Online Payments</h5>
                            <p class="card-text">Pay property taxes and application fees securely online.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-gift fa-3x text-info mb-3"></i>
                            <h5 class="card-title">Scheme Recommendations</h5>
                            <p class="card-text">Get personalized government scheme recommendations based on your profile.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-chart-bar fa-3x text-secondary mb-3"></i>
                            <h5 class="card-title">Transparency Dashboard</h5>
                            <p class="card-text">View government funds, expenses, and ongoing projects transparently.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 text-center">
                        <div class="card-body">
                            <i class="fas fa-bell fa-3x text-danger mb-3"></i>
                            <h5 class="card-title">Real-time Notifications</h5>
                            <p class="card-text">Get instant updates on application status and important announcements.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5>Smart Village Platform</h5>
                    <p>Digitizing rural governance for better transparency and efficiency.</p>
                </div>
                <div class="col-md-6">
                    <h5>Quick Links</h5>
                    <ul class="list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/transparency" class="text-white-50">Transparency</a></li>
                        <li><a href="${pageContext.request.contextPath}/certificate/verify" class="text-white-50">Verify Certificate</a></li>
                        <li><a href="${pageContext.request.contextPath}/login" class="text-white-50">Login</a></li>
                    </ul>
                </div>
            </div>
            <hr>
            <div class="text-center">
                <p>&copy; 2024 Smart Village Platform. All rights reserved.</p>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>