/** Connection Chatting room **/

const joinChattingRoomBtn = document.querySelectorAll(".join-chat-room-btn");
const chatModal = new bootstrap.Modal("#chatModal");
const chatModalElement = document.getElementById("chatModal");
const sendMessageInput = chatModalElement.querySelector("#input-send-message");
const sendMessageBtn = document.getElementById("chat-send-message-btn");
const leaveChatRoomBtn = document.getElementById("chat-leave-btn");

let socket = null;
let stompClient = null;


joinChattingRoomBtn.forEach(btn => {
    btn.addEventListener('click', function (e) {
        const roomName = $(this).data("room-name");
        $.ajax({
            type: 'GET',
            url: '/api/chat/join-availability/' + roomName,
            async: false,
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                connect(roomName);
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        })
    });
});

chatModalElement.addEventListener('hidden.bs.modal', function () {
    chatModalElement.querySelector(".modal-body").innerHTML = "";
    sendMessageInput.value = "";
});

function connect(roomName) {
    socket = new SockJS('/ws/mbti-chat'); // SockJS 통해 서버의 WebSocket 지원 여부, Cookies 필요 여부, CORS를 위한 Origin 정보 등을 응답으로 전달받는다.
    stompClient = Stomp.over(socket); // Stomp.over: WebSocket 지정

    stompClient.connect({}, function (frame) {
        // setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.send("/mbti-chat/join-room/" + roomName, {}, {});

        // stompClient.subscribe('/topic/mbti-chat/' + roomName, function (Message) {
        //     putMessage(JSON.parse(Message.body));
        // });

        stompClient.subscribe('/topic/mbti-chat/' + roomName, putMessage);

        sendMessageBtn.addEventListener('click', function () {
            sendMessage(roomName);
        });

        leaveChatRoomBtn.addEventListener('click', function () {
            disconnect(roomName);
        });

        chatModal.show();

    });
}

function sendMessage(roomName) {
    console.log("sendMessage()");
    if (stompClient) {
        stompClient.send("/mbti-chat/send-message/" + roomName, {}, sendMessageInput.value);
        sendMessageInput.value = "";
    } else
        alert("Not Connected.");
}

function putMessage(Message) {
    Message = JSON.parse(Message.body);

    let row = document.createElement("div");
    let alert = document.createElement("div");
    let alertHeading = document.createElement("h5");
    let alertContent = document.createElement("p");
    row.classList.add("row");
    alert.classList.add("alert");
    alertHeading.classList.add("alert-heading");
    alertHeading.append(Message.from);
    alertContent.append(Message.message);
    alert.append(alertHeading);
    alert.append(alertContent);
    row.append(alert);

    if (Message.from === "Server") {
        row.classList.add("justify-content-center");
        if (Message.status === "ERROR")
            alert.classList.add("alert-danger", "col-10");
        else
            alert.classList.add("alert-warning", "col-10");
    } else {
        // if (data.status === "IMPORTANT")
        //     alert.classList.add("alert-success");

        if (Message.from === USER_INFO.username) {
            row.classList.add("justify-content-end");
            alert.classList.add("alert-info", "col-6");
        }
        else {
            row.classList.add("justify-content-start");
            alert.classList.add("alert-secondary", "col-6");
        }
    }
    chatModalElement.querySelector(".modal-body")
        .insertAdjacentElement('beforeend', row);
}

function disconnect(roomName) {
    $.ajax({
        type: "GET",
        url: "/api/chat/leave-chatroom/" + roomName,
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function () {
            stompClient.send("/mbti-chat/leave-room/" +  roomName, {}, {});
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            console.log("Disconnected");
            chatModal.hide();
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function showSendMessage(post) {
    console.log(post);
    // $("#chatting").append("<tr><td>" + post.from + ": " + post.message + "</td></tr>");
    chatModalElement.querySelector(".modal-body").insertAdjacentHTML('beforeend', "<div class='alert alert-primary text-end'>" + post.message + "</div>")
}

function showReceiveMessage(post) {
    console.log(post);
    // $("#chatting").append("<tr><td>" + post.from + ": " + post.message + "</td></tr>");
    chatModalElement.querySelector(".modal-body").insertAdjacentHTML('beforeend', "<div class='alert alert-secondary text-start'>" + post.message + "</div>")
}

// $(function () {
//     // $("form").on('submit', function (e) {
//     //     e.preventDefault();
//     // });
//
//     // $("#connect").click(function () {
//     //     connect();
//     // });
//     // $("#disconnect").click(function () {
//     //     disconnect();
//     // });
//     // $("#send").click(function () {
//     //     sendMessage();
//     // });
// });
