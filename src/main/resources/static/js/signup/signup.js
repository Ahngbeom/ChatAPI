import {setMovementForUsername} from "./username.js";
import {setMovementForPassword} from "./password.js";
import {setMovementForNickname} from "./nickname.js";
import {setMovementForAuthority} from "./authority.js";

export const signUpInputGuides = {
    username: "ID는 영문 소문자 및 숫자 및 특수기호 '_'를 포함할 수 있으며, 5자 이상이어야 합니다.",
    // password: "PW는 최소 1개 이상의 대문자와 숫자 및 특수기호가 포함되어야하며, 8자 이상이어야 합니다."
    password: "PW는 4자 이상이어야 합니다.", // 개발 편의성을 위해 비밀번호 정책 완화
    nickname: "Nickname은 한글의 자음 및 모음을 포함할 수 없으며 2자 이상이어야합니다.",
    authorities: "최소 1개 이상의 권한을 선택해야합니다."
};

export let signUpJson = {
    username: null,
    password: null,
    nickname: null,
    authorities: new Set()
}

export const signUpForm = document.querySelector("#signUpForm");
export const signUpFormUsername = signUpForm.querySelector("input[name='username']");
export const signUpFormUsernameValidateBtn = signUpForm.querySelector("#signUpFormUsernameValidateBtn");
export const signUpFormPassword = signUpForm.querySelector("input[name='password']");
export const signUpFormReconfirmPassword = document.querySelector("#signUpFormReConfirmPassword");
export const signUpFormNickname = signUpForm.querySelector("input[name='nickname']");
export const signUpFormNicknameValidateBtn = signUpForm.querySelector("#signUpFormNicknameValidateBtn");
export const signUpFormAuthorities = signUpForm.querySelector("#signUpFormAuthorities");
export const signUpFormAuthorityInputList = signUpForm.querySelectorAll("input[name='authorities']");
export const signUpSubmitBtn = document.querySelector("#signUpSubmitBtn");


document.querySelector("#floatingSignUpBtn").addEventListener('click', function () {

    const signUpModalElem = document.querySelector("#signUpModal");

    signUpModalElem.addEventListener('shown.bs.modal', function () {

        setMovementForUsername();

        setMovementForPassword();

        setMovementForNickname();

        setMovementForAuthority();

        /** Sign Up */
        signUpSubmitBtn.addEventListener('click', function () {
            if (signUpJson.username == null && signUpFormUsername.value.length === 0) {
                signUpFormUsername.classList.add('is-invalid');
                setPopoverToSignUpForm('hover focus', signUpFormUsername, signUpInputGuides.username).show();
            } else if (!signUpFormUsernameValidateBtn.classList.contains('btn-success'))
                setPopoverToSignUpForm('hover focus', signUpFormUsernameValidateBtn, "ID 유효성 검사를 수행하세요.").show();
            else if (signUpFormPassword.value.length < 4) {
                signUpFormPassword.classList.add('is-invalid');
                setPopoverToSignUpForm('hover focus', signUpFormPassword, signUpInputGuides.password).show();
            } else if (signUpJson.password == null) {
                signUpFormReconfirmPassword.classList.add('is-invalid');
                if (signUpFormReconfirmPassword.value.length === 0)
                    setPopoverToSignUpForm('hover focus', signUpFormReconfirmPassword, "비밀번호를 다시 입력해주세요.").show();
                else
                    setPopoverToSignUpForm('hover focus', signUpFormReconfirmPassword, "비밀번호가 일치하지않습니다.").show();
            } else if (signUpJson.nickname == null && signUpFormNickname.value.length === 0) {
                signUpFormNickname.classList.add('is-invalid');
                setPopoverToSignUpForm('hover focus', signUpFormNickname, signUpInputGuides.nickname).show();
            } else if (!signUpFormNicknameValidateBtn.classList.contains('btn-success'))
                setPopoverToSignUpForm('hover focus', signUpFormNicknameValidateBtn, "Nickname 유효성 검사를 수행하세요.").show();
            else if (signUpJson.authorities.size === 0) {
                signUpFormAuthorities.classList.add("is-invalid");
                setPopoverToSignUpForm('hover focus', signUpFormAuthorities, signUpInputGuides.authorities).show();
            } else {

                // Set type
                signUpJson.authorities = [...signUpJson.authorities];
                console.log(signUpJson);
                console.log(JSON.stringify(signUpJson));

                $.ajax({
                    type: 'POST',
                    url: '/api/user/signup',
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(signUpJson),
                    success: function (data) {
                        console.log(data);
                        window.location.href = '/login';
                        disposeModalTarget(signUpModalElem);
                        createToast({
                            target: $("#pageToast"),
                            type: "success",
                            header: "Alert",
                            body: "정상적으로 회원가입되었습니다."
                        });
                    },
                    error: function (data) {
                        console.error(data);
                    }
                });
            }
        });

        const userAuthorityFormatConverter = function () {
            if (signUpJson.authorities.has('ROLE_ADMIN')) {
                signUpJson.authorities.delete('ROLE_ADMIN');
                signUpJson.authorities.add({authorityName: 'ROLE_ADMIN'});
            }
            if (signUpJson.authorities.has('ROLE_MANAGER')) {
                signUpJson.authorities.delete('ROLE_MANAGER');
                signUpJson.authorities.add({authorityName: 'ROLE_MANAGER'});
            }
            if (signUpJson.authorities.has('ROLE_USER')) {
                signUpJson.authorities.delete('ROLE_USER');
                signUpJson.authorities.add({authorityName: 'ROLE_USER'});
            }
            signUpJson.authorities = [...signUpJson.authorities];
        };
    });

    showModalTarget(signUpModalElem)
});


// (function autoInputToSignUpForm() {
//     signUpFormUsername.value = "admin";
//     signUpUsernameValidation();
//     signUpJson.username = signUpFormUsername.value;
//
//     signUpFormPassword.value = "1234";
//     signUpFormReconfirmPassword.value = signUpFormPassword.value;
//     signUpJson.password = signUpFormPassword.value;
//
//     signUpFormNickname.value = "ADMIN";
//     signUpNicknameValidation();
//     signUpJson.nickname = signUpFormNickname.value;
//
//     signUpFormAuthorities.querySelector("#checkboxRoleAdmin").checked = true;
//     signUpFormAuthorities.querySelector("#checkboxRoleManager").checked = true;
//     // signUpJson.authorities.add("ROLE_ADMIN");
//     // signUpJson.authorities.add("ROLE_MANAGER");
//     // signUpJson.authorities.delete("ROLE_MANAGER");
//     // signUpJson.authorities = ["ROLE_ADMIN", "ROLE_MANAGER"];
// }());

