const createChatRoomModalElem = document.getElementById("createChatRoomModal");
const createChatRoomSubmitBtn = createChatRoomModalElem.querySelector("#modalInteractionBtn");

const createChatRoom = function () {
    renewalModal({
        target: createChatRoomModalElem,
        title: "Create Chatting Room",
        body: "<div class=\"d-flex flex-column\">\n" +
            "                        <div class=\"d-flex justify-content-center w-100 mx-1\">\n" +
            "                            <form action='/chat/create' method=\"post\" class=\"form-group flex-column w-100\" id=\"createChatRoom\">\n" +
            "                                <div class=\"input-group mb-3 row\">\n" +
            "                                    <label for=\"createChatRoomRoomName\"\n" +
            "                                           class=\"input-group-text col-sm-3 col-form-label\">Room Name</label>\n" +
            "                                    <input type=\"text\" class=\"form-control\" id=\"createChatRoomRoomName\" name=\"roomName\"/>\n" +
            "                                    <button class=\"btn btn-secondary col-sm-2 justify-content-evenly\" type=\"button\"\n" +
            "                                            id=\"createChatRoomRoomNameValidateBtn\" disabled>\n" +
            "                                        <span>Validate</span>\n" +
            "                                    </button>\n" +
            "                                </div>\n" +
            "                                <div class=\"input-group mb-3 row\">\n" +
            "                                    <label for=\"createChatRoomDescription\" class=\"input-group-text col-sm-3 col-form-label\">Description</label>\n" +
            "                                    <textarea class=\"form-control\" id=\"createChatRoomDescription\"\n" +
            "                                           name=\"description\"></textarea>\n" +
            "                                </div>\n" +
            "                                <div class=\"input-group row mb-3\">\n" +
            "                                    <span class=\"input-group-text col-sm-3 text-wrap\">MBTI to allow entry</span>\n" +
            "                                    <div class=\"form-control col-sm-8 p-2 d-flex flex-column\"\n" +
            "                                         id=\"createChatRoompermitMBTICode\">\n" +
            "                                        <div class=\"d-flex justify-content-center\">\n" +
            "                                           <div class=\"form-check form-switch\">\n" +
            "                                               <input class=\"form-check-input\" type=\"checkbox\" role=\"switch\" id=\"checkbox_switch_ALL\">\n" +
            "                                                <label class='form-check-label' for=\"checkbox_switch_ALL\">All</label>\n" +
            "                                           </div>\n" +
            "                                       </div>\n" +
            "                                       <div class='d-flex justify-content-evenly'>" +
            "                                           <div class='form-check col-5 border rounded'>\n" +
            "                                              <input class=\"form-check-input\" type=\"checkbox\" name=\"permitMBTICode\"\n" +
            "                                                      id=\"checkbox_E\"\n" +
            "                                                       value=\"E\">\n" +
            "                                              <label class='form-check-label col-12' for=\"checkbox_E\">E</label>\n" +
            "                                           </div>\n" +
            "                                           <div class='form-check col-5 border rounded'>\n" +
            "                                             <input class=\"form-check-input\" type=\"checkbox\" name=\"permitMBTICode\"\n" +
            "                                                     id=\"checkbox_I\"\n" +
            "                                                     value=\"I\">\n" +
            "                                             <label class='form-check-label col-12' for=\"checkbox_I\">I</label>\n" +
            "                                           </div>\n" +
            "                                       </div>\n" +
            "                                       <div class='d-flex justify-content-evenly'>" +
            "                                           <div class='form-check col-5 border rounded'>\n" +
            "                                              <input class=\"form-check-input\" type=\"checkbox\" name=\"permitMBTICode\"\n" +
            "                                                      id=\"checkbox_S\"\n" +
            "                                                       value=\"S\">\n" +
            "                                              <label class='form-check-label col-12' for=\"checkbox_S\">S</label>\n" +
            "                                           </div>\n" +
            "                                           <div class='form-check col-5 border rounded'>\n" +
            "                                             <input class=\"form-check-input\" type=\"checkbox\" name=\"permitMBTICode\"\n" +
            "                                                     id=\"checkbox_N\"\n" +
            "                                                     value=\"N\">\n" +
            "                                             <label class='form-check-label col-12' for=\"checkbox_N\">N</label>\n" +
            "                                           </div>\n" +
            "                                       </div>\n" +
            "                                       <div class='d-flex justify-content-evenly'>" +
            "                                           <div class='form-check col-5 border rounded'>\n" +
            "                                              <input class=\"form-check-input\" type=\"checkbox\" name=\"permitMBTICode\"\n" +
            "                                                      id=\"checkbox_T\"\n" +
            "                                                       value=\"T\">\n" +
            "                                              <label class='form-check-label col-12' for=\"checkbox_T\">T</label>\n" +
            "                                           </div>\n" +
            "                                           <div class='form-check col-5 border rounded'>\n" +
            "                                             <input class=\"form-check-input\" type=\"checkbox\" name=\"permitMBTICode\"\n" +
            "                                                     id=\"checkbox_F\"\n" +
            "                                                     value=\"F\">\n" +
            "                                             <label class='form-check-label col-12' for=\"checkbox_F\">F</label>\n" +
            "                                           </div>\n" +
            "                                       </div>\n" +
            "                                       <div class='d-flex justify-content-evenly'>" +
            "                                           <div class='form-check col-5 border rounded'>\n" +
            "                                              <input class=\"form-check-input\" type=\"checkbox\" name=\"permitMBTICode\"\n" +
            "                                                      id=\"checkbox_J\"\n" +
            "                                                       value=\"J\">\n" +
            "                                              <label class='form-check-label col-12' for=\"checkbox_J\">J</label>\n" +
            "                                           </div>\n" +
            "                                           <div class='form-check col-5 border rounded'>\n" +
            "                                             <input class=\"form-check-input\" type=\"checkbox\" name=\"permitMBTICode\"\n" +
            "                                                     id=\"checkbox_P\"\n" +
            "                                                     value=\"P\">\n" +
            "                                             <label class='form-check-label col-12' for=\"checkbox_P\">P</label>\n" +
            "                                           </div>\n" +
            "                                       </div>\n" +
            "                                    </div>\n" +
            "                                   <th:div th:replace=\"fragments/toast :: toast ('permitMBTICodeToast')\"></th:div>\n" +
            "                                 </div>\n" +
            "                            </form>\n" +
            "                        </div>\n" +
            "                    </div>",
        interactionBtnType: "primary",
        interactionBtnText: "Create",
    });
    showModalTarget(createChatRoomModalElem);

    let chatRoomJsonData = {
        roomName: null,
        description: null,
        permitMBTICode: {
            code: null
        }
    }

    const checkboxSwitchALL = document.querySelector("#checkbox_switch_ALL");
    const checkboxMBTIElemList = document.querySelectorAll("input[name='permitMBTICode']");
    checkboxSwitchALL.addEventListener("change", function (e) {
        checkboxMBTIElemList.forEach(elem => {
            elem.checked = e.target.checked;
        });
    });

    createChatRoomSubmitBtn.addEventListener("click", function () {
        chatRoomJsonData.roomName = createChatRoomModalElem.querySelector("input[name='roomName']").value;
        chatRoomJsonData.description = createChatRoomModalElem.querySelector("textarea[name='description']").value;
        checkboxMBTIElemList.forEach(elem => {
            chatRoomJsonData.permitMBTICode.code += elem.value;
        });
        console.log(chatRoomJsonData);
        // $.ajax({
        //     type: 'POST',
        //     url: '/chat/create',
        //     contentType: 'application/json; charset=utr-8',
        //     dataType: 'JSON',
        //     data: JSON.stringify(chatRoomJsonData),
        //     success: function (data) {
        //         console.log(data);
        //     },
        //     error: function () {
        //         console.log(data);
        //     }
        // });
    });
}