// Get context path from the current URL
const CONTEXT_PATH = window.location.pathname.split('/').slice(0, 2).join('/');

// Authentication check
const token = localStorage.getItem('token');
const user = JSON.parse(localStorage.getItem('user') || '{}');

if (!token || !user.username) {
    window.location.href = CONTEXT_PATH + '/login';
}

// Set user info
document.getElementById('userInfo').textContent = `Welcome, ${user.username}`;

// API headers
const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
};

// Load dashboard data on page load
document.addEventListener('DOMContentLoaded', function() {
    loadDashboardData();
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
            loadApplications();
            break;
        case 'complaints':
            loadComplaints();
            break;
        case 'certificates':
            loadCertificates();
            break;
        case 'payments':
            loadPayments();
            break;
        case 'schemes':
            loadSchemes();
            break;
    }
}

async function loadDashboardData() {
    try {
        const [applications, complaints, certificates, payments, schemes] = await Promise.all([
            fetch(CONTEXT_PATH + '/api/citizen/applications', { headers }).catch(() => ({ json: () => [] })),
            fetch(CONTEXT_PATH + '/api/citizen/complaints', { headers }).catch(() => ({ json: () => [] })),
            fetch(CONTEXT_PATH + '/api/citizen/certificates', { headers }).catch(() => ({ json: () => [] })),
            fetch(CONTEXT_PATH + '/api/citizen/payments', { headers }).catch(() => ({ json: () => [] })),
            fetch(CONTEXT_PATH + '/api/citizen/schemes/recommended', { headers }).catch(() => ({ json: () => [] }))
        ]);
        
        const [appData, compData, certData, payData, schemeData] = await Promise.all([
            applications.json(),
            complaints.json(),
            certificates.json(),
            payments.json(),
            schemes.json()
        ]);
        
        // Update dashboard cards
        document.getElementById('totalApplications').textContent = appData.length || 0;
        document.getElementById('totalComplaints').textContent = compData.length || 0;
        document.getElementById('totalCertificates').textContent = certData.length || 0;
        document.getElementById('totalPayments').textContent = payData.length || 0;
        
        // Load recommended schemes
        loadRecommendedSchemes(schemeData);
        
    } catch (error) {
        console.error('Error loading dashboard data:', error);
    }
}

function loadRecommendedSchemes(schemes) {
    const container = document.getElementById('recommendedSchemes');
    
    if (!schemes || schemes.length === 0) {
        container.innerHTML = '<p class="text-muted">No recommended schemes available.</p>';
        return;
    }
    
    let html = '';
    schemes.slice(0, 3).forEach(scheme => {
        html += `
            <div class="mb-2">
                <h6>${scheme.name}</h6>
                <p class="text-muted small">${scheme.description}</p>
            </div>
        `;
    });
    
    container.innerHTML = html;
}

async function loadApplications() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/applications', { headers });
        const applications = await response.json();
        
        const tbody = document.getElementById('applicationsTable');
        
        if (!applications || applications.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">No applications found.</td></tr>';
            return;
        }
        
        let html = '';
        applications.forEach(app => {
            const statusBadge = getStatusBadge(app.status);
            html += `
                <tr>
                    <td>${app.applicationNumber || app.id}</td>
                    <td>${(app.applicationType || '').replace('_', ' ')}</td>
                    <td>${statusBadge}</td>
                    <td>${app.appliedDate ? new Date(app.appliedDate).toLocaleDateString() : 'N/A'}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary" onclick="viewApplication(${app.id})">
                            <i class="fas fa-eye"></i>
                        </button>
                    </td>
                </tr>
            `;
        });
        
        tbody.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading applications:', error);
        document.getElementById('applicationsTable').innerHTML = '<tr><td colspan="5" class="text-center">Error loading applications.</td></tr>';
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

async function submitApplication() {
    const form = document.getElementById('applicationForm');
    const formData = new FormData(form);
    
    const data = {
        applicationType: formData.get('applicationType'),
        details: formData.get('details')
    };
    
    if (!data.applicationType) {
        alert('Please select application type');
        return;
    }
    
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/applications', {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            alert('Application submitted successfully!');
            form.reset();
            bootstrap.Modal.getInstance(document.getElementById('applicationModal')).hide();
            loadApplications();
            loadDashboardData();
        } else {
            const error = await response.text();
            alert('Error submitting application: ' + error);
        }
    } catch (error) {
        console.error('Error submitting application:', error);
        alert('Error submitting application. Please try again.');
    }
}

function viewApplication(id) {
    alert('Application details for ID: ' + id);
}

async function loadComplaints() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/complaints', { headers });
        const complaints = await response.json();
        
        const tbody = document.getElementById('complaintsTable');
        
        if (!complaints || complaints.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">No complaints found.</td></tr>';
            return;
        }
        
        let html = '';
        complaints.forEach(comp => {
            const statusBadge = getComplaintStatusBadge(comp.status);
            html += `
                <tr>
                    <td>${comp.id}</td>
                    <td>${comp.category || 'N/A'}</td>
                    <td>${comp.subject || 'N/A'}</td>
                    <td>${statusBadge}</td>
                    <td>${comp.createdAt ? new Date(comp.createdAt).toLocaleDateString() : 'N/A'}</td>
                </tr>
            `;
        });
        
        tbody.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading complaints:', error);
        document.getElementById('complaintsTable').innerHTML = '<tr><td colspan="5" class="text-center">Error loading complaints.</td></tr>';
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

