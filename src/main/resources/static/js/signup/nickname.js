import {signUpFormNickname, signUpFormNicknameValidateBtn, signUpInputGuides, signUpJson} from "./signup.js";

/** Nickname */
export const setMovementForNickname = function () {
    signUpFormNicknameValidateBtn.addEventListener('click', function () {
        $.ajax({
            type: 'GET',
            url: '/nickname-validation',
            data: {nickname: signUpFormNickname.value},
            beforeSend: function (xhr) {
                if (signUpFormNickname.value.length < 2 || koreanConsonantsAndVowels.test(signUpFormNickname.value)) {
                    setPopoverToSignUpForm('hover focus', signUpFormNickname, signUpInputGuides.nickname).show();
                    signUpFormNickname.classList.add('is-invalid');
                    signUpFormNickname.classList.remove('is-valid');
                    xhr.abort();
                } else {
                    signUpFormNickname.setAttribute('disabled', 'true');
                    signUpFormNicknameValidateBtn.setAttribute('disabled', 'true');
                    // $("#signUpFormNicknameValidateBtn").prepend(loadingSpinnerToBtn);
                    signUpFormNicknameValidateBtn.innerHTML = loadingSpinnerToBtn;
                }
            },
            success: function (data) {
                if (data) {
                    setTimeout(function () {
                        signUpFormNickname.removeAttribute('disabled');
                        signUpFormNickname.classList.add('is-valid');
                        signUpFormNicknameValidateBtn.querySelector("span").remove();
                        signUpFormNicknameValidateBtn.classList.replace('btn-secondary', 'btn-success');
                        signUpFormNicknameValidateBtn.innerHTML = "Available";
                        signUpJson.nickname = signUpFormNickname.value;
                    }, 1000);
                } else {
                    signUpFormNickname.removeAttribute('disabled');
                    signUpFormNickname.classList.add('is-invalid');
                    signUpFormNicknameValidateBtn.querySelector("span").remove();
                    signUpFormNicknameValidateBtn.removeAttribute('disabled');
                    createToast({
                        target: $("#modalToast"),
                        type: "danger",
                        header: "Alert",
                        body: "이미 존재하는 Nickname 입니다."
                    });
                }
            }
        });
    });
    signUpFormNickname.addEventListener('input', function (e) {
        signUpFormNicknameValidateBtn.removeAttribute("disabled");
        signUpFormNicknameValidateBtn.classList.replace('btn-success', 'btn-secondary');
        signUpFormNicknameValidateBtn.innerHTML = "Validate";
        signUpFormNickname.classList.remove('is-invalid', 'is-valid');
        if (e.target.value.length === 0)
            signUpFormNickname.classList.add('is-invalid');
    });
}
