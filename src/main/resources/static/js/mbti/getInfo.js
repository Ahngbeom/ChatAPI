
export const MBTI_CODES = [
    "ENFJ",
    "ENFP",
    "ENTJ",
    "ENTP",
    "ESFJ",
    "ESFP",
    "ESTJ",
    "ESTP",
    "INFJ",
    "INFP",
    "INTJ",
    "INTP",
    "ISFJ",
    "ISFP",
    "ISTJ",
    "ISTP",
].sort();

export const ajaxAdminGetUserMbtiList = function (username) {
    $.ajax({
        type: 'GET',
        url: '/api/mbti/list',
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        data: {username: username},
        success: function (data) {
            console.log(data);
            const modalElem = document.querySelector("#simpleInquiryModal");
            renewalModal({
                target: modalElem,
                title: "User's MBTI List",
                body: "<ul class=\"list-group\"></ul>"
            });
            if (data.length > 0) {
                data.forEach(mbtiData => {
                    modalElem.querySelector(".modal-body .list-group").innerHTML += "<li class=\"list-group-item\">" +
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
