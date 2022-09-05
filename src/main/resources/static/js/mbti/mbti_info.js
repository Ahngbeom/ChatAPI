const mbtiTable = document.querySelector("#mbtiListDiv table");

if (mbtiTable) {
    $.ajax({
        type: 'GET',
        url: '/mbti/list',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log(data);
            if (data.length === 0) {
                console.log("MBTI 정보가 없습니다.");
                mbtiTable.querySelector("tbody").innerHTML = "<tr><td colspan='12'><div class='d-flex flex-column align-items-center'></div></td></tr>"
                mbtiTable.querySelector("tbody tr td div").innerHTML = "<h3>MBTI 정보가 없습니다.</h3>" + "<a href='/mbti/register'>MBTI 등록</a>";
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
        let detailRow = mbtiTable.querySelector("tbody").insertRow(-1);

        let indexCell = row.insertCell(0);
        let mbtiCell = row.insertCell(1);
        let personalityCell = row.insertCell(2);
        let detailBtnCell = row.insertCell(3);
        let chattingCell = row.insertCell(4);

        row.id = "flush-heading" + index;
        row.setAttribute("data-bs-toggle", "collapse");
        row.setAttribute("data-bs-target", "#flush-collapse" + index);
        row.setAttribute("aria-expanded", "false");
        row.setAttribute("aria-controls", "flush-collapse" + index);

        indexCell.classList.add("col-1");
        indexCell.innerText = index;
        mbtiCell.classList.add("col-6");
        mbtiCell.innerHTML = data.code + "<span class=\"badge text-bg-info ms-3\">" + data.numberOfTimes + "</span>";
        personalityCell.classList.add("col-3");
        personalityCell.innerText = data.personality;
        detailBtnCell.classList.add("col-1");
        detailBtnCell.innerHTML = "<button type=\"button\" class='btn btn-warning' data-bs-toggle=\"collapse\" data-bs-target=\"#accordionExample\" aria-expanded=\"false\" aria-controls=\"accordionExample\">Detail</button>";
        chattingCell.classList.add("col-1");
        chattingCell.innerHTML = "<button class='btn btn-primary' onclick='chattingRoomEnter()'>Enter</button>";


        detailRow.insertAdjacentHTML("beforeend", "<td colspan=\"12\" class=\"p-0\">\n" +
            "                    <div class=\"accordion accordion-flush\" id=\"accordionFlush" + index + "\">\n" +
            "                        <div class=\"accordion-item\">\n" +
            "                            <div id=\"flush-collapse" + index + "\" class=\"accordion-collapse collapse\"\n" +
            "                                 aria-labelledby=\"flush-heading" + index + "\" data-bs-parent=\"#accordionFlush" + index + "\">\n" +
            "                                <div class=\"accordion-body\">" +
            "                                       <div class='d-flex justify-content-between'>" +
            "                                           <div class='h1 d-inline'>" + data.code +
            "                                               <div class='h3 d-inline'>(등록 횟수: <span class=\"badge text-bg-info ms-1\">" + data.numberOfTimes + "</span>)</div>" +
            "                                               <div class='h3'>" + data.personality + "</div>" +
            "                                           </div>" +
            "                                           <img class='w-50' src='" + data.imgSrc + "'/>" +
            "                                       </div>" +
            "                                       <p class='mt-3'>" + data.introduction + "</p>" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </td>");

    });
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
