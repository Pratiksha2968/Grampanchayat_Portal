<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Smart Village Platform</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <i class="fas fa-building"></i> Admin Panel - Smart Village
            </a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3" id="userInfo"></span>
                <button class="btn btn-outline-light" onclick="logout()">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </button>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
                <div class="position-sticky pt-3">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="#" onclick="showSection('dashboard')">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="showSection('applications')">
                                <i class="fas fa-file-alt"></i> Applications
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="showSection('complaints')">
                                <i class="fas fa-exclamation-triangle"></i> Complaints
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="showSection('certificates')">
                                <i class="fas fa-certificate"></i> Certificates
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="showSection('schemes')">
                                <i class="fas fa-gift"></i> Schemes
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="showSection('transparency')">
                                <i class="fas fa-chart-line"></i> Transparency
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="showSection('payments')">
                                <i class="fas fa-credit-card"></i> Payments
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="showSection('analytics')">
                                <i class="fas fa-chart-bar"></i> Analytics
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- Main content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <!-- Dashboard Section -->
                <div id="dashboard-section" class="content-section">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">Admin Dashboard</h1>
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <div class="btn-group me-2">
                                <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-3 mb-3">
                            <div class="card text-white bg-primary">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <h4 id="totalApplications">0</h4>
                                            <p>Total Applications</p>
                                        </div>
                                        <i class="fas fa-file-alt fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 mb-3">
                            <div class="card text-white bg-warning">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <h4 id="pendingApplications">0</h4>
                                            <p>Pending Applications</p>
                                        </div>
                                        <i class="fas fa-clock fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 mb-3">
                            <div class="card text-white bg-danger">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <h4 id="openComplaints">0</h4>
                                            <p>Open Complaints</p>
                                        </div>
                                        <i class="fas fa-exclamation-triangle fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 mb-3">
                            <div class="card text-white bg-success">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <h4 id="monthlyCollection">₹0</h4>
                                            <p>Monthly Collection</p>
                                        </div>
                                        <i class="fas fa-rupee-sign fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row mt-4">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5>Application Status Distribution</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="applicationChart"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5>Complaint Categories</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="complaintChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Applications Section -->
                <div id="applications-section" class="content-section" style="display: none;">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">Applications Management</h1>
                        <div class="btn-group">
                            <button class="btn btn-outline-primary" onclick="loadPendingApplications()">
                                <i class="fas fa-clock"></i> Pending Only
                            </button>
                            <button class="btn btn-outline-primary" onclick="loadAllApplications()">
                                <i class="fas fa-list"></i> All Applications
                            </button>
                        </div>
                    </div>
                    
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Application No.</th>
                                    <th>Applicant</th>
                                    <th>Type</th>
                                    <th>Status</th>
                                    <th>Applied Date</th>
                                    <th>Priority</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="applicationsTable">
                                <tr>
                                    <td colspan="7" class="text-center">Loading...</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Complaints Section -->
                <div id="complaints-section" class="content-section" style="display: none;">
                    <p>Loading complaints...</p>
                </div>

                <!-- Certificates Section -->
                <div id="certificates-section" class="content-section" style="display: none;">
                    <p>Loading certificates...</p>
                </div>

                <!-- Schemes Section -->
                <div id="schemes-section" class="content-section" style="display: none;">
                    <p>Loading schemes...</p>
                </div>

                <!-- Transparency Section -->
                <div id="transparency-section" class="content-section" style="display: none;">
                    <p>Loading transparency data...</p>
                </div>

                <!-- Analytics Section -->
                <div id="analytics-section" class="content-section" style="display: none;">
                    <p>Loading analytics...</p>
                </div>

                <!-- Payments Section -->
                <div id="payments-section" class="content-section" style="display: none;">
                    <p>Loading payments...</p>
                </div>
            </main>
        </div>
    </div>

    <!-- Application Process Modal -->
    <div class="modal fade" id="processModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Process Application</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="processForm">
                        <input type="hidden" id="applicationId" name="applicationId">
                        <div class="mb-3">
                            <label for="status" class="form-label">Status</label>
                            <select class="form-select" id="status" name="status" required>
                                <option value="">Select Status</option>
                                <option value="APPROVED">Approve</option>
                                <option value="REJECTED">Reject</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="remarks" class="form-label">Remarks</label>
                            <textarea class="form-control" id="remarks" name="remarks" rows="3" required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="processApplication()">Process</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/admin-dashboard.js"></script>
</body>
</html>