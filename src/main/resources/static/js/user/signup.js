const signUpInputGuides = {
    username: "ID는 영문 소문자 및 숫자 및 특수기호 '_'를 포함할 수 있으며, 5자 이상이어야 합니다.",
    // password: "PW는 최소 1개 이상의 대문자와 숫자 및 특수기호가 포함되어야하며, 8자 이상이어야 합니다."
    password: "PW는 4자 이상이어야 합니다.", // 개발 편의성을 위해 비밀번호 정책 완화
    nickname: "한글의 자음 모음은 포함할 수 없습니다.",
    authorities: "최소 1개 이상의 권한을 선택할 수 있습니다."
}

const signUpForm = document.querySelector("#signUpForm");
const signUpFormUsername = signUpForm.querySelector("input[name='username']");
const signUpFormUsernameValidateBtn = signUpForm.querySelector("#signUpFormUsernameValidateBtn");
const signUpFormPassword = signUpForm.querySelector("input[name='password']");
const signUpFormReconfirmPassword = document.querySelector("#signUpFormReConfirmPassword");
const signUpFormNickname = signUpForm.querySelector("input[name='nickname']");
const signUpFormNicknameValidateBtn = signUpForm.querySelector("#signUpFormNicknameValidateBtn");
const signUpFormAuthorities = signUpForm.querySelector("#signUpFormAuthorities");
const signUpFormAuthorityInputList = signUpForm.querySelectorAll("input[name='authorities']");

let signUpJson = {
    username: null,
    password: null,
    nickname: null,
    authorities: {
        ROLE_ADMIN: false,
        ROLE_MANAGER: false,
        ROLE_USER: false
    }
}

