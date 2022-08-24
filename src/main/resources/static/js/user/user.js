
const loggedInUsername = document.querySelector("#loggedInUsername");

(function () {
    $.ajax({
        type: 'GET',
        url: '/user-info',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log(data);
            loggedInUsername.innerHTML = "<h4 class='m-3'>Logged in: " + data.username + "</h4>";
            let logoutBtn = document.createElement("button");
            logoutBtn.type = 'button';
            logoutBtn.innerHTML = "Logout";
            loggedInUsername.innerHTML += "<div class='m-3'><button type='button' class='btn btn-warning' onclick=\"location.href='/logout'\">Logout</button></div>";
        },
        error: function (data) {
            console.log(data);
        }
    });
}());