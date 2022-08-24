const mbtiTable = document.querySelector("#mbtiListDiv table");

$.ajax({
    type: 'GET',
    url: '/mbti/list',
    contentType: 'application/json; charset=utf-8',
    success: function (data) {
        console.log(data);
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
