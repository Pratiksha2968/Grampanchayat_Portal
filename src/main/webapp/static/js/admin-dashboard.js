// Get context path from the current URL
const CONTEXT_PATH = window.location.pathname.split('/').slice(0, 2).join('/');

// Authentication check
const token = localStorage.getItem('token');
const user = JSON.parse(localStorage.getItem('user') || '{}');

if (!token || !user.username) {
    window.location.href = CONTEXT_PATH + '/login';
}

// Check if user is admin
if (user.role !== 'ADMIN' && user.role !== 'SUPER_ADMIN') {
    alert('Access denied. Admin privileges required.');
    window.location.href = CONTEXT_PATH + '/login';
}

// Set user info
document.getElementById('userInfo').textContent = `Admin: ${user.username}`;

// API headers
const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
};

// Load dashboard data on page load
document.addEventListener('DOMContentLoaded', function() {
    loadDashboardData();
    initCharts();
});

function showSection(sectionName) {
    // Hide all sections
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });
    
    // Show selected section
    document.getElementById(sectionName + '-section').style.display = 'block';
    
    // Update active nav
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });
    event.target.classList.add('active');
    
    // Load section data
    switch(sectionName) {
        case 'applications':
            loadAllApplications();
            break;
        case 'complaints':
            loadComplaints();
            break;
        case 'certificates':
            loadCertificates();
            break;
        case 'schemes':
            loadSchemes();
            break;
        case 'transparency':
            loadTransparency();
            break;
        case 'payments':
            loadPayments();
            break;
        case 'analytics':
            loadAnalytics();
            break;
    }
}

async function loadDashboardData() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/admin/stats', { headers });
        const stats = await response.json();
        
        document.getElementById('totalApplications').textContent = stats.totalApplications || 0;
        document.getElementById('pendingApplications').textContent = stats.pendingApplications || 0;
        document.getElementById('openComplaints').textContent = stats.openComplaints || 0;
        document.getElementById('monthlyCollection').textContent = '₹' + (stats.monthlyCollection || 0);
        
    } catch (error) {
        console.error('Error loading dashboard data:', error);
        document.getElementById('totalApplications').textContent = '0';
        document.getElementById('pendingApplications').textContent = '0';
        document.getElementById('openComplaints').textContent = '0';
        document.getElementById('monthlyCollection').textContent = '₹0';
    }
}

let applicationChart, complaintChart;

function initCharts() {
    const appCtx = document.getElementById('applicationChart').getContext('2d');
    applicationChart = new Chart(appCtx, {
        type: 'doughnut',
        data: {
            labels: ['Pending', 'Approved', 'Rejected'],
            datasets: [{
                data: [0, 0, 0],
                backgroundColor: ['#ffc107', '#28a745', '#dc3545']
            }]
        }
    });
    
    const compCtx = document.getElementById('complaintChart').getContext('2d');
    complaintChart = new Chart(compCtx, {
        type: 'bar',
        data: {
            labels: ['Water', 'Road', 'Electricity', 'Sanitation', 'Other'],
            datasets: [{
                label: 'Complaints',
                data: [0, 0, 0, 0, 0],
                backgroundColor: '#007bff'
            }]
        }
    });
}

async function loadAllApplications() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/admin/applications', { headers });
        const applications = await response.json();
        
        const tbody = document.getElementById('applicationsTable');
        
        if (!applications || applications.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" class="text-center">No applications found.</td></tr>';
            return;
        }
        
        let html = '';
        applications.forEach(app => {
            const statusBadge = getStatusBadge(app.status);
            html += `
                <tr>
                    <td>${app.applicationNumber || app.id}</td>
                    <td>${app.applicantName || app.user?.fullName || 'N/A'}</td>
                    <td>${(app.applicationType || '').replace('_', ' ')}</td>
                    <td>${statusBadge}</td>
                    <td>${app.appliedDate ? new Date(app.appliedDate).toLocaleDateString() : 'N/A'}</td>
                    <td>${app.priority || 'Normal'}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary" onclick="openProcessModal(${app.id})">
                            <i class="fas fa-edit"></i> Process
                        </button>
                    </td>
                </tr>
            `;
        });
        
        tbody.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading applications:', error);
        document.getElementById('applicationsTable').innerHTML = '<tr><td colspan="7" class="text-center">Error loading applications.</td></tr>';
    }
}

