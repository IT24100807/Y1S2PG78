<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.25/jspdf.plugin.autotable.min.js"></script>
</head>
<body class="bg-gray-100">
<div class="container mx-auto px-4 py-6">
    <!-- Header with search and logout -->
    <div class="flex justify-between items-center mb-6 pb-3 border-b border-gray-300">
        <h1 class="text-2xl font-bold text-gray-800">Admin Dashboard</h1>
        <div class="flex items-center">
            <input type="text" id="searchInput" placeholder="Search users by name or email"
                   class="px-4 py-2 mr-4 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500">
            <a href="src/main/webapp/WEB-INF/views/login.jsp" class="text-red-600 font-semibold hover:text-red-800">Logout →</a>
        </div>
    </div>

    <!-- User Management Section -->
    <div>
        <h2 class="text-xl font-semibold mb-4">User Management</h2>

        <div class="bg-white rounded-lg shadow-sm">
            <!-- Table Header -->
            <div class="bg-gray-50 p-4 rounded-t-lg border-b border-gray-200">
                <div class="flex justify-between items-center">
                    <div>
                        <button id="generatePdfBtn" class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 mr-2">Generate PDF</button>
                        <button id="addUserBtn" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700">Add User</button>
                    </div>
                </div>
            </div>

            <!-- User Table -->
            <table class="w-full">
                <thead>
                <tr class="bg-gray-800 text-white">
                    <th class="py-3 px-4 text-left">ID</th>
                    <th class="py-3 px-4 text-left">Name</th>
                    <th class="py-3 px-4 text-left">Email</th>
                    <th class="py-3 px-4 text-left">Role</th>
                    <th class="py-3 px-4 text-left">Actions</th>
                </tr>
                </thead>
                <tbody id="usersTableBody">
                <!-- User data will be populated dynamically -->
                </tbody>
            </table>
        </div>
    </div>
</div>


<!-- Add/Edit User Modal -->
<!-- Add/Edit User Modal -->
<div id="userModal" class="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center hidden z-50">
    <div class="bg-white rounded-lg shadow-lg w-full max-w-md mx-4">
        <div class="flex justify-between items-center p-4 border-b border-gray-200">
            <h2 id="modalTitle" class="text-xl font-semibold">Add User</h2>
            <button class="close text-gray-500 hover:text-gray-800 text-2xl font-bold">&times;</button>
        </div>

        <form id="userForm" class="p-4">
            <input type="hidden" id="userId">

            <div class="mb-4">
                <label for="fullName" class="block text-gray-700 mb-2">Full Name</label>
                <input type="text" id="fullName" class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500" required>
            </div>

            <div class="mb-4">
                <label for="email" class="block text-gray-700 mb-2">Email</label>
                <input type="email" id="email" class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500" required>
            </div>

            <div class="mb-4">
                <label for="role" class="block text-gray-700 mb-2">Role</label>
                <select id="role" class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500" required>
                    <option value="Admin">Admin</option>
                    <option value="Teacher">Teacher</option>
                    <option value="Student">Student</option>
                </select>
            </div>

            <div class="mb-4">
                <label for="password" class="block text-gray-700 mb-2">Password</label>
                <input type="password" id="password" class="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500" required>
            </div>

            <div class="flex justify-end">
                <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">Save</button>
            </div>
        </form>
    </div>
</div>

