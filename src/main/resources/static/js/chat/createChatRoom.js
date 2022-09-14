const createChatRoomModalElem = document.getElementById("createChatRoomModal");
const createChatRoomSubmitBtn = createChatRoomModalElem.querySelector("#createChatRoomSubmitBtn");

const checkboxSwitchALL = document.querySelector("#checkbox_switch_ALL");
const checkboxMBTIElemList = document.querySelectorAll("input[name='permitMBTICode']");

const addPermitMBTICodeBtn = createChatRoomModalElem.querySelector("#addPermitMBTICodeBtn");

let chatRoomJsonData = {
    roomName: null,
    description: null,
    permitMBTICode: null
}

const createChatRoom = function () {
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
                body: "Enter the name of the room to be created."
            });
            return;
        }
        if (chatRoomJsonData.permitMBTICode === null) {
            createToast({
                target: document.getElementById("modalToast"),
                type: "warning",
                header: "Alert",
                body: "Please add at least one MBTI code for entry."
            });
            return;
        }

        console.log(chatRoomJsonData);
        $.ajax({
            type: 'POST',
            url: '/chat/create',
            contentType: 'application/json; charset=utf-8',
            dataType: 'JSON',
            data: JSON.stringify(chatRoomJsonData),
            success: function (data) {
                console.log(data);
            },
            error: function (data) {
                console.log(data);
            }
        });
    });
}

const enableAllPermitMBTICodeSwitch = function () {
    checkboxSwitchALL.addEventListener("change", function (e) {
        checkboxMBTIElemList.forEach(elem => {
            elem.checked = e.target.checked;
        });
    });
}

const enableSelectPermitMBTICode = function () {
    addPermitMBTICodeBtn.addEventListener('click', function () {
        let permitMBTICode = {
            focused: null,
            recognition: null,
            decision: null,
            coping: null
        }
        checkboxMBTIElemList.forEach(elem => {
            if (elem.checked === true) {
                if (elem.value.match(/[EI]/)) {
                    permitMBTICode.focused = permitMBTICode.focused === null ? elem.value : ".";
                } else if (elem.value.match(/[SN]/)) {
                    permitMBTICode.recognition = permitMBTICode.recognition === null ? elem.value : ".";
                } else if (elem.value.match(/[TF]/)) {
                    permitMBTICode.decision = permitMBTICode.decision === null ? elem.value : ".";
                } else if (elem.value.match(/[JP]/)) {
                    permitMBTICode.coping = permitMBTICode.coping === null ? elem.value : ".";
                } else {
                    createToast({
                        target: document.getElementById("modalToast"),
                        type: "danger",
                        header: "Alert",
                        body: "Fatal Error"
                    });
                }
            }

        });
        if (permitMBTICode.focused === null || permitMBTICode.recognition === null || permitMBTICode.decision === null || permitMBTICode.coping === null) {
            createToast({
                target: document.getElementById("modalToast"),
                type: "warning",
                header: "Alert",
                body: "Invalid checkbox selection"
            });
        } else {
            // let code = permitMBTICode.focused + " " + permitMBTICode.recognition + " " + permitMBTICode.decision + " " + permitMBTICode.coping;
            permitMBTICode = {code: permitMBTICode.focused + permitMBTICode.recognition + permitMBTICode.decision + permitMBTICode.coping};
            console.log(permitMBTICode);
            createChatRoomModalElem.querySelector("#MBTICodeToastArea").innerHTML =
                "<span class=\"badge bg-info w-100 d-flex justify-content-between\">" +
                "   <span class='h3 font-weight-bold'>" + (permitMBTICode.code === "...." ? "Permit All" : permitMBTICode.code) + "</span>" +
                "   <button type=\"button\" class=\"btn-close me-2 m-auto\" data-bs-dismiss=\"badge\" aria-label=\"Close\"></button>" +
                "</span>"
            chatRoomJsonData.permitMBTICode = permitMBTICode;
            document.querySelector("button[class='btn-close me-2 m-auto'][data-bs-dismiss='badge']").addEventListener('click', function (e) {
                    chatRoomJsonData.permitMBTICode = null;
                    this.parentElement.remove();
            });
        }
    });
}