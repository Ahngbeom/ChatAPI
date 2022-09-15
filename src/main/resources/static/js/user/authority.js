export function ajaxGetUserAuthorities(userNo) {
    $.ajax({
        type: 'GET',
        url: '/user-authorities',
        contentType: "application/json; charset=utf-8",
        dataType: 'JSON',
        data: {userNo: userNo},
        success: function (data) {
            const modalElem = document.querySelector("#staticBackdropModal");
            renewalModal({
                target: modalElem,
                title: "User's Authorities",
                body: "<ul class=\"list-group\">\n" +
                    "  <li class=\"list-group-item\">\n" +
                    "    <input class=\"form-check-input me-1\" type=\"checkbox\" value=\"ROLE_ADMIN\" id='firstCheckbox' disabled>\n" +
                    "    <label class=\"form-check-label col-11\" for='firstCheckbox'>ROLE_ADMIN</label>\n" +
                    "  </li>\n" +
                    "  <li class=\"list-group-item\">\n" +
                    "    <input class=\"form-check-input me-1\" type=\"checkbox\" value=\"ROLE_MANAGER\" id='secondCheckbox' disabled>\n" +
                    "    <label class=\"form-check-label col-11\" for='secondCheckbox'>ROLE_MANAGER</label>\n" +
                    "  </li>\n" +
                    "  <li class=\"list-group-item\">\n" +
                    "    <input class=\"form-check-input me-1\" type=\"checkbox\" value=\"ROLE_USER\" id='thirdCheckbox' disabled>\n" +
                    "    <label class=\"form-check-label col-11\" for='thirdCheckbox'>ROLE_USER</label>\n" +
                    "  </li>" +
                    "</ul>",
                interactionBtnText: "권한 변경",
                interactionBtnType: "warning",
            });
            data.forEach(auth => {
                document.querySelector(".modal-body .list-group .list-group-item input[value='" + auth.authorityName + "']").checked = true;
            });
            modalElem.querySelector("#modalInteractionBtn").innerHTML = "변경";
            modalElem.querySelector("#modalInteractionBtn").classList.replace("btn-primary", "btn-warning");
            modalElem.querySelector("#modalInteractionBtn").addEventListener('click', function () {
                modalElem.querySelectorAll(".modal-body input").forEach(input => {
                    input.removeAttribute("disabled");
                });
                modalElem.querySelector("#modalInteractionBtn").innerHTML = "변경 사항 적용";
                modalElem.querySelector("#modalInteractionBtn").classList.replace("btn-warning", "btn-primary");
            });
            modalElem.querySelector("#modalInteractionBtn").classList.remove("visually-hidden");
            showModalTarget(modalElem);
        },
        error: function (data) {
            console.error(data);
        }
    });
}