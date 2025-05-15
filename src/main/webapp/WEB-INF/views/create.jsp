<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register to ExamCom</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-900 flex items-center justify-center min-h-screen">
<div class="bg-gray-800 text-white p-10 rounded-lg shadow-md w-full max-w-md">
    <h2 class="text-2xl font-bold mb-6 text-center text-yellow-100">Register to ExamCom</h2>
    <form id="registerForm">
        <label class="block mb-2 text-sm font-medium">Full Name</label>
        <input type="text" name="name" required class="w-full p-2 mb-4 bg-gray-700 rounded">

        <label class="block mb-2 text-sm font-medium">Email Address</label>
        <input type="email" name="email" required class="w-full p-2 mb-4 bg-gray-700 rounded">

        <label class="block mb-2 text-sm font-medium">Password</label>
        <input type="password" name="password" required class="w-full p-2 mb-4 bg-gray-700 rounded">

        <label class="block mb-2 text-sm font-medium">Role</label>
        <div class="mb-4 space-y-1">
            <label class="flex items-center"><input type="radio" name="role" value="Student" required class="mr-2"> Student</label>
            <label class="flex items-center"><input type="radio" name="role" value="Teacher" required class="mr-2"> Teacher</label>
            <label class="flex items-center"><input type="radio" name="role" value="Admin" required class="mr-2"> Admin</label>
        </div>

        <button type="submit" id="registerBtn"
                class="w-full bg-yellow-600 hover:bg-yellow-700 text-white font-bold py-2 px-4 rounded">
            Register
        </button>
        <p id="loadingText" class="mt-3 text-sm text-gray-300 hidden">Registering...</p>
    </form>
</div>

<script>
    const form = document.getElementById('registerForm');
    const loadingText = document.getElementById('loadingText');
    const registerBtn = document.getElementById('registerBtn');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const data = {
            fullName: formData.get('name'),
            email: formData.get('email'),
            password: formData.get('password'),
            role: formData.get('role')
        };

        registerBtn.disabled = true;
        loadingText.classList.remove('hidden');

        try {
            const response = await fetch('/api/users', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                throw new Error('Registration failed');
            }


            alert('Registration successful!');
            window.location.href = '/login';
        } catch (err) {
            alert(err.message || 'An error occurred.');
        } finally {
            registerBtn.disabled = false;
            loadingText.classList.add('hidden');
        }
    });
</script>
</body>
</html>
