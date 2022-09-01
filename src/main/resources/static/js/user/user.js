const loggedInUsername = document.querySelectorAll("#loggedInUsername");

const getUserList = function () {
    $.ajax({
        type: 'GET',
        url: '/user-list',
        async: false,
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        success: function (data) {
            mainContainer.innerHTML = "<h1>User Table</h1>";
            // mainContainer.innerHTML += "<th:block th:replace=\"/fragments/table :: table-primary-fragment\"/>";
            mainContainer.append(createTableElement(data));
        },
        error: function (data) {
            console.log(data);
        }
    });
};




