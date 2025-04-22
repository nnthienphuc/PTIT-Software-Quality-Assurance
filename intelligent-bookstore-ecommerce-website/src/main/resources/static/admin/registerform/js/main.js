function validatePassword() {
    var password = document.getElementById("passwordInput").value;
    var passwordError = document.getElementById("passwordError");

    // Các điều kiện mật khẩu
    var hasUpperCase = /[A-Z]/.test(password);
    var hasLowerCase = /[a-z]/.test(password);
    var hasNumbers = /[0-9]/.test(password);
    var hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
    var isValidLength = password.length >= 8;

    // Kiểm tra điều kiện mật khẩu
    if (!hasUpperCase || !hasLowerCase || !hasNumbers || !hasSpecialChar || !isValidLength) {
        passwordError.style.display = "block";
        return false;
    } else {
        passwordError.style.display = "none";
        return true;
    }
}

function togglePassword() {
    var passwordInput = document.getElementById("passwordInput");
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
}


// Gọi hàm validatePassword khi người dùng nhập mật khẩu
document.getElementById("passwordInput").addEventListener("input", validatePassword);

function validatePassword() {
    var password = document.getElementById("passwordInput").value;
    var passwordError = document.getElementById("passwordError");

    // Các điều kiện mật khẩu
    var hasUpperCase = /[A-Z]/.test(password);
    var hasLowerCase = /[a-z]/.test(password);
    var hasNumbers = /[0-9]/.test(password);
    var hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
    var isValidLength = password.length >= 8;

    // Kiểm tra điều kiện mật khẩu
    if (!hasUpperCase || !hasLowerCase || !hasNumbers || !hasSpecialChar || !isValidLength) {
        passwordError.style.display = "block";
        return false;
    } else {
        passwordError.style.display = "none";
        return true;
    }
}

function validateConfirmPassword() {
    var password = document.getElementById("passwordInput").value;
    var confirmPassword = document.getElementById("confirmPasswordInput").value;
    var confirmPasswordError = document.getElementById("confirmPasswordError");

    // Kiểm tra xem Confirm Password có khớp với Password hay không
    if (password !== confirmPassword) {
        confirmPasswordError.style.display = "block";
        return false;
    } else {
        confirmPasswordError.style.display = "none";
        return true;
    }
}

function togglePassword() {
    var passwordInput = document.getElementById("passwordInput");
    var confirmPasswordInput = document.getElementById("confirmPasswordInput");
    
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        confirmPasswordInput.type = "text";
    } else {
        passwordInput.type = "password";
        confirmPasswordInput.type = "password";
    }
}

// Gọi hàm validateConfirmPassword khi người dùng nhập Confirm Password
document.getElementById("confirmPasswordInput").addEventListener("input", validateConfirmPassword);
// Function to toggle password visibility
function togglePassword(inputId, toggleIconId) {
    const passwordInput = document.getElementById(inputId);
    const toggleIcon = document.getElementById(toggleIconId);
    
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        toggleIcon.textContent = "🙈"; // Change icon to "eye closed" when showing the password
    } else {
        passwordInput.type = "password";
        toggleIcon.textContent = "👁️"; // Change back to "eye open" when hiding the password
    }
}


