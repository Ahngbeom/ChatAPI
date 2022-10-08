import {chatRoomJsonData, CRUDChatRoomModalElem} from "./utils.js";
import {MBTI_CODES} from "../mbti/getInfo.js";

export const permitMBTICodeSelectAllSwitchBtn = document.querySelector("#checkbox_switch_ALL");
export const checkboxMBTIElemList = document.querySelectorAll("input[name='permitMBTICode']");

export let PermitMBTI_survey = {
    focused: null,
    recognition: null,
    decision: null,
    coping: null,
}

export function PermitMBTI_survey_init() {
    PermitMBTI_survey.focused = null;
    PermitMBTI_survey.recognition = null;
    PermitMBTI_survey.decision = null;
    PermitMBTI_survey.coping = null;
}


export const enableAllPermitMBTICodeSwitch = function () {
    $(".permitMBTICode-all-select-btn").on('change', function () {
        checkboxMBTIElemList.forEach(elem => {
            elem.checked = this.checked;
        });
        if (this.checked) {
            MBTI_CODES.forEach(code => chatRoomJsonData.permitMBTICode.add(code));
            PermitMBTI_survey.focused = ".";
            PermitMBTI_survey.recognition = ".";
            PermitMBTI_survey.decision = ".";
            PermitMBTI_survey.coping = ".";
        } else {
            chatRoomJsonData.permitMBTICode.clear();
            PermitMBTI_survey.focused = null;
            PermitMBTI_survey.recognition = null;
            PermitMBTI_survey.decision = null;
            PermitMBTI_survey.coping = null;
        }
        ReflectionPermitMbtiCode(chatRoomJsonData.permitMBTICode);
    });
}

export const enableSelectPermitMBTICode = function () {
    $("input[name='permitMBTICode']").on('change', function () {
        if (this.checked) {
            if (this.value.match(/[EI]/)) {
                PermitMBTI_survey.focused = PermitMBTI_survey.focused === null ? this.value : ".";
            } else if (this.value.match(/[SN]/)) {
                PermitMBTI_survey.recognition = PermitMBTI_survey.recognition === null ? this.value : ".";
            } else if (this.value.match(/[TF]/)) {
                PermitMBTI_survey.decision = PermitMBTI_survey.decision === null ? this.value : ".";
            } else if (this.value.match(/[JP]/)) {
                PermitMBTI_survey.coping = PermitMBTI_survey.coping === null ? this.value : ".";
            }
        } else {
            if (this.value.match(/[EI]/)) {
                if (PermitMBTI_survey.focused === ".") {
                    if (this.value === "E") PermitMBTI_survey.focused = "I";
                    else PermitMBTI_survey.focused = "E";
                } else {
                    PermitMBTI_survey.focused = null;
                }
            } else if (this.value.match(/[SN]/)) {
                if (PermitMBTI_survey.recognition === ".") {
                    if (this.value === "S") PermitMBTI_survey.recognition = "N";
                    else PermitMBTI_survey.recognition = "S";
                } else {
                    PermitMBTI_survey.recognition = null;
                }
            } else if (this.value.match(/[TF]/)) {
                if (PermitMBTI_survey.decision === ".") {
                    if (this.value === "T") PermitMBTI_survey.decision = "F";
                    else PermitMBTI_survey.decision = "T";
                } else {
                    PermitMBTI_survey.decision = null;
                }
            } else if (this.value.match(/[JP]/)) {
                if (PermitMBTI_survey.coping === ".") {
                    if (this.value === "J") PermitMBTI_survey.coping = "P";
                    else PermitMBTI_survey.coping = "J";
                } else {
                    PermitMBTI_survey.coping = null;
                }
            }
        }
        console.log(PermitMBTI_survey);

        let code = PermitMBTI_survey.focused + PermitMBTI_survey.recognition + PermitMBTI_survey.decision + PermitMBTI_survey.coping;
        MBTI_CODES.forEach(value => {
            if (this.checked && value.match(code)) {
                chatRoomJsonData.permitMBTICode.add(value);
            } else if (!this.checked && !value.match(code)) {
                chatRoomJsonData.permitMBTICode.delete(value);
            }
        });
        console.log(chatRoomJsonData.permitMBTICode);

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
                if (input.value.match("[" + value + "]"))
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