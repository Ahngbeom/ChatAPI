import {enableAllPermitMBTICodeSwitch, enableSelectPermitMBTICode} from "./checkPermitMBTI.js";

const createChatRoomBtn = document.querySelector("#createChatRoomBtn");

export const createChatRoomModalElem = document.getElementById("CreateChatRoomModal");
export const createChatRoomSubmitBtn = createChatRoomModalElem.querySelector("#CreateChatRoomSubmitBtn");

export let chatRoomJsonData = {
    roomName: null,
    description: null,
    permitMBTICode: new Set()
}

createChatRoomBtn.addEventListener("click", function () {
    showModalTarget(createChatRoomModalElem);

    enableAllPermitMBTICodeSwitch();
    enableSelectPermitMBTICode();

    createChatRoomSubmitBtn.addEventListener("click", function () {
        chatRoomJsonData.roomName = createChatRoomModalElem.querySelector("input[name='roomName']").value;
        chatRoomJsonData.description = createChatRoomModalElem.querySelector("textarea[name='description']").value;

        if (chatRoomJsonData.roomName.length === 0) {
            createToast({
                target: document.getElementById("modalToast"),
                type: "warning",
                header: "Alert",
                body: "채팅방 이름을 입력해주세요."
            });
            return;
        }
        if (chatRoomJsonData.permitMBTICode.size === 0) {
            createToast({
                target: document.getElementById("modalToast"),
                type: "warning",
                header: "Alert",
                body: "채팅방에 입장 가능한 MBTI를 선택해주세요."
            });
            return;
        }

        console.log(chatRoomJsonData);

        $.ajax({
            type: 'POST',
            url: '/api/chat/create',
            contentType: 'application/json; charset=utf-8',
            dataType: 'JSON',
            data: JSON.stringify({
                roomName: chatRoomJsonData.roomName,
                description: chatRoomJsonData.description,
                permitMBTICode: Array.from(chatRoomJsonData.permitMBTICode)
            }),
            success: function (data) {
                console.log(data);
                location.reload();
            },
            error: function (data) {
                console.log(data);
            }
        });
    });
});




