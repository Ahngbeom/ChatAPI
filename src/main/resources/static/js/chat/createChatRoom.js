import {chatRoomJsonData, formDataToJsonFormatterForChatRoom} from "./utils.js";

const createChatRoomBtn = document.querySelector("#createChatRoomBtn");

export const createChatRoomModalElem = document.getElementById("CreateChatRoomModal");
export const createChatRoomSubmitBtn = createChatRoomModalElem.querySelector("#CreateChatRoomSubmitBtn");

createChatRoomBtn.addEventListener("click", function () {
    showModalTarget(createChatRoomModalElem);
});

createChatRoomSubmitBtn.addEventListener("click", function () {
    if (formDataToJsonFormatterForChatRoom(createChatRoomModalElem.querySelector("form"))) {
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
    }
});