<script>
    // Load the jsPDF library
    const { jsPDF } = window.jspdf;

    // Global users array to store fetched data
    let users = [];

    // DOM elements
    const searchInput = document.getElementById("searchInput");
    const usersTableBody = document.getElementById("usersTableBody");
    const userModal = document.getElementById("userModal");
    const userForm = document.getElementById("userForm");
    const modalTitle = document.getElementById("modalTitle");
    const closeBtn = document.querySelector(".close");
    const addUserBtn = document.getElementById("addUserBtn");
    const generatePdfBtn = document.getElementById("generatePdfBtn");

    // Event listeners
    document.addEventListener("DOMContentLoaded", loadUsers);
    searchInput.addEventListener("input", filterUsers);
    userForm.addEventListener("submit", saveUser);
    closeBtn.addEventListener("click", closeModal);
    addUserBtn.addEventListener("click", showAddUserModal);
    generatePdfBtn.addEventListener("click", generatePdf);

    // Fetch all users from API
    function loadUsers() {
        fetch("/api/users")
            .then(response => response.json())
            .then(data => {
                users = data;
                renderUsers(users);
            })
            .catch(error => console.error("Error loading users:", error));
    }

    // Render users to table
    function renderUsers(usersToRender) {
        usersTableBody.innerHTML = "";

        usersToRender.forEach(user => {
            const row = document.createElement("tr");
            row.className = "border-b border-gray-200 hover:bg-gray-50";

            row.innerHTML =
                "<td class=\"py-3 px-4\">" + user.id + "</td>" +
                "<td class=\"py-3 px-4\">" + user.fullName + "</td>" +
                "<td class=\"py-3 px-4\">" + user.email + "</td>" +
                "<td class=\"py-3 px-4\">" + user.role + "</td>" +
                "<td class=\"py-3 px-4\">" +
                "<button onclick=\"editUser(" + user.id + ")\" class=\"px-3 py-1 bg-blue-600 text-white rounded text-sm mr-1 hover:bg-blue-700\">Edit Role</button>" +
                "<button onclick=\"deleteUser(" + user.id + ")\" class=\"px-3 py-1 bg-red-600 text-white rounded text-sm hover:bg-red-700\">Delete</button>" +
                "</td>";


            usersTableBody.appendChild(row);
        });
    }

    // Filter users based on search input
    function filterUsers() {
        const searchTerm = searchInput.value.toLowerCase();

        const filteredUsers = users.filter(user => {
            return user.fullName.toLowerCase().includes(searchTerm) ||
                user.email.toLowerCase().includes(searchTerm);
        });

        renderUsers(filteredUsers);
    }

    // Show modal for adding a new user
    function showAddUserModal() {
        modalTitle.textContent = "Add User";
        userForm.reset();
        document.getElementById("userId").value = "";
        userModal.classList.remove("hidden");
    }

    // Show modal for editing an existing user
    function editUser(userId) {
        const user = users.find(u => u.id === userId);

        if (user) {
            modalTitle.textContent = "Edit Role";
            document.getElementById("userId").value = user.id;
            document.getElementById("fullName").value = user.fullName;
            document.getElementById("email").value = user.email;
            document.getElementById("role").value = user.role;
            document.getElementById("password").value = ""; // Don't populate password

            userModal.classList.remove("hidden");
        }
    }

    // Close the modal
    function closeModal() {
        userModal.classList.add("hidden");
    }

    // Save user (create or update)
    function saveUser(e) {
        e.preventDefault();

        const userId = document.getElementById("userId").value;
        const user = {
            fullName: document.getElementById("fullName").value,
            email: document.getElementById("email").value,
            role: document.getElementById("role").value,
            password: document.getElementById("password").value
        };

        if (userId) {
            // Update existing user
            user.id = parseInt(userId);

            fetch("/api/users", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            })
                .then(() => {
                    loadUsers();
                    closeModal();
                })
                .catch(error => console.error("Error updating user:", error));
        } else {
            // Create new user
            fetch("/api/users", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            })
                .then(() => {
                    loadUsers();
                    closeModal();
                })
                .catch(error => console.error("Error creating user:", error));
        }
    }

    // Delete user
    function deleteUser(userId) {
        if (confirm("Are you sure you want to delete this user?")) {
            fetch(`/api/users/`+userId, {
                method: "DELETE"
            })
                .then(() => {
                    loadUsers();
                })
                .catch(error => console.error("Error deleting user:", error));
        }
    }

    // Generate PDF report
    function generatePdf() {
        const doc = new jsPDF();

        // Add title
        doc.setFontSize(18);
        doc.text("User Management Report", 14, 22);

        // Add date
        doc.setFontSize(12);
        doc.text("Generated on: " + new Date().toLocaleDateString(), 14, 30);


        // Create table
        const tableColumn = ["ID", "Name", "Email", "Role"];
        const tableRows = [];

        users.forEach(user => {
            const userData = [
                user.id,
                user.fullName,
                user.email,
                user.role
            ];
            tableRows.push(userData);
        });

        doc.autoTable({
            head: [tableColumn],
            body: tableRows,
            startY: 40,
            styles: { fontSize: 10 },
            columnStyles: { 0: { cellWidth: 20 } }
        });

        // Save PDF
        doc.save("users-report.pdf");
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
        if (event.target === userModal) {
            closeModal();
        }
    };
</script>
</body>
</html>