/** AJAX for Chatting Room List **/

const getChatRoomList = function () {
    let result = null;
    $.ajax({
        type: 'GET',
        url: '/chat/list',
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            // console.log(data);
            // createRowsForChatRoomList(chatRoomTable, data);
            console.log(data);
            result = data;
        },
        error: function (data) {
            console.error(data);
        }
    });
    return result;
}
