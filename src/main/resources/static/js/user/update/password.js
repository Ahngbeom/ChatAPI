const changeUserPasswordBtn = document.querySelector("#changeUserPasswordBtn");
const changeUserPwModal = document.querySelector("#changeUserPwModal");

let pwData = {
    oldPassword: null,
    newPassword: null
}

changeUserPasswordBtn.addEventListener('click', function () {
    ExistPasswordCheck();
});

function ExistPasswordCheck() {
    renewalModal({
        target: changeUserPwModal,
        type: "primary",
        title: "기존 비밀번호",
        body: "<input type='password' class='form-control'/>"
    }, "<button type='button' class='btn btn-primary' id='existPasswordSubmitBtn'>Submit</button>")
    showModalTarget(changeUserPwModal);

    $("#existPasswordSubmitBtn").one('click', function () {
        pwData.oldPassword = changeUserPwModal.querySelector("input[type='password']").value
        updateNewPassword();
    });
}

function updateNewPassword() {
    renewalModal({
        target: changeUserPwModal,
        type: "warning",
        title: "새로운 비밀번호",
        body: "<div class=\"mb-3 row\">\n" +
            "    <label for=\"inputPassword\" class=\"col-sm-2 col-form-label\">Password</label>\n" +
            "    <div class=\"col-sm-10\">\n" +
            "       <label for=\"inputPassword\" class=\"form-label\"></label>" +
            "      <input type=\"password\" class=\"form-control\" id=\inputPassword\">\n" +
            "    </div>\n" +
            "  </div>\n" +
            "  <div class=\"mb-3 row\">\n" +
            "    <label for=\"inputConfirmPassword\" class=\"col-sm-2 col-form-label\">Reconfirm</label>\n" +
            "    <div class=\"col-sm-10\">\n" +
            "       <label for=\"inputConfirmPassword\" class=\"form-label\"></label>" +
            "      <input type=\"password\" class=\"form-control\" id=\"inputConfirmPassword\">\n" +
            "    </div>\n" +
            "  </div>"
    }, "<button type='button' class='btn btn-warning' id='updateNewPasswordSubmitBtn'>Update</button>")
    showModalTarget(changeUserPwModal);

    $("#updateNewPasswordSubmitBtn").on('click', function () {
        const inputPasswords = changeUserPwModal.querySelectorAll("input[type='password']");
        if (inputPasswords[0].value.length < 4) {
            inputPasswords[0].classList.add("is-invalid");
        }

        if (inputPasswords[0].value === inputPasswords[1].value) {
            pwData.newPassword = changeUserPwModal.querySelectorAll("input[type='password']")[1].value
            $.ajax({
                type: 'POST',
                url: '/api/user/change-password',
                data: pwData,
                success: function (data, status, xhr) {
                    renewalModal({
                        target: confirmModalElem,
                        type: "success",
                        title: xhr.responseText
                    });
                    showModalTarget(confirmModalElem);
                },
                error: function (xhr) {
                    console.error(xhr.responseText);
                    renewalModal({
                        target: confirmModalElem,
                        type: "danger",
                        title: xhr.responseText
                    });
                    showModalTarget(confirmModalElem);
                }
            });
        } else {
            inputPasswords[0].classList.add("is-invalid");
            inputPasswords[1].classList.add("is-invalid");
            // $("label[for='" + inputPasswords[1].id + "']").html("비밀번호가 일치하지 않습니다.");
        }
    });
}
