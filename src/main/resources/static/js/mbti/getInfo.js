import {mbtiTable, ajaxGetUserMBTIList} from "./table.js"

if (mbtiTable)
    ajaxGetUserMBTIList();

export const ajaxGetUserMbtiList = function (userNo) {
    $.ajax({
        type: 'GET',
        url: '/mbti/list',
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        data: {userNo: userNo},
        success: function (data) {
            const modalElem = document.querySelector("#viewUserInfoModal");
            renewalModal({
                target: modalElem,
                title: "User's MBTI List",
                body: "<ul class=\"list-group\"></ul>"
            });
            data.forEach(mbtiData => {
                document.querySelector(".modal-body .list-group").innerHTML = "<li class=\"list-group-item\">" +
                    "    <div class=\"d-flex w-100 justify-content-between\">\n" +
                    "       <div class=\"mb-1 h3\">" + mbtiData.code +
                    "           <div class='h5'>" + mbtiData.personality + "</div>" +
                    "       </div>\n" +
                    "       <img class='w-50' src='" + mbtiData.imgSrc + "'/>" +
                    "    </div>\n" +
                    "    <p class=\"mb-1\">" + mbtiData.introduction + "</p>\n" +
                    "</li>";
            });
            modalElem.querySelector("#modalInteractionBtn").classList.add("visually-hidden");
            showModalTarget(modalElem);
        },
        error: function (data) {
            console.error(data);
        }
    });
}
