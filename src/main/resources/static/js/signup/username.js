import {signUpInputGuides, signUpJson, signUpFormUsername, signUpFormUsernameValidateBtn} from "./signup.js";

/** Username */
export function setMovementForUsername() {
    setPopoverToSignUpForm('hover focus', signUpFormUsername, signUpInputGuides.username);

    signUpFormUsernameValidateBtn.addEventListener('click', function () {
        $.ajax({
            type: 'GET',
            url: '/api/user/username-validation',
            data: {username: signUpFormUsername.value},
            beforeSend: function (xhr) {
                if (signUpFormUsername.value === null || signUpFormUsername.value.length < 5
                    || koreanReg.test(signUpFormUsername.value) || alphabetUpperReg.test(signUpFormUsername.value)) {
                    setPopoverToSignUpForm('hover focus', signUpFormUsername, signUpInputGuides.username).show();
                    signUpFormUsername.classList.add('is-invalid');
                    signUpFormUsername.classList.remove('is-valid');
                    xhr.abort();
                } else {
                    signUpFormUsername.setAttribute('disabled', 'true');
                    signUpFormUsernameValidateBtn.setAttribute('disabled', 'true');
                    signUpFormUsernameValidateBtn.innerHTML = loadingSmallSpinnerSpan;
                }
            },
            success: function (data) {
                if (data === true) {
                    setTimeout(function () {
                        signUpFormUsername.removeAttribute('disabled');
                        signUpFormUsername.classList.add('is-valid');
                        signUpFormUsernameValidateBtn.querySelector("span").remove();
                        signUpFormUsernameValidateBtn.classList.replace('btn-secondary', 'btn-success');
                        signUpFormUsernameValidateBtn.innerHTML = "Available";
                        signUpJson.username = signUpFormUsername.value;
                    }, 1000);
                } else {
                    signUpFormUsername.removeAttribute('disabled');
                    signUpFormUsername.classList.add('is-invalid');
                    signUpFormUsernameValidateBtn.querySelector("span").remove();
                    signUpFormUsernameValidateBtn.removeAttribute('disabled');
                    createToast({
                        target: $("#modalToast"),
                        type: "danger",
                        header: "Alert",
                        body: data
                    });
                }
            }
        });
    });

    signUpFormUsername.addEventListener('focus', function (e) {
        setPopoverToSignUpForm('hover focus', e.target, signUpInputGuides.username);
    });

    signUpFormUsername.addEventListener('input', function (e) {
        signUpFormUsernameValidateBtn.removeAttribute("disabled");
        signUpFormUsernameValidateBtn.classList.replace('btn-success', 'btn-secondary');
        signUpFormUsernameValidateBtn.innerHTML = "Validate";
        signUpFormUsername.classList.remove('is-invalid', 'is-valid');
        if (e.target.value.length === 0)
            signUpFormUsername.classList.add('is-invalid');
    });
}


