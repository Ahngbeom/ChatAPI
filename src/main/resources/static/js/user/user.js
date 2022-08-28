const loggedInUsername = document.querySelectorAll("#loggedInUsername");


(function () {
    $.ajax({
        type: 'GET',
        url: '/user-info',
        async: false,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log(data);
            // let logoutBtn = document.createElement("button");
            // logoutBtn.type = 'button';
            // logoutBtn.innerHTML = "Logout";
            loggedInUsername.forEach(elem => {
                elem.innerHTML = "<h4 class='m-3'>Logged in: " + data.username + "</h4>";
                elem.innerHTML += "<div class='m-3'><button type='button' class='btn btn-warning' onclick=\"location.href='/logout'\">Logout</button></div>";
            });
            if (data.username) {
                document.body.style.paddingTop = "150px";
                let adminNavbar =
                    "  <div class=\"container-fluid text-bg-secondary opacity-75\">\n" +
                    "    <button class=\"btn btn-link link-light\" onclick='getUserList()'>User List</button>\n" +
                    "    <a class=\"link-light\" href=\"#\">Chat Room List</a>\n" +
                    "    <a class=\"link-light\" href=\"#\">Statistics</a>\n" +
                    "  </div>\n";
                navContainer.insertAdjacentHTML("afterend", adminNavbar);
            }
        },
        error: function (data) {
            console.log(data);
        }
    });
}());

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




