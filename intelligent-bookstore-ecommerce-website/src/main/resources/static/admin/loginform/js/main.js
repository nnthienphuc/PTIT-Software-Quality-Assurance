const inputs = document.querySelectorAll(".input");
const passwordInput = document.getElementById('password');
const errorMessage = document.querySelector('.error-message');
const successIcon = document.querySelector('.success-icon');
const errorIcon = document.querySelector('.error-icon');

function togglePassword() {
  const type = passwordInput.type === 'password' ? 'text' : 'password';
  passwordInput.type = type;
}

passwordInput.addEventListener('input', function () {
  const value = passwordInput.value;
  const minLength = 8;
  const hasNumber = /\d/.test(value);
  const hasLowercase = /[a-z]/.test(value);
  const hasUppercase = /[A-Z]/.test(value);
  const hasSpecialChar = /[!@#$%^&*]/.test(value);

  if (value.length >= minLength && hasNumber && hasLowercase && hasUppercase && hasSpecialChar) {
	errorMessage.style.display = 'none';
	successIcon.style.display = 'inline';
	errorIcon.style.display = 'none';
  } else {
	errorMessage.style.display = 'block';
	successIcon.style.display = 'none';
	errorIcon.style.display = 'inline';
  }
});

function addcl(){
	let parent = this.parentNode.parentNode;
	parent.classList.add("focus");
}

function remcl(){
	let parent = this.parentNode.parentNode;
	if(this.value == ""){
		parent.classList.remove("focus");
	}
}


inputs.forEach(input => {
	input.addEventListener("focus", addcl);
	input.addEventListener("blur", remcl);
});


