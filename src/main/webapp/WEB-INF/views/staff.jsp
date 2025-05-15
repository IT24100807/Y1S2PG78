<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Professor Dashboard</title>
    <!-- Include Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            background-color: #f3f4f6;
        }
        .card {
            background-color: white;
            border-radius: 0.5rem;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            padding: 1.5rem;
        }
        .icon {
            color: #543e29;
            font-size: 1.5rem;
        }
        .btn-primary {
            background-color: #1e3a5f;
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 0.25rem;
            display: flex;
            align-items: center;
            transition: background-color 0.2s;
            font-size: 0.875rem;
        }
        .btn-primary:hover {
            background-color: #15294a;
        }
        .btn-icon {
            margin-right: 0.25rem;
        }
    </style>
</head>
<body>
<!-- Login Form (Initially Hidden) -->
<div id="loginContainer" class="hidden min-h-screen flex items-center justify-center p-4">
    <div class="bg-white rounded-lg shadow-md p-8 max-w-md w-full">
        <div id="loginAlert" class="hidden mb-4 p-4 rounded"></div>

        <h1 class="text-2xl font-bold text-center mb-6">Professor Login</h1>

        <form id="loginForm" class="space-y-6">
            <div>
                <label for="loginEmail" class="block text-sm font-medium text-gray-700 mb-1">Email Address</label>
                <input type="email" id="loginEmail" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500" required>
            </div>

            <div>
                <label for="loginPassword" class="block text-sm font-medium text-gray-700 mb-1">Password</label>
                <input type="password" id="loginPassword" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500" required>
            </div>

            <div>
                <button type="submit" class="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                    Sign In
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Dashboard (Initially Hidden) -->
<div id="dashboardContainer" class="hidden min-h-screen">
    <!-- Top Navigation Bar -->
    <header class="bg-white p-4 shadow-sm">
        <div class="container mx-auto flex justify-between items-center">
            <div class="flex items-center space-x-3">
                <div class="bg-amber-100 w-10 h-10 rounded-full flex items-center justify-center">
                        <span class="text-amber-900">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M18 8h1a4 4 0 010 8h-1"></path>
                                <path d="M2 8h16v9a4 4 0 01-4 4H6a4 4 0 01-4-4V8z"></path>
                                <line x1="6" y1="1" x2="6" y2="4"></line>
                                <line x1="10" y1="1" x2="10" y2="4"></line>
                                <line x1="14" y1="1" x2="14" y2="4"></line>
                            </svg>
                        </span>
                </div>
                <h1 class="text-xl font-medium" id="welcomeMessage">Welcome, Professor</h1>
            </div>
            <div class="flex items-center space-x-4">
                <a href="#" class="text-gray-700 hover:text-gray-900 flex items-center" id="profileBtn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="mr-1">
                        <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"></path>
                        <circle cx="12" cy="7" r="4"></circle>
                    </svg>
                    Profile
                </a>
                <a href="#" class="text-gray-700 hover:text-gray-900 flex items-center" id="logoutBtn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="mr-1">
                        <path d="M9 21H5a2 2 0 01-2-2V5a2 2 0 012-2h4"></path>
                        <polyline points="16 17 21 12 16 7"></polyline>
                        <line x1="21" y1="12" x2="9" y2="12"></line>
                    </svg>
                    Logout
                </a>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <main class="container mx-auto py-6 px-4">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <!-- Course Management Card -->
            <div class="card">
                <div class="icon mb-4">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path>
                        <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>
                    </svg>
                </div>
                <h2 class="text-xl font-medium mb-2">Course Management</h2>
                <p class="text-gray-600 mb-4 text-sm">Manage your subjects and enrolled students</p>
                <a href="#" class="btn-primary inline-flex">
                    <span class="btn-icon">→</span>
                    View Courses
                </a>
            </div>

            <!-- Assignments Card -->
            <div class="card">
                <div class="icon mb-4">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="8" y1="6" x2="21" y2="6"></line>
                        <line x1="8" y1="12" x2="21" y2="12"></line>
                        <line x1="8" y1="18" x2="21" y2="18"></line>
                        <line x1="3" y1="6" x2="3.01" y2="6"></line>
                        <line x1="3" y1="12" x2="3.01" y2="12"></line>
                        <line x1="3" y1="18" x2="3.01" y2="18"></line>
                    </svg>
                </div>
                <h2 class="text-xl font-medium mb-2">Assignments</h2>
                <p class="text-gray-600 mb-6 text-sm"></p>

                <h3 class="text-lg font-medium mb-1">Grade Submissions</h3>
                <p class="text-gray-600 mb-4 text-sm">Review and grade student submissions</p>
                <a href="#" class="btn-primary inline-flex">
                    <span class="btn-icon">○</span>
                    Grade Assignments
                </a>
            </div>

            <!-- Schedule Card -->
            <div class="card">
                <div class="icon mb-4">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                        <line x1="16" y1="2" x2="16" y2="6"></line>
                        <line x1="8" y1="2" x2="8" y2="6"></line>
                        <line x1="3" y1="10" x2="21" y2="10"></line>
                    </svg>
                </div>
                <h2 class="text-xl font-medium mb-2">Schedule</h2>
                <p class="text-gray-600 mb-4 text-sm">Access your class and meeting schedule</p>
                <a href="#" class="btn-primary inline-flex">
                    <span class="btn-icon">→</span>
                    View Schedule
                </a>
            </div>
        </div>
    </main>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Check if user is logged in
        const userId = localStorage.getItem('userId');
        const userName = localStorage.getItem('userName');

        if (userId) {
            // Show dashboard
            document.getElementById('dashboardContainer').classList.remove('hidden');

            // Update welcome message if userName exists
            if (userName) {
                document.getElementById('welcomeMessage').textContent = `Welcome, ${userName}`;
            }

            // Setup logout button
            document.getElementById('logoutBtn').addEventListener('click', function(e) {
                e.preventDefault();
                if (confirm('Are you sure you want to logout?')) {
                    localStorage.removeItem('userId');
                    localStorage.removeItem('userName');
                    window.location.href = '/login';
                }
            });

            // Setup profile button
            document.getElementById('profileBtn').addEventListener('click', function(e) {
                e.preventDefault();
                window.location.href = '/profile';
            });
        } else {
            window.location.href = "/login"
        }
    });

    function showLoginAlert(message, type) {
        const alertBox = document.getElementById('loginAlert');
        alertBox.textContent = message;
        alertBox.classList.remove('hidden', 'bg-green-100', 'text-green-800', 'bg-red-100', 'text-red-800');

        if (type === 'success') {
            alertBox.classList.add('bg-green-100', 'text-green-800');
        } else {
            alertBox.classList.add('bg-red-100', 'text-red-800');
        }
    }
</script>
</body>
</html>