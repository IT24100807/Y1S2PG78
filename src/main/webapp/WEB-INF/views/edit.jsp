<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <!-- Include Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<!-- Navigation Bar -->
<nav class="bg-navy-800 text-white p-4 shadow-md">
    <div class="container mx-auto flex justify-between items-center">
        <div class="flex items-center space-x-2">
            <span class="font-bold text-xl">📚</span>
            <a href="/" class="font-semibold">Dashboard</a>
        </div>
        <div class="flex items-center space-x-4" id="navButtons">
            <!-- Dynamic buttons will be inserted here via JavaScript -->
        </div>
    </div>
</nav>

<!-- Profile Content -->
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold text-center text-navy-800 mb-6">My Profile</h1>

    <div class="max-w-2xl mx-auto bg-white rounded-lg shadow-md p-8">
        <div id="alertMessage" class="hidden mb-4 p-4 rounded"></div>

        <form id="profileForm" class="space-y-6">
            <input type="hidden" id="userId" name="id">

            <div>
                <label for="fullName" class="block text-sm font-medium text-gray-700 mb-1">Full Name</label>
                <input type="text" id="fullName" name="fullName" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-navy-500"
                       pattern="^[A-Za-z\s]{2,50}$" title="Name must be 2-50 characters and contain only letters and spaces" required>
                <p class="text-xs text-gray-500 mt-1">Name must be 2-50 characters, letters and spaces only</p>
            </div>

            <div>
                <label for="email" class="block text-sm font-medium text-gray-700 mb-1">Email Address</label>
                <input type="email" id="email" name="email" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-navy-500"
                       pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" title="Please enter a valid email address" required>
                <p class="text-xs text-gray-500 mt-1">Must be a valid email format</p>
            </div>

            <div>
                <label for="role" class="block text-sm font-medium text-gray-700 mb-1">Role</label>
                <input type="text" id="role" name="role" class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100" readonly>
            </div>

            <div>
                <label for="createdAt" class="block text-sm font-medium text-gray-700 mb-1">Member Since</label>
                <input type="text" id="createdAt" class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100" readonly>
            </div>

            <div class="pt-4 border-t border-gray-200">
                <h3 class="text-lg font-medium text-gray-900 mb-4">Change Password</h3>

                <div class="space-y-4">
                    <div>
                        <label for="currentPassword" class="block text-sm font-medium text-gray-700 mb-1">Current Password</label>
                        <input type="password" id="currentPassword" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-navy-500">
                    </div>

                    <div>
                        <label for="newPassword" class="block text-sm font-medium text-gray-700 mb-1">New Password</label>
                        <input type="password" id="newPassword"
                               pattern="^.{6,}$"
                               title="Password must be at least 8 characters and include at least one letter and one number"
                               class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-navy-500">
                        <p class="text-xs text-gray-500 mt-1">Password must be at least 8 characters with at least one letter and one number</p>
                    </div>

                    <div>
                        <label for="confirmPassword" class="block text-sm font-medium text-gray-700 mb-1">Confirm New Password</label>
                        <input type="password" id="confirmPassword" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-navy-500">
                    </div>
                </div>
            </div>

            <div class="flex flex-col md:flex-row space-y-3 md:space-y-0 md:space-x-3 pt-4">
                <button type="submit" class="flex-1 bg-navy-700 hover:bg-navy-800 text-white font-medium py-2 px-4 rounded focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-navy-500 transition duration-200">
                    Save Changes
                </button>
                <button type="button" id="deleteAccountBtn" class="flex-1 bg-red-600 hover:bg-red-700 text-white font-medium py-2 px-4 rounded focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 transition duration-200">
                    Delete Account
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div id="deleteModal" class="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center z-50 hidden">
    <div class="bg-white rounded-lg shadow-xl p-6 max-w-md w-full">
        <h3 class="text-xl font-bold text-gray-900 mb-4">Confirm Account Deletion</h3>
        <p class="mb-6 text-gray-700">Are you sure you want to delete your account? This action cannot be undone.</p>
        <div class="flex justify-end space-x-3">
            <button id="cancelDeleteBtn" class="px-4 py-2 bg-gray-300 hover:bg-gray-400 rounded transition duration-200">
                Cancel
            </button>
            <button id="confirmDeleteBtn" class="px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded transition duration-200">
                Delete Account
            </button>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="bg-navy-800 text-white p-4 mt-auto">
    <div class="container mx-auto text-center">
        <p>&copy; 2025 Student Portal. All rights reserved.</p>
    </div>
