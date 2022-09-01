import {setMovementForUsername} from "./username.js";

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

document.querySelector("#floatingSignUpBtn").addEventListener('click', function () {


    const signUpModal = document.querySelector("#signUpModal");
    signUpModal.querySelector(".modal-dialog").classList.add("modal-lg");
    signUpModal.querySelector(".modal-title").innerHTML = "Sign Up";
    signUpModal.querySelector(".modal-body").innerHTML = "<div class=\"d-flex flex-column\">\n" +
        "        <th:block th:replace=\"/fragments/alert :: alert\"/>\n" +
        "        <div class=\"d-flex justify-content-center w-100 mx-1\">\n" +
        "            <form action=\"/signup\" method=\"post\" class=\"form-group flex-column w-100\" id=\"signUpForm\">\n" +
        "                <div class=\"input-group mb-3 row\">\n" +
        "                    <label for=\"signUpFormUsername\" class=\"input-group-text col-sm-3 col-form-label\">ID</label>\n" +
        "                    <input type=\"text\" class=\"form-control\" id=\"signUpFormUsername\" name=\"username\"/>\n" +
        "                    <button class=\"btn btn-secondary col-sm-2\" type=\"button\" id=\"signUpFormUsernameValidateBtn\">Validate\n" +
        "                    </button>\n" +
        "                </div>\n" +
        "                <div class=\"input-group mb-3 row\">\n" +
        "                    <label for=\"signUpFormPassword\" class=\"input-group-text col-sm-3 col-form-label\">Password</label>\n" +
        "                    <input type=\"password\" class=\"form-control\" id=\"signUpFormPassword\" name=\"password\"/>\n" +
        "                </div>\n" +
        "                <div class=\"input-group mb-3 row\">\n" +
        "                    <label for=\"signUpFormReConfirmPassword\" class=\"input-group-text col-sm-3 col-form-label\">Reconfirm Password</label>\n" +
        "                    <input type=\"password\" class=\"form-control\" id=\"signUpFormReConfirmPassword\" disabled/>\n" +
        "                </div>\n" +
        "                <div class=\"input-group mb-3 row\">\n" +
        "                    <label for=\"singUpFormNickname\" class=\"input-group-text col-sm-3 col-form-label\">Nickname</label>\n" +
        "                    <input type=\"text\" class=\"form-control\" id=\"singUpFormNickname\" name=\"nickname\"/>\n" +
        "                    <button class=\"btn btn-secondary col-sm-2\" type=\"button\" id=\"signUpFormNicknameValidateBtn\">Validate\n" +
        "                    </button>\n" +
        "                </div>\n" +
        "                <div class=\"input-group row mb-3\">\n" +
        "                    <span class=\"input-group-text col-sm-3\">Authority</span>\n" +
        "                    <div class=\"form-control col-sm-8 p-2 d-flex justify-content-around\"\n" +
        "                         id=\"signUpFormAuthorities\">\n" +
        "                        <div class=\"form-check\">\n" +
        "                            <input class=\"form-check-input\" type=\"checkbox\" name=\"authorities\"\n" +
        "                                   id=\"checkboxRoleAdmin\"\n" +
        "                                   value=\"ROLE_ADMIN\">\n" +
        "                            <label class=\"form-check-label\" for=\"checkboxRoleAdmin\">ADMIN</label>\n" +
        "                        </div>\n" +
        "                        <div class=\"form-check\">\n" +
        "                            <input class=\"form-check-input\" type=\"checkbox\" name=\"authorities\"\n" +
        "                                   id=\"checkboxRoleManager\"\n" +
        "                                   value=\"ROLE_MANAGER\">\n" +
        "                            <label class=\"form-check-label\" for=\"checkboxRoleManager\">MANAGER</label>\n" +
        "                        </div>\n" +
        "                        <div class=\"form-check\">\n" +
        "                            <input class=\"form-check-input\" type=\"checkbox\" name=\"authorities\" id=\"checkboxRoleUser\"\n" +
        "                                   value=\"ROLE_USER\">\n" +
        "                            <label class=\"form-check-label\" for=\"checkboxRoleUser\">USER</label>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "                <div class=\"d-flex justify-content-end\">\n" +
        "                    <button type=\"reset\" class=\"btn btn-warning mx-3\">Reset</button>\n" +
        "                    <button type=\"button\" class=\"btn btn-info mx-3\" onclick=\"signUp()\">Submit</button>\n" +
        "                </div>\n" +
        "            </form>\n" +
        "        </div>\n" +
        "    </div>";


    signUpModal.addEventListener('shown.bs.modal', function () {

        const signUpForm = document.querySelector("#signUpForm");
        const signUpFormPassword = signUpForm.querySelector("input[name='password']");
        const signUpFormReconfirmPassword = document.querySelector("#signUpFormReConfirmPassword");
        const signUpFormNickname = signUpForm.querySelector("input[name='nickname']");
        const signUpFormNicknameValidateBtn = signUpForm.querySelector("#signUpFormNicknameValidateBtn");
        const signUpFormAuthorities = signUpForm.querySelector("#signUpFormAuthorities");
        const signUpFormAuthorityInputList = signUpForm.querySelectorAll("input[name='authorities']");


        setMovementForUsername(signUpForm);

        /** Password */
        setPopoverToSignUpForm('hover focus', signUpFormPassword, signUpInputGuides.password);
        signUpFormPassword.addEventListener('input', function (e) {
            const password = e.target.value;
            if (password.length < 4) {
                signUpFormPassword.classList.add('is-invalid');
                signUpFormReconfirmPassword.setAttribute('disabled', "true");
            } else {
                signUpFormPassword.classList.replace('is-invalid', 'is-valid');
                signUpFormReconfirmPassword.removeAttribute('disabled');
                setPopoverToSignUpForm('hover focus', signUpFormReconfirmPassword, "비밀번호를 다시 입력해주세요.");
                signUpFormReconfirmPassword.addEventListener('input', function (e) {
                    if (password === e.target.value) {
                        setPopoverToSignUpForm('hover focus', signUpFormReconfirmPassword, null);
                        if (signUpFormReconfirmPassword.classList.contains('is-invalid'))
                            signUpFormReconfirmPassword.classList.replace('is-invalid', 'is-valid');
                        else
                            signUpFormReconfirmPassword.classList.add('is-valid');
                        signUpJson.password = password;
                    } else {
                        setPopoverToSignUpForm('hover focus', signUpFormReconfirmPassword, "비밀번호가 일치하지않습니다.");
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
                    if (signUpFormNickname.value.length < 2 || koreanConsonantsAndVowels.test(signUpFormNickname.value)) {
                        setPopoverToSignUpForm('hover focus', signUpFormNickname, signUpInputGuides.nickname).show();
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
                        createToast($("#modalToast"), "danger", "Alert", "이미 존재하는 Nickname 입니다.");
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
        setPopoverToSignUpForm('hover focus', signUpFormAuthorities, signUpInputGuides.authorities);
        signUpFormAuthorityInputList.forEach(auth => {
            auth.addEventListener('change', function (e) {
                signUpFormAuthorities.classList.replace('border-danger', 'border-1');
                if (e.target.checked)
                    signUpJson.authorities.add(e.target.value);
                else
                    signUpJson.authorities.delete(e.target.value);
            });
        });

        /** Sign Up */
        const signUp = function () {
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
                signUpFormAuthorities.classList.replace('border-1', 'border-danger');
                setPopoverToSignUpForm('hover focus', signUpFormAuthorities, signUpInputGuides.authorities).show();
            } else {

                // userAuthorityFormatConverter();

                signUpJson.authorities = [...signUpJson.authorities];
                console.log(signUpJson);
                console.log(JSON.stringify(signUpJson));

                $.ajax({
                    type: 'POST',
                    url: '/signup',
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(signUpJson),
                    success: function (data, status, xhr) {
                        console.log(data);
                        console.log(status);
                        console.log(xhr);
                        // window.location.href = '/login';
                    },
                    error: function (data, status, xhr) {
                        console.error(data);
                        console.error(status);
                        console.error(xhr);
                    }
                });
            }
        };

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

    if (signUpModal) {
        const modal = new bootstrap.Modal(signUpModal);
        modal.show();
    }
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

