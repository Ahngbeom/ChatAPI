/** Utilities for Chatting Room **/
import {
    enableAllPermitMBTICodeSwitch,
    enableSelectPermitMBTICode, MBTI_survey,
    ReflectionPermitMbtiCode
} from "./checkPermitMBTI.js";

const disposable = document.querySelector("#disposable-modal");
const updateChatRoomModalElem = document.querySelector("#UpdateChatRoomModal");

export let CRUDChatRoomModalElem = null;

$(".permitMBTICode-all-select-btn").on('change', function () {

});


$(".edit-chat-room-btn").on('click', function () {

    $.ajax({
        type: 'GET',
        url: '/api/chat/info',
        async: false,
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        data: {
            roomName: $(this).data('room-name')
        },
        success: function (data) {
            console.log(data);
            updateChatRoomModalElem.querySelector("input[name='roomName']").value = data.roomName;
            updateChatRoomModalElem.querySelector("textarea[name='description']").value = data.description;
            showModalTarget(updateChatRoomModalElem);

            ReflectionPermitMbtiCode(new Set(data.permitMBTICode));
        },
        error: function (xhr) {
            renewalModal({
                target: disposable,
                title: "Error",
                body: "<p class='text-danger'>" + xhr.responseText + "</p>"
            });
        }
    });


});

function checked_to_MBTI_Survey(target) {
    if (target.value.match(/[EI]/)) {
        MBTI_survey.focused = MBTI_survey.focused === null ? this.value : ".";
    } else if (target.value.match(/[SN]/)) {
        MBTI_survey.recognition = MBTI_survey.recognition === null ? this.value : ".";
    } else if (target.value.match(/[TF]/)) {
        MBTI_survey.decision = MBTI_survey.decision === null ? this.value : ".";
    } else if (target.value.match(/[JP]/)) {
        MBTI_survey.coping = MBTI_survey.coping === null ? this.value : ".";
    }
}

$(".CRUD-chatRoom-modal").on('shown.bs.modal', function () {
    CRUDChatRoomModalElem = $(this);
    checkboxMBTIElemList.forEach(input => input.checked = false);
    enableAllPermitMBTICodeSwitch();
    enableSelectPermitMBTICode();
});

$(".CRUD-chatRoom-modal").on('hidden.bs.modal', function () {
    CRUDChatRoomModalElem = null;
});
