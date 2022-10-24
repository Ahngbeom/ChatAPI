import {PermitMBTI_survey, PermitMBTI_survey_init, ReflectionPermitMbtiCode} from "./checkPermitMBTI.js";
import {
    chatRoomJsonData,
    chatRoomJsonData_init,
    disposableModalElem,
    formDataToJsonFormatterForChatRoom
} from "./utils.js";

export const updateChatRoomModalElem = document.querySelector("#UpdateChatRoomModal");
const updateChatRoomSubmitBtn = updateChatRoomModalElem.querySelector("#UpdateChatRoomSubmitBtn");
const removeChatRoomSubmitBtn = updateChatRoomModalElem.querySelector("#RemoveChatRoomSubmitBtn");

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
            updateChatRoomModalElem.querySelector("input[name='roomId']").value = data.roomId;
            updateChatRoomModalElem.querySelector("input[name='roomName']").value = data.roomName;
            updateChatRoomModalElem.querySelector("textarea[name='description']").value = data.description;
            updateChatRoomModalElem.querySelector("form").setAttribute("data-room-name", data.roomName);
            PermitMBTI_survey_init();
            data.permitMBTICode.forEach(code => {
                if (code.match(/[EI]/)) {
                    if (PermitMBTI_survey.focused === null)
                        PermitMBTI_survey.focused = code.charAt(0);
                    else if (PermitMBTI_survey.focused !== code.charAt(0))
                        PermitMBTI_survey.focused = ".";
                }
                if (code.match(/[SN]/)) {
                    if (PermitMBTI_survey.recognition === null)
                        PermitMBTI_survey.recognition = code.charAt(1);
                    else if (PermitMBTI_survey.recognition !== code.charAt(1))
                        PermitMBTI_survey.recognition = ".";
                }
                if (code.match(/[TF]/)) {
                    if (PermitMBTI_survey.decision === null)
                        PermitMBTI_survey.decision = code.charAt(2);
                    else if (PermitMBTI_survey.decision !== code.charAt(2))
                        PermitMBTI_survey.decision = ".";
                }
                if (code.match(/[JP]/)) {
                    if (PermitMBTI_survey.fulfillment === null)
                        PermitMBTI_survey.fulfillment = code.charAt(3);
                    else if (PermitMBTI_survey.fulfillment !== code.charAt(3))
                        PermitMBTI_survey.fulfillment = ".";
                }
            });
            // console.log(PermitMBTI_survey);
            showModalTarget(updateChatRoomModalElem);
            // chatRoomJsonData.permitMBTICode = new Set(data.permitMBTICode);
            ReflectionPermitMbtiCode(new Set(data.permitMBTICode));

            chatRoomJsonData_init(
                updateChatRoomModalElem.querySelector("input[name='roomId']").value,
                updateChatRoomModalElem.querySelector("input[name='roomName']").value,
                updateChatRoomModalElem.querySelector("textarea[name='description']").value,
                new Set(data.permitMBTICode)
            );
        },
        error: function (xhr) {
            renewalModal({
                target: disposableModalElem,
                title: "Error",
                body: "<p class='text-danger'>" + xhr.responseText + "</p>"
            });
        }
    });

    updateChatRoomSubmitBtn.addEventListener('click', function () {
        // if (formDataToJsonFormatterForChatRoom(updateChatRoomModalElem.querySelector("form"))) {
        $.ajax({
            type: "POST",
            url: "/api/chat/update",
            contentType: "application/json; charset=utf-8",
            dataType: "JSON",
            data: JSON.stringify({
                roomId: chatRoomJsonData.roomId,
                roomName: chatRoomJsonData.roomName,
                description: chatRoomJsonData.description,
                permitMBTICode: Array.from(chatRoomJsonData.permitMBTICode)
            }),
            success: function (data) {
                if (data) {
                    renewalModal({
                        target: confirmModalElem,
                        type: "success",
                        title: "채팅방이 수정되었습니다.",
                    }, "<button class='btn btn-primary' id='confirmModalAcceptBtn'>확인</button>");
                    showModalTarget(confirmModalElem);
                    $("#confirmModalAcceptBtn").one('click', function () {
                        location.reload();
                    });
                }
            },
            error: function (xhr) {
                console.error(xhr.responseText);
            }
        })
        // }
    }, {once: true});

    removeChatRoomSubmitBtn.addEventListener('click', function () {
        renewalModal({
            target: confirmModalElem,
            type: "danger",
            title: "정말 채팅방을 삭제하시겠습니까?",
        }, "<button type=\"button\" class=\"btn btn-danger\" id=\"confirmModalAcceptBtn\">Accept</button>");
        showModalTarget(confirmModalElem);

        $("#confirmModalAcceptBtn").one('click', function () {
            console.log("Remove...");
            $.ajax({
                type: "GET",
                url: "/api/chat/remove",
                contentType: "application/json; charset=utf-8",
                dataType: "JSON",
                data: {
                    roomId: chatRoomJsonData.roomId
                },
                success: function (data) {
                    if (data) {
                        renewalModal({
                            target: confirmModalElem,
                            type: "success",
                            title: "채팅방이 삭제되었습니다.",
                        }, "<button class='btn btn-primary' id='confirmModalAcceptBtn'>확인</button>");
                        showModalTarget(confirmModalElem);
                        $("#confirmModalAcceptBtn").one('click', function () {
                            location.reload();
                        });
                    }
                },
                error: function (xhr) {
                    alert(xhr.responseText);
                }
            })
        });
    }, {once: true});
});