/** Username */
const signUpUsernameValidation = function () {
    $.ajax({
        type: 'GET',
        url: '/username-validation',
        data: {username: signUpFormUsername.value},
        beforeSend: function (xhr) {
            if (signUpFormUsername.value === null || signUpFormUsername.value.length < 5
                || koreanReg.test(signUpFormUsername.value) || alphabetUpperReg.test(signUpFormUsername.value)) {
                setPopoverToSignUpForm(signUpFormUsername, signUpInputGuides.username).show();
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
            if (data) {
                setTimeout(function () {
                    signUpFormUsername.removeAttribute('disabled');
                    signUpFormUsername.classList.add('is-valid');
                    signUpFormUsernameValidateBtn.querySelector("span").remove();
                    signUpFormUsernameValidateBtn.classList.replace('btn-secondary', 'btn-success');
                    signUpFormUsernameValidateBtn.innerHTML = "Available";
                    signUpJson.username = signUpFormUsername.value;
                }, 1000);
            }
        }
    });
}
signUpFormUsername.addEventListener('focus', function (e) {
    setPopoverToSignUpForm(e.target, signUpInputGuides.username);
});
signUpFormUsername.addEventListener('input', function (e) {
    signUpFormUsernameValidateBtn.removeAttribute("disabled");
    signUpFormUsernameValidateBtn.classList.replace('btn-success', 'btn-secondary');
    signUpFormUsernameValidateBtn.innerHTML = "Validate";
    signUpFormUsername.classList.remove('is-invalid', 'is-valid');
    if (e.target.value.length === 0)
        signUpFormUsername.classList.add('is-invalid');
});

/** Password */
setPopoverToSignUpForm(signUpFormPassword, signUpInputGuides.password);
signUpFormPassword.addEventListener('input', function (e) {
    const password = e.target.value;
    if (password.length < 4) {
        signUpFormPassword.classList.add('is-invalid');
    } else {
        signUpFormPassword.classList.replace('is-invalid', 'is-valid');
        signUpFormReconfirmPassword.removeAttribute('disabled');
        setPopoverToSignUpForm(signUpFormReconfirmPassword, "비밀번호를 다시 입력해주세요.");
        signUpFormReconfirmPassword.addEventListener('input', function (e) {
            if (password === e.target.value) {
                setPopoverToSignUpForm(signUpFormReconfirmPassword, null);
                if (signUpFormReconfirmPassword.classList.contains('is-invalid'))
                    signUpFormReconfirmPassword.classList.replace('is-invalid', 'is-valid');
                else
                    signUpFormReconfirmPassword.classList.add('is-valid');
                signUpJson.password = password;
            } else {
                setPopoverToSignUpForm(signUpFormReconfirmPassword, "비밀번호가 일치하지않습니다.");
                if (signUpFormReconfirmPassword.classList.contains('is-valid'))
                    signUpFormReconfirmPassword.classList.replace('is-valid', 'is-invalid');
                else
                    signUpFormReconfirmPassword.classList.add('is-invalid');
            }
        });
    }
});

/** Nickname */
const signUpNicknameValidation = function () {
    $.ajax({
        type: 'GET',
        url: '/nickname-validation',
        data: {nickname: signUpFormNickname.value},
        beforeSend: function (xhr) {
            if (signUpFormNickname.value === null || koreanConsonantsAndVowels.test(signUpFormNickname.value)) {
                setPopoverToSignUpForm(signUpFormNickname, signUpInputGuides.nickname).show();
                signUpFormNickname.classList.add('is-invalid');
                signUpFormNickname.classList.remove('is-valid');
                xhr.abort();
            } else {
                signUpFormNickname.setAttribute('disabled', 'true');
                signUpFormNicknameValidateBtn.setAttribute('disabled', 'true');
                $("#signUpFormNicknameValidateBtn").prepend(loadingSpinnerToBtn);
            }
        },
        success: function (data) {
            console.log(data);
            if (data) {
                setTimeout(function () {
                    signUpFormNickname.removeAttribute('disabled');
                    signUpFormNickname.classList.add('is-valid');
                    signUpFormNicknameValidateBtn.querySelector("span").remove();
                    signUpFormNicknameValidateBtn.classList.replace('btn-secondary', 'btn-success');
                    signUpFormNicknameValidateBtn.innerHTML = "Available";
                    signUpJson.nickname = signUpFormNickname.value;
                }, 1000);
            }
        }
    })
};
signUpFormNickname.addEventListener('input', function (e) {
    signUpFormNicknameValidateBtn.removeAttribute("disabled");
    signUpFormNicknameValidateBtn.classList.replace('btn-success', 'btn-secondary');
    signUpFormNicknameValidateBtn.innerHTML = "Validate";
    signUpFormNickname.classList.remove('is-invalid', 'is-valid');
    if (e.target.value.length === 0)
        signUpFormNickname.classList.add('is-invalid');
});

/** Authority */
setPopoverToSignUpForm(signUpFormAuthorities, signUpInputGuides.authorities);

signUpFormAuthorityInputList.forEach(auth => {
    auth.addEventListener('change', function (e) {
        signUpFormAuthorities.classList.replace('border-danger', 'border-1');
        if (e.target.value === 'ROLE_ADMIN')
            signUpJson.authorities.ROLE_ADMIN = e.target.checked;
        else if (e.target.value === 'ROLE_MANAGER')
            signUpJson.authorities.ROLE_MANAGER = e.target.checked;
        else if (e.target.value === 'ROLE_USER')
            signUpJson.authorities.ROLE_USER = e.target.checked;
        else
            alert("ERROR");
    });
});

const signUp = function () {

    if (signUpJson.username == null)
        signUpFormUsername.classList.add('is-invalid');
    if (signUpJson.password == null)
        signUpFormPassword.classList.add('is-invalid');
    if (signUpJson.nickname == null)
        signUpFormNickname.classList.add('is-invalid');
    if (signUpJson.authorities.ROLE_ADMIN === false && signUpJson.authorities.ROLE_MANAGER === false && signUpJson.authorities.ROLE_USER === false) {
        signUpFormAuthorities.classList.replace('border-1', 'border-danger');
    }
    console.log(signUpJson);

    // $.ajax({
    //     type: 'POST',
    //     url: '/signup',
    //     dataType: 'JSON',
    //     contentType: 'application/json; charset=utf-8',
    //     data: JSON.stringify({
    //         username: signUpForm.querySelector("input[name='username']").value,
    //         password: signUpForm.querySelector("input[name='password']").value,
    //         nickname: signUpForm.querySelector("input[name='nickname']").value,
    //     }),
    //     success: function (data, status, xhr) {
    //         console.log(data);
    //         console.log(status);
    //         console.log(xhr);
    //         window.location.href = '/login';
    //     },
    //     error: function (data, status, xhr) {
    //         console.error(data);
    //         console.error(status);
    //         console.error(xhr);
    //     }
    // });
};