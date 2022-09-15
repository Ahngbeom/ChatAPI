// export let mbtiTable = document.querySelector("#mbtiListDiv table");

import {removeChildNode, replaceChildNode} from "/js/utility/changeElement.js";

export function allMBTIListContentLoad() {
    replaceChildNode(bannerContainer, "<div class=\"d-flex d-block flex-column\">\n" +
        "        <h1 class=\"d-flex justify-content-center\">My MBTI List</h1>\n" +
        "    </div>");
    contentContainer.innerHTML = "<div id=\"mbtiListDiv\" class=\"flex-column\">\n" +
        "        <div class=\"d-flex justify-content-end\" id=\"btnAreaForMbtiTable\">\n" +
        "            <button class=\"btn btn-info me-3\" onclick=\"location.href='/mbti/register'\">Add</button>\n" +
        "        </div>\n" +
        "        <table class=\"table table-striped table-hover table-responsive\" id='mbtiTable'>\n" +
        "            <thead>\n" +
        "            <tr>\n" +
        "                <th class=\"col-1\" colspan=\"1\" scope=\"col\">#</th>\n" +
        "                <th class=\"col-6\" colspan=\"1\" scope=\"col\">MBTI</th>\n" +
        "                <th class=\"col-4\" colspan=\"1\" scope=\"col\">Personality</th>\n" +
        "                <th class=\"col-1\" colspan=\"1\" scope=\"col\"></th>\n" +
        "                <!--                <th class=\"col-1\" colspan=\"1\" scope=\"col\">Chatting</th>-->\n" +
        "            </tr>\n" +
        "            </thead>\n" +
        "            <tbody class=\"\">\n" +
        "            </tbody>\n" +
        "        </table>\n" +
        "    </div>";

    let myMBTIList = ajaxGetMyMBTIList();

    if (myMBTIList.length === 0) {
        console.log("MBTI 정보가 없습니다.");
        contentContainer.querySelector("table tbody").innerHTML = "<tr><td colspan='12'><div class='d-flex flex-column align-items-center'></div></td></tr>"
        contentContainer.querySelector("table tbody tr td div").innerHTML = "<h3>MBTI 정보가 없습니다.</h3>" + "<a href='/mbti/register'>MBTI 등록</a>";
    } else {
        mbtiInfoToTable(myMBTIList);
    }
}

export function ajaxGetMyMBTIList() {
    let result = null;
    $.ajax({
        type: 'GET',
        url: '/api/mbti/list',
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            // if (data.length === 0) {
            //     console.log("MBTI 정보가 없습니다.");
            //     mbtiTable.querySelector("tbody").innerHTML = "<tr><td colspan='12'><div class='d-flex flex-column align-items-center'></div></td></tr>"
            //     mbtiTable.querySelector("tbody tr td div").innerHTML = "<h3>MBTI 정보가 없습니다.</h3>" + "<a href='/mbti/register'>MBTI 등록</a>";
            // } else {
            //     mbtiInfoToTable(data);
            // }
            result = data;
        },
        error: function (data) {
            console.error(data);
        }
    });
    return result;
}

export const mbtiInfoToTable = function (mbtiList) {
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