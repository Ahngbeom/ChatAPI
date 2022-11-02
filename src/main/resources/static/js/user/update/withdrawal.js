const simpleInquiryModal = document.querySelector("#simpleInquiryModal");
const userWithdrawalBtn = document.querySelector("#userWithdrawalBtn");

userWithdrawalBtn.addEventListener('click', function () {
    renewalModal({
        target: simpleInquiryModal,
        type: 'danger',
        title: "회원 탈퇴",
        body: "<span>비밀번호를 입력해주세요.</span>" +
            "<input type='password' class='form-control' />"
    }, "<button type='button' class='btn btn-danger' id='userWithdrawalSubmitBtn'>탈퇴</button>");

    $("#userWithdrawalSubmitBtn").one('click', function () {
        console.log(simpleInquiryModal.querySelector("input[type='password']").value)
        $.ajax({
            type: 'POST',
            url: '/api/user/remove',
            data: {password: simpleInquiryModal.querySelector("input[type='password']").value},
            success: function (data) {
                console.log(data);
                renewalModal({
                    target: simpleInquiryModal,
                    type: 'success',
                    title: data
                });
                showModalTarget(simpleInquiryModal);
            },
            error: function (xhr) {
                console.error(xhr.responseText);
                renewalModal({
                    target: simpleInquiryModal,
                    type: 'danger',
                    title: xhr.responseText
                });
                showModalTarget(simpleInquiryModal);
            }
        })

    });
    showModalTarget(simpleInquiryModal);

});