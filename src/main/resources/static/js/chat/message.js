import {chatModalElement, sendMessageInput} from "./utils.js";
import {stompClient} from "./connection.js";

export function getChatLog(roomId) {
    $.ajax({
        type: "GET",
        url: "/api/chat/log/" + roomId,
        async: false,
        contentType: "application/json; charset=utf-8",
        dataType: 'JSON',
        success: function (data) {
            data.forEach(message => putMessage(message));
        },
        error: function (xhr) {
            console.error(xhr.responseText);
        }
    })
}

export function sendMessage(roomId) {
    console.log("sendMessage()");
    if (stompClient) {
        stompClient.send("/mbti-chat/send-message/" + roomId, {}, sendMessageInput.value);
        sendMessageInput.value = "";
    } else
        alert("Not Connected.");
}

export function putMessage(Message) {
    let row = document.createElement("div");
    let alert = document.createElement("div");
    let alertHeading = document.createElement("h5");
    let alertContent = document.createElement("p");
    row.classList.add("row");
    alert.classList.add("alert");
    alertHeading.classList.add("alert-heading");
    alertHeading.append(Message.from);
    alertContent.append(Message.message);
    alert.append(alertHeading);
    alert.append(alertContent);
    row.append(alert);

    if (Message.from === "Server") {
        row.classList.add("justify-content-center");
        if (Message.status === "ERROR")
            alert.classList.add("alert-danger", "col-10");
        else
            alert.classList.add("alert-warning", "col-10");
    } else {
        // if (data.status === "IMPORTANT")
        //     alert.classList.add("alert-success");

        if (Message.from === USER_INFO.username) {
            row.classList.add("justify-content-end");
            alert.classList.add("alert-info", "col-6");
        } else {
            row.classList.add("justify-content-start");
            alert.classList.add("alert-secondary", "col-6");
        }
    }
    chatModalElement.querySelector(".modal-body")
        .insertAdjacentElement('beforeend', row);
}