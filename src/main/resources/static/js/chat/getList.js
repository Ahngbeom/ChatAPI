/** AJAX for Chatting Room List **/

const chatRoomTable = document.querySelector("#chatRoomListDiv table");

$.ajax({
    type: 'GET',
    url: '/chat/list',
    contentType: 'application/json; charset=utf-8',
    success: function (data) {
        console.log(data);
        createRowsForChatRoomList(chatRoomTable, data);
    },
    error: function (data) {
        console.error(data);
    }
});

const createRowsForChatRoomList = function (targetTable, jsonList) {
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
        joinCell.innerHTML = "<button class='btn btn-warning' onclick='joinChattingRoom()'>Join</button>";

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