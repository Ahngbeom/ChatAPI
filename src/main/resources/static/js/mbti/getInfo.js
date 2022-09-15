
export const ajaxAdminGetUserMbtiList = function (userNo) {
    $.ajax({
        type: 'GET',
        url: '/api/mbti/list',
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        data: {userNo: userNo},
        success: function (data) {
            console.log(data.length);
            const modalElem = document.querySelector("#staticBackdropModal");
            renewalModal({
                target: modalElem,
                title: "User's MBTI List",
                body: "<ul class=\"list-group\"></ul>"
            });
            if (data.length > 0) {
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
            } else {
                document.querySelector(".modal-body").innerHTML = "<h3 class='text-center text-muted'>No MBTI registered.</h3>";
            }
            modalElem.querySelector("#modalInteractionBtn").classList.add("visually-hidden");
            showModalTarget(modalElem);
        },
        error: function (data) {
            console.error(data);
        }
    });
}
