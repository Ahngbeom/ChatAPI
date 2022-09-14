import {ajaxGetUserAuthorities} from "./authority.js";
import {ajaxAdminGetUserMbtiList} from "/js/mbti/getInfo.js";

export const getUserList = function () {
    let result = null;
    $.ajax({
        type: 'GET',
        url: '/user-list',
        async: false,
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        success: function (data) {
            // mainContainer.innerHTML = "<h1>User Table</h1>";
            // // mainContainer.innerHTML += "<th:block th:replace=\"/fragments/table :: table-primary-fragment\"/>";
            // mainContainer.append(createUsersTableElement(data));
            // enableUserInfoInteractionBtn();

            // mbtiTable.classList.add("table-success");
            result = data;
        },
        error: function (data) {
            console.log(data);
        }
    });
    return result;
};

export const enableUserInfoInteractionBtn = function () {
    const getUserAuthoritiesBtn = document.querySelectorAll(".getUserAuthoritiesBtn");
    const getUserMBTIListBtn = document.querySelectorAll(".getUserMBTIListBtn");
    getUserAuthoritiesBtn.forEach(btn => {
        btn.addEventListener('click', function (e) {
            ajaxGetUserAuthorities(e.currentTarget.dataset.userno);
        });
    });
    getUserMBTIListBtn.forEach(btn => {
        btn.addEventListener('click', function (e) {
            ajaxAdminGetUserMbtiList(e.currentTarget.dataset.userno);
        });
    });
}

export const createUsersTableElement = function (data) {

    const table = document.createElement('table');
    table.setAttribute('class', 'table table-success table-striped text-center table-responsive');
    const thead = table.createTHead();
    const thead_row = thead.insertRow(0);
    thead_row.insertCell(0).innerHTML = "#";
    thead_row.insertCell(1).innerHTML = "Username";
    thead_row.insertCell(2).innerHTML = "Nickname";
    thead_row.insertCell(3).innerHTML = "Authority";
    thead_row.insertCell(4).innerHTML = "MBTI-List";

    const tbody = table.createTBody();
    data.forEach((user, index) => {
        const tbody_row = tbody.insertRow(index);
        tbody_row.insertCell(0).innerHTML = user.id;
        tbody_row.insertCell(1).innerHTML = user.username;
        tbody_row.insertCell(2).innerHTML = user.nickname;
        tbody_row.insertCell(3).innerHTML = "<button class='btn btn-light getUserAuthoritiesBtn' data-userno='" + user.id + "'>조회</button>";
        tbody_row.insertCell(4).innerHTML = "<button class='btn btn-light getUserMBTIListBtn' data-userno='" + user.id + "'>조회</button>";
        // console.log(user, index);
    });

    return table;
}