async function loadPendingApplications() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/admin/applications/pending', { headers });
        const applications = await response.json();
        
        const tbody = document.getElementById('applicationsTable');
        
        if (!applications || applications.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" class="text-center">No pending applications.</td></tr>';
            return;
        }
        
        let html = '';
        applications.forEach(app => {
            const statusBadge = getStatusBadge(app.status);
            html += `
                <tr>
                    <td>${app.applicationNumber || app.id}</td>
                    <td>${app.applicantName || app.user?.fullName || 'N/A'}</td>
                    <td>${(app.applicationType || '').replace('_', ' ')}</td>
                    <td>${statusBadge}</td>
                    <td>${app.appliedDate ? new Date(app.appliedDate).toLocaleDateString() : 'N/A'}</td>
                    <td>${app.priority || 'Normal'}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary" onclick="openProcessModal(${app.id})">
                            <i class="fas fa-edit"></i> Process
                        </button>
                    </td>
                </tr>
            `;
        });
        
        tbody.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading pending applications:', error);
    }
}

function getStatusBadge(status) {
    const badges = {
        'PENDING': '<span class="badge bg-warning">Pending</span>',
        'APPROVED': '<span class="badge bg-success">Approved</span>',
        'REJECTED': '<span class="badge bg-danger">Rejected</span>'
    };
    return badges[status] || '<span class="badge bg-secondary">Unknown</span>';
}

function openProcessModal(applicationId) {
    document.getElementById('applicationId').value = applicationId;
    document.getElementById('status').value = '';
    document.getElementById('remarks').value = '';
    new bootstrap.Modal(document.getElementById('processModal')).show();
}

async function processApplication() {
    const applicationId = document.getElementById('applicationId').value;
    const status = document.getElementById('status').value;
    const remarks = document.getElementById('remarks').value;
    
    if (!status || !remarks) {
        alert('Please fill in all fields');
        return;
    }
    
    try {
        const response = await fetch(CONTEXT_PATH + '/api/admin/applications/' + applicationId + '/process', {
            method: 'PUT',
            headers: headers,
            body: JSON.stringify({ status, remarks })
        });
        
        if (response.ok) {
            alert('Application processed successfully!');
            bootstrap.Modal.getInstance(document.getElementById('processModal')).hide();
            loadAllApplications();
            loadDashboardData();
        } else {
            const error = await response.text();
            alert('Error processing application: ' + error);
        }
    } catch (error) {
        console.error('Error processing application:', error);
        alert('Error processing application.');
    }
}

async function loadComplaints() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/admin/complaints', { headers });
        const complaints = await response.json();
        
        const section = document.getElementById('complaints-section');
        
        let html = `
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Complaints Management</h1>
            </div>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Complainant</th>
                            <th>Category</th>
                            <th>Subject</th>
                            <th>Status</th>
                            <th>Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
        `;
        
        if (!complaints || complaints.length === 0) {
            html += '<tr><td colspan="7" class="text-center">No complaints found.</td></tr>';
        } else {
            complaints.forEach(comp => {
                const statusBadge = getComplaintStatusBadge(comp.status);
                html += `
                    <tr>
                        <td>${comp.id}</td>
                        <td>${comp.complainantName || 'N/A'}</td>
                        <td>${comp.category || 'N/A'}</td>
                        <td>${comp.subject || 'N/A'}</td>
                        <td>${statusBadge}</td>
                        <td>${comp.createdAt ? new Date(comp.createdAt).toLocaleDateString() : 'N/A'}</td>
                        <td>
                            <button class="btn btn-sm btn-outline-success" onclick="resolveComplaint(${comp.id})">
                                <i class="fas fa-check"></i> Resolve
                            </button>
                        </td>
                    </tr>
                `;
            });
        }
        
        html += '</tbody></table></div>';
        section.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading complaints:', error);
        document.getElementById('complaints-section').innerHTML = '<p class="text-danger">Error loading complaints.</p>';
    }
}

