import {MBTI_CODES} from "../mbti/getInfo.js";

const createChatRoomBtn = document.querySelector("#createChatRoomBtn");

const createChatRoomModalElem = document.getElementById("createChatRoomModal");
const createChatRoomSubmitBtn = createChatRoomModalElem.querySelector("#createChatRoomSubmitBtn");

const permitMBTICodeSelectAllSwitchBtn = document.querySelector("#checkbox_switch_ALL");
const checkboxMBTIElemList = document.querySelectorAll("input[name='permitMBTICode']");

const addPermitMBTICodeBtn = createChatRoomModalElem.querySelector("#addPermitMBTICodeBtn");

let chatRoomJsonData = {
    roomName: null,
    description: null,
    permitMBTICode: new Set()
}

let MBTI_survey = {
    focused: null,
    recognition: null,
    decision: null,
    coping: null
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
            },
            error: function (data) {
                console.log(data);
            }
        });
    });
});


const enableAllPermitMBTICodeSwitch = function () {
    permitMBTICodeSelectAllSwitchBtn.addEventListener("change", function (e) {
        checkboxMBTIElemList.forEach(elem => {
            elem.checked = this.checked;
        });
        if (this.checked) {
            MBTI_CODES.map(code => chatRoomJsonData.permitMBTICode.add(code));
        } else {
            chatRoomJsonData.permitMBTICode.clear();
        }
        ReflectionPermitMbtiCode();
    });
}

const enableSelectPermitMBTICode = function () {
    checkboxMBTIElemList.forEach(elem => {
        elem.addEventListener("change", function () {
            if (this.checked) {
                if (this.value.match(/[EI]/)) {
                    MBTI_survey.focused = MBTI_survey.focused === null ? elem.value : ".";
                } else if (this.value.match(/[SN]/)) {
                    MBTI_survey.recognition = MBTI_survey.recognition === null ? elem.value : ".";
                } else if (this.value.match(/[TF]/)) {
                    MBTI_survey.decision = MBTI_survey.decision === null ? elem.value : ".";
                } else if (this.value.match(/[JP]/)) {
                    MBTI_survey.coping = MBTI_survey.coping === null ? elem.value : ".";
                }
            } else {
                if (this.value.match(/[EI]/)) {
                    if (MBTI_survey.focused === ".") {
                        if (this.value === "E") MBTI_survey.focused = "I";
                        else MBTI_survey.focused = "E";
                    } else {
                        MBTI_survey.focused = null;
                    }
                } else if (this.value.match(/[SN]/)) {
                    if (MBTI_survey.recognition === ".") {
                        if (this.value === "S") MBTI_survey.recognition = "N";
                        else MBTI_survey.recognition = "S";
                    } else {
                        MBTI_survey.recognition = null;
                    }
                } else if (this.value.match(/[TF]/)) {
                    if (MBTI_survey.decision === ".") {
                        if (this.value === "T") MBTI_survey.decision = "F";
                        else MBTI_survey.decision = "T";
                    } else {
                        MBTI_survey.decision = null;
                    }
                } else if (this.value.match(/[JP]/)) {
                    if (MBTI_survey.coping === ".") {
                        if (this.value === "J") MBTI_survey.coping = "P";
                        else MBTI_survey.coping = "J";
                    } else {
                        MBTI_survey.coping = null;
                    }
                }
            }
            console.log(MBTI_survey);

            if (MBTI_survey.focused !== null && MBTI_survey.recognition !== null && MBTI_survey.decision !== null && MBTI_survey.coping !== null) {
                chatRoomJsonData.permitMBTICode.clear();
                let code = MBTI_survey.focused + MBTI_survey.recognition + MBTI_survey.decision + MBTI_survey.coping;
                console.log(code);
                MBTI_CODES.forEach(value => {
                    if (value.match(code)) {
                        chatRoomJsonData.permitMBTICode.add(value);
                    }
                });
                console.log(chatRoomJsonData.permitMBTICode);
            }
            ReflectionPermitMbtiCode();
        });
    });
}

const ReflectionPermitMbtiCode = function () {
    if (chatRoomJsonData.permitMBTICode.size > 0) {
        createChatRoomModalElem.querySelector("#MBTICodeToastArea ul").innerHTML = "";
        Array.from(chatRoomJsonData.permitMBTICode).sort().forEach(value => {
            createChatRoomModalElem.querySelector("#MBTICodeToastArea ul").innerHTML +=
                "<li class='list-group-item'>" +
                "   <span class='d-flex justify-content-end'>" + value +
                "       <button type=\"button\" class=\"btn-close me-2 m-auto\" data-bs-dismiss=\"badge\" aria-label=\"Close\"></button>" +
                "   </span>" +
                "</li>";
        })
    } else {
        createChatRoomModalElem.querySelector("#MBTICodeToastArea ul").innerHTML = "";
    }
}
