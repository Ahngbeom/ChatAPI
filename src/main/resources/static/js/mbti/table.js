export const mbtiTable = document.querySelector("#mbtiListDiv table");

export const ajaxGetUserMBTIListNoParam = function () {
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
                mbtiInfoToTable(data);
            }
        },
        error: function (data) {
            console.error(data);
        }
    });
}

const mbtiInfoToTable = function (mbtiList) {
    mbtiList.forEach(function (data, index) {

        let row = mbtiTable.querySelector("tbody").insertRow(-1);
        let detailRow = mbtiTable.querySelector("tbody").insertRow(-1);

        let indexCell = row.insertCell(0);
        let mbtiCell = row.insertCell(1);
        let personalityCell = row.insertCell(2);
        let alterCell = row.insertCell(3);
        // let chattingCell = row.insertCell(3);

        row.id = "flush-heading" + index;
        row.setAttribute("data-bs-toggle", "collapse");
        row.setAttribute("data-bs-target", "#flush-collapse" + index);
        row.setAttribute("aria-expanded", "false");
        row.setAttribute("aria-controls", "flush-collapse" + index);

        indexCell.classList.add("col-1");
        indexCell.innerText = index;
        mbtiCell.classList.add("col-6");
        mbtiCell.innerHTML = data.code + "<span class=\"badge text-bg-info ms-3\">" + data.numberOfTimes + "</span>";
        personalityCell.classList.add("col-4");
        personalityCell.innerText = data.personality;
        alterCell.classList.add("col-1");
        alterCell.innerHTML = "<button class='btn btn-warning' onclick='floatingAlterUserModal()'>Alter</button>";
        // chattingCell.classList.add("col-1");
        // chattingCell.innerHTML = "<button class='btn btn-primary' onclick='chattingRoomEnter()'>Enter</button>";
        // chattingCell.innerHTML = "<button class='btn btn-primary' onclick=\"location.href='/chat'\">Enter</button>";


        const regDate = new Date(data.regDate);
        const recentRegDate = new Date(data.recentRegDate);
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
            "                                               <span class='h6 d-block'>최초 등록 날짜: " + regDate.toLocaleDateString() + " " + regDate.toLocaleTimeString() + "</span>" +
            "                                               <span class='h6 d-block'>최근 등록 날짜: " + recentRegDate.toLocaleDateString() + " " + recentRegDate.toLocaleTimeString() + "</span>" +
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