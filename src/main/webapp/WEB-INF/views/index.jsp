<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <!-- Include Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<!-- Navigation Bar -->
<nav class="bg-navy-800 text-white p-4 shadow-md">
    <div class="container mx-auto flex justify-between items-center">
        <div class="flex items-center space-x-2">
            <span class="font-bold text-xl">📚</span>
            <span class="font-semibold" id="welcomeMessage">Welcome</span>
        </div>
        <div class="flex items-center space-x-4" id="navButtons">
            <!-- Dynamic buttons will be inserted here via JavaScript -->
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold text-center text-navy-800 mb-10">Student Dashboard</h1>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <!-- My Courses Card -->
        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex justify-center mb-4">
                <div class="bg-amber-100 p-3 rounded-full">
                    <span class="text-amber-800 text-xl">📚</span>
                </div>
            </div>
            <h2 class="text-xl font-semibold text-center text-navy-700 mb-2">My Courses</h2>
            <p class="text-gray-600 text-center mb-6">Browse and manage your enrolled courses</p>
            <button class="bg-navy-700 text-white py-2 px-4 rounded w-full hover:bg-navy-800 transition duration-300">
                View Courses
            </button>
        </div>

        <!-- Assignments Card -->
        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex justify-center mb-4">
                <div class="bg-amber-100 p-3 rounded-full">
                    <span class="text-amber-800 text-xl">📋</span>
                </div>
            </div>
            <h2 class="text-xl font-semibold text-center text-navy-700 mb-2">Assignments</h2>
            <p class="text-gray-600 text-center mb-6">Submit and track your assignments</p>
            <button class="bg-navy-700 text-white py-2 px-4 rounded w-full hover:bg-navy-800 transition duration-300">
                Submit Assignment
            </button>
        </div>

        <!-- Grades Card -->
        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex justify-center mb-4">
                <div class="bg-amber-100 p-3 rounded-full">
                    <span class="text-amber-800 text-xl">📊</span>
                </div>
            </div>
            <h2 class="text-xl font-semibold text-center text-navy-700 mb-2">Grades</h2>
            <p class="text-gray-600 text-center mb-6">Check and review your academic performance</p>
            <button class="bg-navy-700 text-white py-2 px-4 rounded w-full hover:bg-navy-800 transition duration-300">
                View Grades
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
        const userId = localStorage.getItem('userId');
        const navButtons = document.getElementById('navButtons');
        const welcomeMessage = document.getElementById('welcomeMessage');

        if (userId) {
            // User is logged in
            welcomeMessage.textContent = 'Welcome, Student';

            // Create profile button
            const profileBtn = document.createElement('a');
            profileBtn.href = '/profile';
            profileBtn.className = 'flex items-center space-x-1 bg-navy-700 hover:bg-navy-600 px-3 py-1 rounded';
            profileBtn.innerHTML = '<span>👤</span><span>Profile</span>';

            // Create logout button
            const logoutBtn = document.createElement('a');
            logoutBtn.href = '#';
            logoutBtn.className = 'flex items-center space-x-1 bg-navy-700 hover:bg-navy-600 px-3 py-1 rounded';
            logoutBtn.innerHTML = '<span>🚪</span><span>Logout</span>';
            logoutBtn.addEventListener('click', function(e) {
                e.preventDefault();
                localStorage.removeItem('userId');
                window.location.reload();
            });

            // Add buttons to navbar
            navButtons.appendChild(profileBtn);
            navButtons.appendChild(logoutBtn);
        } else {
            // User is not logged in
            welcomeMessage.textContent = 'Welcome, Guest';

            // Create login button
            const loginBtn = document.createElement('a');
            loginBtn.href = '/login';
            loginBtn.className = 'flex items-center space-x-1 bg-navy-700 hover:bg-navy-600 px-3 py-1 rounded';
            loginBtn.innerHTML = '<span>🔐</span><span>Login</span>';

            // Add button to navbar
            navButtons.appendChild(loginBtn);
        }
    });
</script>

<!-- Custom Tailwind CSS for navy colors -->
<style type="text/css">
    .bg-navy-600 { background-color: #2c3e6d; }
    .bg-navy-700 { background-color: #1e2d52; }
    .bg-navy-800 { background-color: #172442; }
    .text-navy-700 { color: #1e2d52; }
    .text-navy-800 { color: #172442; }
</style>
</body>
</ht