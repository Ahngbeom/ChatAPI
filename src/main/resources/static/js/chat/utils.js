/************ Utilities for Chatting Room ************/
import {
    checkboxMBTIElemList,
    enableAllPermitMBTICodeSwitch,
    enableSelectPermitMBTICode
} from "./checkPermitMBTI.js";

/** disposable Modal Element **/
export const disposableModalElem = document.querySelector("#disposable-modal");

export let chatRoomJsonData = {
    roomId: null,
    roomName: null,
    description: null,
    permitMBTICode: new Set()
}

export function chatRoomJsonData_init(...args) {
    chatRoomJsonData.roomId = args[0];
    chatRoomJsonData.roomName = args[1];
    chatRoomJsonData.description = args[2];
    if (args[3] !== undefined)
        chatRoomJsonData.permitMBTICode = args[3];
    // else
    //     chatRoomJsonData.permitMBTICode = new Set();
}

/** Convert the data in the form to JSON format **/
export function formDataToJsonFormatterForChatRoom(formElem) {
    chatRoomJsonData_init(
        formElem.querySelector("input[name='roomId']").value,
        formElem.querySelector("input[name='roomName']").value,
        formElem.querySelector("textarea[name='description']").value
    );

    // chatRoomJsonData.roomId = formElem.querySelector("input[name='roomId']").value;
    // chatRoomJsonData.roomName = formElem.querySelector("input[name='roomName']").value;
    // chatRoomJsonData.description = formElem.querySelector("textarea[name='description']").value;

    if (chatRoomJsonData.roomName.length === 0) {
        alert("채팅방 이름을 입력해주세요.");
        return false;
    }
    if (chatRoomJsonData.permitMBTICode.size === 0) {
        alert("채팅방에 입장 가능한 MBTI를 선택해주세요.");
        return false;
    }

    console.log(chatRoomJsonData);
    return true;
}

/** Both Modal Elements for Create and Update **/
export let CRUDChatRoomModalElem = null;

$(".CRUD-chatRoom-modal").on('show.bs.modal', function () {
    CRUDChatRoomModalElem = $(this);
    // checkboxMBTIElemList.forEach(input => input.checked = false);
    enableAllPermitMBTICodeSwitch();
    enableSelectPermitMBTICode();
});

$(".CRUD-chatRoom-modal").on('hidden.bs.modal', function () {
    CRUDChatRoomModalElem = null;
});
