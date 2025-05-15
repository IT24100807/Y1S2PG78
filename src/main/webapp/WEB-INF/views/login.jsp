<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - ExamCom</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-900 flex items-center justify-center min-h-screen">
<div class="bg-gray-800 text-white p-10 rounded-lg shadow-md w-full max-w-md">
    <h2 class="text-2xl font-bold mb-6 text-center text-yellow-100">Login to ExamCom</h2>
    <form id="loginForm">
        <div class="mb-4">
            <label class="block mb-1 text-sm font-medium">Email Address</label>
            <input type="email" name="email" required
                   class="w-full p-2 bg-gray-700 border border-gray-600 rounded focus:outline-none focus:ring-2 focus:ring-yellow-600">
        </div>
        <div class="mb-6">
            <label class="block mb-1 text-sm font-medium">Password</label>
            <input type="password" name="password" required
                   class="w-full p-2 bg-gray-700 border border-gray-600 rounded focus:outline-none focus:ring-2 focus:ring-yellow-600">
        </div>
        <button type="submit"
                class="w-full bg-yellow-600 hover:bg-yellow-700 text-white font-bold py-2 px-4 rounded transition duration-200">
            Login
        </button>
        <p id="loadingText" class="mt-3 text-sm text-gray-300 hidden">Logging in...</p>
    </form>
    <p>Dont have an account ? <a href="/register">Register Now</a></p>
</div>

<script>
    const form = document.getElementById('loginForm');
    const loadingText = document.getElementById('loadingText');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        loadingText.classList.remove('hidden');

        const email = form.email.value;
        const password = form.password.value;

        try {
            const response = await fetch('/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });

            if (!response.ok) throw new Error('Invalid email or password');
            const user = await response.json();

            if (!user || !user.id || !user.role) {
                throw new Error('Invalid user data received');
            }

            // Save user info in sessionStorage
            localStorage.setItem('userId', user.id);
            localStorage.setItem('role', user.role);

            // Redirect based on role
            const role = user.role.toLowerCase();
            if (role === 'admin') {
                window.location.href = '/admin';
            } else if (role === 'teacher' || role === 'staff') {
                window.location.href = '/staff';
            } else if (role === 'student') {
                window.location.href = '/';
            } else {
                alert('Unknown role. Access denied.');
            }

        } catch (err) {
            alert(err.message || 'Login failed');
        } finally {
            loadingText.classList.add('hidden');
        }
    });
</script>
</body>
</html>