function getComplaintStatusBadge(status) {
    const badges = {
        'OPEN': '<span class="badge bg-danger">Open</span>',
        'IN_PROGRESS': '<span class="badge bg-warning">In Progress</span>',
        'RESOLVED': '<span class="badge bg-success">Resolved</span>'
    };
    return badges[status] || '<span class="badge bg-secondary">Unknown</span>';
}

async function resolveComplaint(complaintId) {
    const resolution = prompt('Enter resolution details:');
    if (!resolution) return;
    
    try {
        const response = await fetch(CONTEXT_PATH + '/api/admin/complaints/' + complaintId + '/status?status=RESOLVED&resolution=' + encodeURIComponent(resolution), {
            method: 'PUT',
            headers: headers
        });
        
        if (response.ok) {
            alert('Complaint resolved successfully!');
            loadComplaints();
        } else {
            alert('Error resolving complaint.');
        }
    } catch (error) {
        console.error('Error resolving complaint:', error);
        alert('Error resolving complaint.');
    }
}

async function loadCertificates() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/certificates', { headers });
        const certificates = await response.json();
        
        const section = document.getElementById('certificates-section');
        
        let html = `
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Certificates Management</h1>
            </div>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Certificate No.</th>
                            <th>Type</th>
                            <th>Applicant</th>
                            <th>Issue Date</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
        `;
        
        if (!certificates || certificates.length === 0) {
            html += '<tr><td colspan="6" class="text-center">No certificates found.</td></tr>';
        } else {
            certificates.forEach(cert => {
                html += `
                    <tr>
                        <td>${cert.certificateNumber || cert.id}</td>
                        <td>${cert.certificateType || 'N/A'}</td>
                        <td>${cert.applicantName || 'N/A'}</td>
                        <td>${cert.issueDate ? new Date(cert.issueDate).toLocaleDateString() : 'N/A'}</td>
                        <td><span class="badge bg-success">Issued</span></td>
                        <td>
                            <button class="btn btn-sm btn-outline-primary" onclick="viewCertificate('${cert.certificateNumber || cert.id}')">
                                <i class="fas fa-eye"></i> View
                            </button>
                        </td>
                    </tr>
                `;
            });
        }
        
        html += '</tbody></table></div>';
        section.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading certificates:', error);
        document.getElementById('certificates-section').innerHTML = '<p class="text-danger">Error loading certificates.</p>';
    }
}

function viewCertificate(certNumber) {
    alert('Certificate ' + certNumber + ' details would be shown here.');
}

async function loadSchemes() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/schemes', { headers });
        const schemes = await response.json();
        
        const section = document.getElementById('schemes-section');
        
        let html = `
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Government Schemes</h1>
                <button class="btn btn-primary" onclick="showAddSchemeForm()">
                    <i class="fas fa-plus"></i> Add Scheme
                </button>
            </div>
            <div class="row">
        `;
        
        if (!schemes || schemes.length === 0) {
            html += '<div class="col-12"><p class="text-center">No schemes found.</p></div>';
        } else {
            schemes.forEach(scheme => {
                html += `
                    <div class="col-md-4 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${scheme.name || 'N/A'}</h5>
                                <p class="card-text">${scheme.description || 'No description'}</p>
                                <p><strong>Category:</strong> ${scheme.category || 'N/A'}</p>
                                <p><strong>Eligibility:</strong> ${scheme.eligibilityCriteria || 'N/A'}</p>
                                <span class="badge ${scheme.active ? 'bg-success' : 'bg-secondary'}">${scheme.active ? 'Active' : 'Inactive'}</span>
                            </div>
                        </div>
                    </div>
                `;
            });
        }
        
        html += '</div>';
        section.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading schemes:', error);
        document.getElementById('schemes-section').innerHTML = '<p class="text-danger">Error loading schemes.</p>';
    }
}