async function submitComplaint() {
    const form = document.getElementById('complaintForm');
    const formData = new FormData(form);
    
    const data = {
        category: formData.get('category'),
        subject: formData.get('subject'),
        description: formData.get('description')
    };
    
    if (!data.category || !data.subject || !data.description) {
        alert('Please fill in all fields');
        return;
    }
    
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/complaints', {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            alert('Complaint submitted successfully!');
            form.reset();
            bootstrap.Modal.getInstance(document.getElementById('complaintModal')).hide();
            loadComplaints();
            loadDashboardData();
        } else {
            const error = await response.text();
            alert('Error submitting complaint: ' + error);
        }
    } catch (error) {
        console.error('Error submitting complaint:', error);
        alert('Error submitting complaint. Please try again.');
    }
}

async function loadCertificates() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/certificates', { headers });
        const certificates = await response.json();
        
        const tbody = document.getElementById('certificatesTable');
        
        if (!certificates || certificates.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">No certificates found. Your approved applications will generate certificates automatically.</td></tr>';
            return;
        }
        
        let html = '';
        certificates.forEach(cert => {
            html += `
                <tr>
                    <td>${cert.certificateNumber || cert.id}</td>
                    <td>${(cert.certificateType || '').replace('_', ' ')}</td>
                    <td>${cert.issueDate ? new Date(cert.issueDate).toLocaleDateString() : 'N/A'}</td>
                    <td><span class="badge bg-success">Issued</span></td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary" onclick="downloadCertificate('${cert.certificateNumber || cert.id}')">
                            <i class="fas fa-download"></i> Download
                        </button>
                    </td>
                </tr>
            `;
        });
        
        tbody.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading certificates:', error);
        document.getElementById('certificatesTable').innerHTML = '<tr><td colspan="5" class="text-center">Error loading certificates.</td></tr>';
    }
}

function downloadCertificate(certNumber) {
    alert('Certificate ' + certNumber + ' download would start here.');
}

async function loadPayments() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/payments', { headers });
        const payments = await response.json();
        
        const tbody = document.getElementById('paymentsTable');
        
        if (!payments || payments.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">No payment history found.</td></tr>';
            return;
        }
        
        let html = '';
        payments.forEach(pay => {
            const statusBadge = pay.status === 'COMPLETED' ? 
                '<span class="badge bg-success">Completed</span>' : 
                '<span class="badge bg-warning">Pending</span>';
            html += `
                <tr>
                    <td>${pay.transactionId || pay.id}</td>
                    <td>${(pay.paymentType || '').replace('_', ' ')}</td>
                    <td>₹${pay.amount || 0}</td>
                    <td>${statusBadge}</td>
                    <td>${pay.paymentDate ? new Date(pay.paymentDate).toLocaleDateString() : 'N/A'}</td>
                </tr>
            `;
        });
        
        tbody.innerHTML = html;
        
    } catch (error) {
        console.error('Error loading payments:', error);
        document.getElementById('paymentsTable').innerHTML = '<tr><td colspan="5" class="text-center">Error loading payments.</td></tr>';
    }
}

async function submitPayment() {
    const form = document.getElementById('paymentForm');
    const formData = new FormData(form);
    
    const paymentType = formData.get('paymentType');
    const amount = formData.get('amount');
    
    if (!paymentType || !amount) {
        alert('Please fill in all fields');
        return;
    }
    
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/payments?paymentType=' + paymentType + '&amount=' + amount, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        if (response.ok) {
            alert('Payment initiated successfully!');
            form.reset();
            bootstrap.Modal.getInstance(document.getElementById('paymentModal')).hide();
            loadPayments();
            loadDashboardData();
        } else {
            const error = await response.text();
            alert('Error processing payment: ' + error);
        }
    } catch (error) {
        console.error('Error processing payment:', error);
        alert('Error processing payment. Please try again.');
    }
}

async function loadSchemes() {
    try {
        const response = await fetch(CONTEXT_PATH + '/api/citizen/schemes', { headers });
        const schemes = await response.json();
        
        const container = document.getElementById('schemesContainer');
        
        if (!schemes || schemes.length === 0) {
            container.innerHTML = '<p class="text-center">No schemes available.</p>';
            return;
        }
        
        let html = '';
        schemes.forEach(scheme => {
            html += `
                <div class="col-md-6 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${scheme.name}</h5>
                            <p class="card-text">${scheme.description || 'No description available'}</p>
                            <p><strong>Category:</strong> ${scheme.category || 'N/A'}</p>
                            <p><strong>Eligibility:</strong> ${scheme.eligibilityCriteria || 'N/A'}</p>
                            ${scheme.maxIncome ? `<p><strong>Max Income:</strong> ₹${scheme.maxIncome}</p>` : ''}
                            ${scheme.minAge || scheme.maxAge ? `<p><strong>Age:</strong> ${scheme.minAge || 0} - ${scheme.maxAge || 'Any'} years</p>` : ''}
                            <span class="badge ${scheme.active ? 'bg-success' : 'bg-secondary'}">${scheme.active ? 'Active' : 'Inactive'}</span>
                        </div>
                    </div>
                </div>
            `;
        });
        
        container.innerHTML = '<div class="row">' + html + '</div>';
        
    } catch (error) {
        console.error('Error loading schemes:', error);
        document.getElementById('schemesContainer').innerHTML = '<p class="text-danger">Error loading schemes.</p>';
    }
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = CONTEXT_PATH + '/login';
}
