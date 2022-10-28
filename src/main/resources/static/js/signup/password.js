import {signUpFormPassword, signUpFormReconfirmPassword, signUpInputGuides, signUpJson} from "./signup.js";

/** Password */
export const setMovementForPassword = function () {
    setPopoverToSignUpForm('hover focus', signUpFormPassword, signUpInputGuides.password);
    signUpFormPassword.addEventListener('input', function (e) {
        signUpFormReconfirmPassword.value = "";
        checkingPassword();
    });
    signUpFormReconfirmPassword.addEventListener('input', function (e) {
        checkingPassword();
    });
}

function checkingPassword() {
    if (signUpFormPassword.value.length < 4) {
        signUpFormPassword.classList.add('is-invalid');
        // signUpFormReconfirmPassword.setAttribute('disabled', "true");
    } else {
        signUpFormPassword.classList.replace('is-invalid', 'is-valid');
        if (signUpFormReconfirmPassword.classList.contains('is-valid'))
            signUpFormReconfirmPassword.classList.replace('is-valid', 'is-invalid');
        signUpFormReconfirmPassword.removeAttribute('disabled');
        setPopoverToSignUpForm('hover focus', signUpFormReconfirmPassword, "비밀번호를 다시 입력해주세요.");
        signUpFormReconfirmPassword.addEventListener('input', function (e) {
            if (signUpFormPassword.value === e.target.value) {
                matchedPassword();
                signUpJson.password = signUpFormPassword.value;
            } else {
                mismatchedPassword();
                signUpJson.password = undefined;
            }
        });
    }
}

function matchedPassword() {
    setPopoverToSignUpForm('hover focus', signUpFormReconfirmPassword, null);
    if (signUpFormReconfirmPassword.classList.contains('is-invalid'))
        signUpFormReconfirmPassword.classList.replace('is-invalid', 'is-valid');
    else
        signUpFormReconfirmPassword.classList.add('is-valid');
    // createToast({
    //     target: $("#modalToast"),
    //     type: "success",
    //     header: "Alert",
    //     body: "Both passwords match."
    // });
}

function mismatchedPassword() {
    setPopoverToSignUpForm('hover focus', signUpFormReconfirmPassword, "비밀번호가 일치하지 않습니다.");
    // createToast({
    //     target: $("#modalToast"),
    //     type: "warning",
    //     header: "Alert",
    //     body: "Both passwords do not match."
    // });
    if (signUpFormReconfirmPassword.classList.contains('is-valid'))
        signUpFormReconfirmPassword.classList.replace('is-valid', 'is-invalid');
    else
        signUpFormReconfirmPassword.classList.add('is-invalid');
    signUpJson.password = null;
}

