import {enableAllPermitMBTICodeSwitch, enableSelectPermitMBTICode, PermitMBTI_survey} from "./checkPermitMBTI.js";
import {chatRoomJsonData, chatRoomJsonData_init, formDataToJsonFormatterForChatRoom} from "./utils.js";

const createChatRoomBtn = document.querySelector("#createChatRoomBtn");

export const createChatRoomModalElem = document.getElementById("CreateChatRoomModal");
export const createChatRoomSubmitBtn = createChatRoomModalElem.querySelector("#CreateChatRoomSubmitBtn");

createChatRoomBtn.addEventListener("click", function () {
    showModalTarget(createChatRoomModalElem);

    // enableAllPermitMBTICodeSwitch();
    // enableSelectPermitMBTICode();

    // createChatRoomModalElem.addEventListener('hidden.bs.modal', function () {
    //     createChatRoomModalElem.querySelectorAll("input").forEach(input => {
    //         input.value = "";
    //     });
    //     createChatRoomModalElem.querySelectorAll("textarea").forEach(textarea => {
    //         textarea.innerHTML = "";
    //     });
    //     $("#checkbox_switch_ALL").checked = false;
    //     // createChatRoomModalElem.querySelector("#checkbox_switch_ALL").evenchecked = false;
    // });

    createChatRoomSubmitBtn.addEventListener("click", function () {
        if (formDataToJsonFormatterForChatRoom(createChatRoomModalElem.querySelector("form"))) {
            console.log(chatRoomJsonData);
            console.log(PermitMBTI_survey)
            //     $.ajax({
            //         type: 'POST',
            //         url: '/api/chat/create',
            //         contentType: 'application/json; charset=utf-8',
            //         dataType: 'JSON',
            //         data: JSON.stringify({
            //             roomName: chatRoomJsonData.roomName,
            //             description: chatRoomJsonData.description,
            //             permitMBTICode: Array.from(chatRoomJsonData.permitMBTICode)
            //         }),
            //         success: function (data) {
            //             console.log(data);
            //             location.reload();
            //         },
            //         error: function (data) {
            //             console.log(data);
            //         }
            //     });
        }
    }, {once: true});
});




