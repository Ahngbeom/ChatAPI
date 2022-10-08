import {CRUDChatRoomModalElem} from "./utils.js";
import {MBTI_CODES} from "../mbti/getInfo.js";
import {chatRoomJsonData} from "./createChatRoom.js";

export const permitMBTICodeSelectAllSwitchBtn = document.querySelector("#checkbox_switch_ALL");
export const checkboxMBTIElemList = document.querySelectorAll("input[name='permitMBTICode']");

export let MBTI_survey = {
    focused: null,
    recognition: null,
    decision: null,
    coping: null
}

export const enableAllPermitMBTICodeSwitch = function () {
    $(".permitMBTICode-all-select-btn").on('change', function () {
        checkboxMBTIElemList.forEach(elem => {
            elem.checked = this.checked;
        });
        if (this.checked) {
            MBTI_CODES.forEach(code => chatRoomJsonData.permitMBTICode.add(code));
            MBTI_survey.focused = ".";
            MBTI_survey.recognition = ".";
            MBTI_survey.decision = ".";
            MBTI_survey.coping = ".";
        } else {
            chatRoomJsonData.permitMBTICode.clear();
            MBTI_survey.focused = null;
            MBTI_survey.recognition = null;
            MBTI_survey.decision = null;
            MBTI_survey.coping = null;
        }
        ReflectionPermitMbtiCode(CRUDChatRoomModalElem, chatRoomJsonData.permitMBTICode);
    });
}

export const enableSelectPermitMBTICode = function (modalTarget) {
    $("input[name='permitMBTICode']").on('change', function () {
        if (this.checked) {
            if (this.value.match(/[EI]/)) {
                MBTI_survey.focused = MBTI_survey.focused === null ? this.value : ".";
            } else if (this.value.match(/[SN]/)) {
                MBTI_survey.recognition = MBTI_survey.recognition === null ? this.value : ".";
            } else if (this.value.match(/[TF]/)) {
                MBTI_survey.decision = MBTI_survey.decision === null ? this.value : ".";
            } else if (this.value.match(/[JP]/)) {
                MBTI_survey.coping = MBTI_survey.coping === null ? this.value : ".";
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
        console.log(chatRoomJsonData.permitMBTICode.size);
        if (chatRoomJsonData.permitMBTICode.size === 16)
            permitMBTICodeSelectAllSwitchBtn.checked = true;
        else
            permitMBTICodeSelectAllSwitchBtn.checked = false;
        ReflectionPermitMbtiCode(chatRoomJsonData.permitMBTICode);
    });
}


export const ReflectionPermitMbtiCode = function (permitMBTICodeSet) {
    // console.log(permitMBTICodeSet);
    if (permitMBTICodeSet.size > 0) {
        CRUDChatRoomModalElem.find("#MBTICodeToastArea ul").html("");
        Array.from(permitMBTICodeSet).sort().forEach(value => {
            // console.log(value);
            checkboxMBTIElemList.forEach(input => {
                if(input.value.match("[" + value + "]"))
                    input.checked = true;
            });
            CRUDChatRoomModalElem.find("#MBTICodeToastArea ul").append(
                "<li class='list-group-item'>" +
                "   <span class='d-flex justify-content-end'>" + value +
                "       <button type=\"button\" class=\"btn-close me-2 m-auto\" data-bs-dismiss=\"badge\" aria-label=\"Close\"></button>" +
                "   </span>" +
                "</li>"
            );
        })
    } else {
        CRUDChatRoomModalElem.find("#MBTICodeToastArea ul").html("");
    }
}