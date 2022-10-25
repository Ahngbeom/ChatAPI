export function JoinAvailability(roomName) {
    let result = false;
    $.ajax({
        type: "GET",
        url: "/api/chat/join-availability/" + roomName,
        async: false,
        contentType: "application/json; charset=UTF-8",
        success: function () {
            result = true;
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
    return result;
}

export function LeaveAvailability(roomName) {
    let result = false;
    $.ajax({
        type: "GET",
        url: "/api/chat/leave-chatroom/" + roomName,
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function () {
            result = true;
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
    return true;
}
