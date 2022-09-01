import {signUpInputGuides, signUpJson} from "./signup.js";

// const signUpFormUsername = signUpForm.querySelector("input[name='username']");
// const signUpFormUsernameValidateBtn = signUpForm.querySelector("#signUpFormUsernameValidateBtn");

/** Username */
export function setMovementForUsername(signUpForm) {
    const signUpFormUsername = signUpForm.querySelector("input[name='username']");
    const signUpFormUsernameValidateBtn = signUpForm.querySelector("#signUpFormUsernameValidateBtn");

    setPopoverToSignUpForm('hover focus', signUpFormUsername, signUpInputGuides.username);

    signUpFormUsernameValidateBtn.addEventListener('click', function () {
        $.ajax({
            type: 'GET',
            url: '/username-validation',
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
                    $("#signUpFormUsernameValidateBtn").prepend(loadingSpinnerToBtn);
                }
            },
            success: function (data) {
                console.log(data);
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
                    createToast($("#modalToast"), "danger", "Alert", data);
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


