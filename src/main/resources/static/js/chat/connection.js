/** Connection Chatting room **/

import {JoinAvailability, LeaveAvailability} from "./availability.js";
import {getChatLog, putMessage, sendMessage} from "./message.js";
import {
    chatModal,
    chatModalElement,
    leaveChatRoomBtn,
    sendMessageBtn,
    sendMessageInput
} from "./utils.js";

export let socket = null;
export let stompClient = null;

function connect(roomId) {
    socket = new SockJS('/ws/mbti-chat'); // SockJS 통해 서버의 WebSocket 지원 여부, Cookies 필요 여부, CORS를 위한 Origin 정보 등을 응답으로 전달받는다.
    stompClient = Stomp.over(socket); // Stomp.over: WebSocket 지정

    stompClient.connect({}, function (frame) {
        // console.log('Connected: ' + frame);

        stompClient.send("/mbti-chat/join-room/" + roomId, {}, {});

        stompClient.subscribe('/topic/mbti-chat/' + roomId, function (Message) {
            putMessage(JSON.parse(Message.body));
        });

        sendMessageBtn.addEventListener('click', function () {
            sendMessage(roomId);
        });

        leaveChatRoomBtn.addEventListener('click', function () {
            disconnect(roomId);
        });

        chatModal.show();

    });
}

function disconnect(roomName) {
    if (LeaveAvailability(roomName)) {
        stompClient.send("/mbti-chat/leave-room/" + roomName, {}, {});
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");
        chatModal.hide();
    }
}

$(".join-chat-room-btn").on('click', function () {
    const roomId = $(this).data("room-id");
    if (JoinAvailability(roomId)) {
        getChatLog(roomId);
        connect(roomId);
    }
});

chatModalElement.addEventListener('hidden.bs.modal', function () {
    chatModalElement.querySelector(".modal-body").innerHTML = "";
    sendMessageInput.value = "";
    location.reload();
});
