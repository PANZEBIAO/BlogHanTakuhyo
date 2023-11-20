document.addEventListener("DOMContentLoaded", function () {
    const usernameInput = document.querySelector("#userName");
    const emailInput = document.querySelector("#Email");
    const passwordInput = document.querySelector("#password");
    const passwordtooInput = document.querySelector("#passwordtoo");
    const submitButton = document.querySelector(".button");

    function areAllFieldsValid() {
        return (
            isFieldValid(usernameInput) &&
            isEmailValid(emailInput) &&
            isFieldValid(passwordInput) &&
            isPasswordMatch(passwordInput, passwordtooInput)
        );
    }

    function isFieldValid(input) {
        const value = input.value.trim();
        return value !== "" && value.length >= 6 && value.length <= 16;
    }

    function isEmailValid(input) {
        const emailValue = input.value.trim();
        return emailValue !== "" && /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(emailValue);
    }

    function isPasswordMatch(passwordInput, passwordtooInput) {
        const passwordValue = passwordInput.value.trim();
        const passwordtooValue = passwordtooInput.value.trim();
        return passwordValue !== "" && passwordtooValue !== "" && passwordValue === passwordtooValue;
    }

    function updateButtonState() {
        const isValid = areAllFieldsValid();
        submitButton.disabled = !isValid;
        submitButton.style.backgroundColor = isValid ? "skyblue" : "#ececec";
    }

    function handleInputEvent(input, span, validationFunction) {
        input.addEventListener("input", () => {
            if (validationFunction(input)) {
                span.innerHTML = "Valid";
                span.className = "success";
                span.style.color = "green";
            } else {
                span.innerHTML = "Invalid";
                span.className = "error";
                span.style.color = "red";
            }
            updateButtonState();
        });
    }

    handleInputEvent(usernameInput, document.querySelector(".username"), isFieldValid);
    handleInputEvent(emailInput, document.querySelector(".email"), isEmailValid);
    handleInputEvent(passwordInput, document.querySelector(".password"), isFieldValid);
    handleInputEvent(passwordtooInput, document.querySelector(".passwordtoo"), () => isPasswordMatch(passwordInput, passwordtooInput));

    updateButtonState();
});