</footer>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Get user ID from localStorage
        const userId = localStorage.getItem('userId');
        if (!userId) {
            window.location.href = '/login';
            return;
        }

        // Setup navigation
        setupNavigation(userId);

        // Load user data
        loadUserData(userId);

        // Form submission handler
        document.getElementById('profileForm').addEventListener('submit', handleFormSubmit);

        // Delete account handler
        document.getElementById('deleteAccountBtn').addEventListener('click', showDeleteModal);
        document.getElementById('cancelDeleteBtn').addEventListener('click', hideDeleteModal);
        document.getElementById('confirmDeleteBtn').addEventListener('click', deleteAccount);
    });

    function setupNavigation(userId) {
        const navButtons = document.getElementById('navButtons');

        // Create logout button
        const logoutBtn = document.createElement('a');
        logoutBtn.href = '#';
        logoutBtn.className = 'flex items-center space-x-1 bg-navy-700 hover:bg-navy-600 px-3 py-1 rounded';
        logoutBtn.innerHTML = '<span>🚪</span><span>Logout</span>';
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            if (confirm('Are you sure you want to logout?')) {
                localStorage.removeItem('userId');
                window.location.href = '/login';
            }
        });

        navButtons.appendChild(logoutBtn);
    }

    function loadUserData(userId) {
        fetch(`/api/users/`+userId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load user data');
                }
                return response.json();
            })
            .then(user => {
                // Populate form fields
                document.getElementById('userId').value = user.id;
                document.getElementById('fullName').value = user.fullName;
                document.getElementById('email').value = user.email;
                document.getElementById('role').value = user.role;

                // Format and display created date
                const createdDate = new Date(user.createdAt);
                document.getElementById('createdAt').value = createdDate.toLocaleDateString() + ' ' + createdDate.toLocaleTimeString();
            })
            .catch(error => {
                showAlert('Error: ' + error.message, 'error');
            });
    }

    function handleFormSubmit(event) {
        event.preventDefault();

        // Validate form
        const form = event.target;
        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        // Password validation
        const currentPassword = document.getElementById('currentPassword').value;
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // If trying to change password
        if (newPassword || confirmPassword) {
            if (!currentPassword) {
                showAlert('Please enter your current password', 'error');
                return;
            }

            if (newPassword !== confirmPassword) {
                showAlert('New passwords do not match', 'error');
                return;
            }
        }

        // Prepare user data
        const userData = {
            id: parseInt(document.getElementById('userId').value),
            fullName: document.getElementById('fullName').value,
            email: document.getElementById('email').value,
            role: document.getElementById('role').value
        };

        // Always include password in the update request
        // If changing password, use the new one, otherwise use current
        userData.password = newPassword || currentPassword;

        // Send update request
        fetch('/api/users', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to update profile');
                }
                showAlert('Profile updated successfully', 'success');

                // Clear password fields
                document.getElementById('currentPassword').value = '';
                document.getElementById('newPassword').value = '';
                document.getElementById('confirmPassword').value = '';
            })
            .catch(error => {
                showAlert('Error: ' + error.message, 'error');
            });
    }

    function showDeleteModal() {
        document.getElementById('deleteModal').classList.remove('hidden');
    }

    function hideDeleteModal() {
        document.getElementById('deleteModal').classList.add('hidden');
    }

    function deleteAccount() {
        const userId = document.getElementById('userId').value;

        fetch(`/api/users/`+userId, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete account');
                }

                // Clear local storage and redirect to login
                localStorage.removeItem('userId');
                window.location.href = '/login';
            })
            .catch(error => {
                hideDeleteModal();
                showAlert('Error: ' + error.message, 'error');
            });
    }

    function showAlert(message, type) {
        const alertBox = document.getElementById('alertMessage');
        alertBox.textContent = message;
        alertBox.classList.remove('hidden', 'bg-green-100', 'text-green-800', 'bg-red-100', 'text-red-800');

        if (type === 'success') {
            alertBox.classList.add('bg-green-100', 'text-green-800');
        } else {
            alertBox.classList.add('bg-red-100', 'text-red-800');
        }

        // Auto hide after 5 seconds
        setTimeout(() => {
            alertBox.classList.add('hidden');
        }, 5000);
    }
</script>

<!-- Custom Tailwind CSS for navy colors -->
<style type="text/css">
    .bg-navy-500 { background-color: #3a5180; }
    .bg-navy-600 { background-color: #2c3e6d; }
    .bg-navy-700 { background-color: #1e2d52; }
    .bg-navy-800 { background-color: #172442; }
    .text-navy-500 { color: #3a5180; }
    .text-navy-700 { color: #1e2d52; }
    .text-navy-800 { color: #172442; }
    .ring-navy-500 { --tw-ring-color: #3a5180; }
</style>
</body>
</html>