import {replaceChildNode} from "/js/utility/changeElement.js";
import {enableJoinChattingService} from "./connection.js";

/** AJAX for Chatting Room List **/

export const getChatRoomListContentLoad = function () {
    replaceChildNode(bannerContainer, "<div class=\"d-flex d-block flex-column\">\n" +
        "        <h1 class=\"d-flex justify-content-center\">User List</h1>\n" +
        "    </div>");
    replaceChildNode(contentContainer, "<div id=\"chatRoomListDiv\" class=\"flex-column\">\n" +
        "        <div class=\"d-flex justify-content-end\" id=\"btnAreaForchatRoomList\">\n" +
        "            <button class=\"btn btn-info me-3\" onclick=\"location.href='/chat/create'\">Create</button>\n" +
        "        </div>\n" +
        "        <table class=\"table table-striped table-hover table-responsive\" id='chatRoomTable'>\n" +
        "            <thead>\n" +
        "            <tr>\n" +
        "                <th class=\"col-7\" colspan=\"1\" scope=\"col\">Room Name</th>\n" +
        "                <th class=\"col-4\" colspan=\"1\" scope=\"col\">Concurrent connected User</th>\n" +
        "                <th class=\"col-1\" colspan=\"1\" scope=\"col\"></th>\n" +
        "            </tr>\n" +
        "            </thead>\n" +
        "            <tbody class=\"\">\n" +
        "            </tbody>\n" +
        "        </table>\n" +
        "    </div>");
    createRowsForChatRoomList(document.querySelector("#chatRoomTable"), getChatRoomList());
    enableJoinChattingService();
}

export const getChatRoomList = function () {
    let result = null;
    $.ajax({
        type: 'GET',
        url: '/chat/list',
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            // console.log(data);
            // createRowsForChatRoomList(chatRoomTable, data);
            console.log(data);
            result = data;
        },
        error: function (data) {
            console.error(data);
        }
    });
    return result;
}

export const createRowsForChatRoomList = function (targetTable, jsonList) {
    jsonList.forEach(function (data, index) {

        let row = targetTable.querySelector("tbody").insertRow(-1);
        let detailRow = targetTable.querySelector("tbody").insertRow(-1);

        let roomNameCell = row.insertCell(0);
        let ccuCell = row.insertCell(1);
        let joinCell = row.insertCell(2);

        row.id = "flush-heading" + index;
        row.setAttribute("data-bs-toggle", "collapse");
        row.setAttribute("data-bs-target", "#flush-collapse" + index);
        row.setAttribute("aria-expanded", "false");
        row.setAttribute("aria-controls", "flush-collapse" + index);

        roomNameCell.classList.add("col-8");
        roomNameCell.innerText = data.roomName;
        ccuCell.classList.add("col-3");
        ccuCell.innerHTML = "<h3><span class=\"badge text-bg-info ms-3\">" + "???" + "</span></h3>";
        joinCell.classList.add("col-1");
        joinCell.innerHTML = "<button class='btn btn-warning joinChattingRoomBtn'>Join</button>";

        detailRow.insertAdjacentHTML("beforeend", "<td colspan=\"12\" class=\"p-0\">\n" +
            "                    <div class=\"accordion accordion-flush\" id=\"accordionFlush" + index + "\">\n" +
            "                        <div class=\"accordion-item\">\n" +
            "                            <div id=\"flush-collapse" + index + "\" class=\"accordion-collapse collapse\"\n" +
            "                                 aria-labelledby=\"flush-heading" + index + "\" data-bs-parent=\"#accordionFlush" + index + "\">\n" +
            "                                <div class=\"accordion-body\">" +
            "                                       <div class='d-flex justify-content-between'>" +
            "                                           <div class='h1 d-inline'>" + data.roomName +
            "                                               <div class='h3 d-inline'>(등록 횟수: <span class=\"badge text-bg-info ms-1\">" + "???" + "</span>)</div>" +
            "                                               <div class='h3'>개설자: " + "???" + "</div>" +
            "                                               <span class='h6 d-block'>개설 날짜: " + data.createDate + "</span>" +
            "                                           </div>" +
            "                                       </div>" +
            "                                       <p class='mt-3'>" + data.description + "</p>" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </td>");
    });
}