function showAddSchemeForm() {
    alert('Add scheme form would be shown here.');
}

async function loadTransparency() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/transparency/stats', { headers });
        const stats = await response.json();
        
        const section = document.getElementById('transparency-section');
        
        let html = `
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Transparency & RTI</h1>
            </div>
            <div class="row">
                <div class="col-md-3 mb-3">
                    <div class="card text-white bg-primary">
                        <div class="card-body">
                            <h4>${stats.totalRecords || 0}</h4>
                            <p>Total Records</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 mb-3">
                    <div class="card text-white bg-success">
                        <div class="card-body">
                            <h4>₹${stats.totalFunds || 0}</h4>
                            <p>Total Funds</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 mb-3">
                    <div class="card text-white bg-info">
                        <div class="card-body">
                            <h4>${stats.projectsCompleted || 0}</h4>
                            <p>Projects Completed</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 mb-3">
                    <div class="card text-white bg-warning">
                        <div class="card-body">
                            <h4>${stats.projectsOngoing || 0}</h4>
                            <p>Ongoing Projects</p>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        section.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading transparency:', error);
        document.getElementById('transparency-section').innerHTML = '<p class="text-danger">Error loading transparency data.</p>';
    }
}

function loadAnalytics() {
    try {
        fetch(CONTEXT_PATH + '/api/admin/analytics/applications', { headers })
            .then(res => res.json())
            .then(appData => {
                const section = document.getElementById('analytics-section');
                
                let html = `
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">Analytics & Reports</h1>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header"><h5>Applications by Type</h5></div>
                                <div class="card-body">
                                    <ul class="list-group">
                `;
                
                for (const [type, count] of Object.entries(appData || {})) {
                    html += `<li class="list-group-item d-flex justify-content-between">${type}: <span class="badge bg-primary">${count}</span></li>`;
                }
                
                html += `
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card">
                                <div class="card-header"><h5>Payment Summary</h5></div>
                                <div class="card-body">
                                    <p class="text-muted">Payment analytics data</p>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                
                section.innerHTML = html;
            })
            .catch(error => {
                console.error('Error loading analytics:', error);
                document.getElementById('analytics-section').innerHTML = '<p class="text-danger">Error loading analytics.</p>';
            });
    } catch (error) {
        console.error('Error loading analytics:', error);
    }
}

async function loadPayments() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/admin/payments', { headers });
        const payments = await response.json();
        
        const section = document.getElementById('payments-section');
        
        let html = `
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Payments Management</h1>
            </div>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Transaction ID</th>
                            <th>User</th>
                            <th>Type</th>
                            <th>Amount</th>
                            <th>Status</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
        `;
        
        if (!payments || payments.length === 0) {
            html += '<tr><td colspan="6" class="text-center">No payments found.</td></tr>';
        } else {
            payments.forEach(pay => {
                const statusBadge = pay.status === 'COMPLETED' ? 
                    '<span class="badge bg-success">Completed</span>' : 
                    '<span class="badge bg-warning">Pending</span>';
                html += `
                    <tr>
                        <td>${pay.transactionId || pay.id}</td>
                        <td>${pay.user?.fullName || pay.user?.username || 'N/A'}</td>
                        <td>${(pay.paymentType || '').replace('_', ' ')}</td>
                        <td>₹${pay.amount || 0}</td>
                        <td>${statusBadge}</td>
                        <td>${pay.paymentDate ? new Date(pay.paymentDate).toLocaleDateString() : 'N/A'}</td>
                    </tr>
                `;
            });
        }
        
        html += '</tbody></table></div>';
        section.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading payments:', error);
        document.getElementById('payments-section').innerHTML = '<p class="text-danger">Error loading payments.</p>';
    }
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = CONTEXT_PATH + '/login';
}
