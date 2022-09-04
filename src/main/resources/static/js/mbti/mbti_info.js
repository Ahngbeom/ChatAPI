const mbtiTable = document.querySelector("#mbtiListDiv table");

if (mbtiTable) {
    $.ajax({
        type: 'GET',
        url: '/mbti/list',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            // console.log(data);
            if (data.length === 0) {
                console.log("MBTI 정보가 없습니다.");
                mbtiTable.querySelector("tbody").innerHTML = "<h3>MBTI 정보가 없습니다.</h3>" + "<a href='/mbti/register'>MBTI 등록</a>";
            } else {
                mbtiInfo2Page(data);
            }
        },
        error: function (data) {
            console.error(data);
        }
    });
}

const mbtiInfo2Page = function (mbtiList) {
    mbtiList.forEach(function (data, index) {

        let row = mbtiTable.querySelector("tbody").insertRow(-1);

        let indexCell = row.insertCell(0);
        let mbtiCell = row.insertCell(1);
        let personalityCell = row.insertCell(2);
        let chattingCell = row.insertCell(3);

        indexCell.innerText = index;
        mbtiCell.innerText = data.mbti;
        personalityCell.innerText = data.personality;
        chattingCell.innerHTML = "<button class='btn btn-primary' onclick='chattingRoomEnter()'>Enter</button>";

    });

    mbtiTable.querySelector("tfoot tr").insertAdjacentHTML('beforeend', "<td colspan='4'><a href='/mbti/register'>Change</a></td>");
}

function chattingRoomEnter() {
    connect();
    chatModal.show();
}

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
                    "      <h4 class=\"mb-1\">" + mbtiData.mbti + "</h4>\n" +
                    "      <h5>" + mbtiData.personality + "</h5>\n" +
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
