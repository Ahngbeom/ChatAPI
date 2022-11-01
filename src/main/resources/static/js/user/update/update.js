const userUpdateTable = document.querySelector("#userUpdateTable");
const userUpdateSubmitBtn = document.querySelector("#userUpdateSubmitBtn");

userUpdateSubmitBtn.addEventListener('click', function () {

    let userUpdateData = {
        username: userUpdateTable.querySelector("input[name='username']").value,
        email: userUpdateTable.querySelector("input[name='email']").value,
        nickname: userUpdateTable.querySelector("input[name='nickname']").value,
        authorities: []
    }
    userUpdateTable.querySelectorAll("input[name='authorities']:checked").forEach(checkedAuth => {
        userUpdateData.authorities.push(checkedAuth.value);
    });
    console.log(userUpdateData);

    $.ajax({
        type: 'POST',
        url: '/api/user/update',
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        data: JSON.stringify(userUpdateData),
        success: function (data) {
            renewalModal({
                target: confirmModalElem,
                type: "success",
                title: "변경 사항이 적용되었습니다."
            });
            showModalTarget(confirmModalElem);
        },
        error: function (xhr) {
            console.error(xhr.responseText);
            renewalModal({
                target: confirmModalElem,
                type: "danger",
                title: "Error",
                body: xhr.responseText
            });
            showModalTarget(confirmModalElem);
        }
    })
});

